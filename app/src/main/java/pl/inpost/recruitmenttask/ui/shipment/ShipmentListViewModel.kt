package pl.inpost.recruitmenttask.ui.shipment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.source.local.SharedPreferencesManager
import pl.inpost.recruitmenttask.data.source.model.AdapterItem
import pl.inpost.recruitmenttask.data.source.model.ShipmentStatus
import pl.inpost.recruitmenttask.data.source.repository.ShipmentsRepository
import pl.inpost.recruitmenttask.utils.setState
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val shipmentsRepository: ShipmentsRepository,
    private val sharedPreferences: SharedPreferencesManager
) : ViewModel() {

    private val _items = MutableLiveData<List<AdapterItem.Shipment>>(mutableListOf())
    val items: LiveData<List<AdapterItem.Shipment>>
        get() = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private var currentFiltering: ShipmentStatus? = ShipmentStatus.ALL

    private val _getRawJson = MutableLiveData<Boolean>()
    val getRawJson: LiveData<Boolean>
        get() = _getRawJson

    private var rawJson: String? = null

    init {
        _dataLoading.value = true
        if (!sharedPreferences.getBoolean(FIRST_TIME_DATA_LOAD, false)) {
            initJson()
            saveFirstTimeLoad()
            rawJson = null
        } else {
            loadAllShipments()
        }
    }

    //only needed when raw json exist
    private fun initJson() {
        _getRawJson.value = true
    }

    private fun saveFirstTimeLoad() {
        sharedPreferences.putBoolean(FIRST_TIME_DATA_LOAD, true)
    }

    private fun filterItems(
        shipments: List<AdapterItem.Shipment>
    ): List<AdapterItem.Shipment> {
        val shipmentsToShow = ArrayList<AdapterItem.Shipment>()
        val filteringTypeName = currentFiltering?.name
        currentFiltering?.let {
            for (shipment in shipments) {
                val statusName = ShipmentStatus.valueOfOrNull(shipment.status)?.name
                when (currentFiltering) {
                    ShipmentStatus.ALL -> {
                        shipmentsToShow.clear()
                        shipmentsToShow.addAll(shipments)
                    }

                    ShipmentStatus.READY_TO_PICKUP -> {
                        if (statusName.equals(filteringTypeName)) shipmentsToShow.add(shipment)
                    }

                    else -> {
                        shipmentsToShow.add(shipment)
                    }
                }
            }
        }
            ?: return shipments.filter { ShipmentStatus.valueOfOrNull(it.status)?.priority != ShipmentStatus.READY_TO_PICKUP.priority }

        return shipmentsToShow
    }

    private fun initData() {
        viewModelScope.launch {
            val shipments = shipmentsRepository.getShipments(json = rawJson)
            if (shipments is Result.Success) {
                _items.setState { shipments.data }
            } else if (shipments is Result.Error) {
                throw shipments.exception
            }
            delay(DELAY)
            _dataLoading.value = false
        }
    }

    private fun setFiltering(requestType: ShipmentStatus?) {
        _dataLoading.value = true
        currentFiltering = requestType
        loadShipments()
    }


    fun showReadyToPickUp() {
        setFiltering(ShipmentStatus.READY_TO_PICKUP)
    }

    fun showRemainingShipments() {
        setFiltering(null)
    }

    fun loadAllShipments() {
        setFiltering(ShipmentStatus.ALL)
    }

    fun showArchived() {
        viewModelScope.launch {
            val archived = shipmentsRepository.getArchived()
            if (archived is Result.Success) {
                _items.setState { archived.data }
            } else if (archived is Result.Error) {
                throw archived.exception
            }
            _dataLoading.value = false
        }
    }

    private fun loadShipments() {
        viewModelScope.launch {
            val shipments = shipmentsRepository.getShipments(null)
            if (shipments is Result.Success) {
                _items.setState { filterItems(shipments.data) }
            } else if (shipments is Result.Error) {
                throw shipments.exception
            }
            _dataLoading.value = false
        }
    }

    fun archiveShipment(archive: AdapterItem.Shipment) {
        viewModelScope.launch {
            shipmentsRepository.archiveShipment(archive)
        }
    }

    fun setRawJson(json: String?) {
        this.rawJson = json
        initData()
    }

    fun getRawJson(): String? {
        return rawJson
    }

    fun getCurrentFiltering(): ShipmentStatus? {
        return currentFiltering
    }

    companion object {
        const val FIRST_TIME_DATA_LOAD = "FIRST_TIME_DATA_LOAD"
        const val DELAY = 300L
    }
}
