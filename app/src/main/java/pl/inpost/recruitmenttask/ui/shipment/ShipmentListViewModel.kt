package pl.inpost.recruitmenttask.ui.shipment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.source.local.CustomSharedPreferences
import pl.inpost.recruitmenttask.data.source.model.AdapterItem
import pl.inpost.recruitmenttask.data.source.model.ShipmentStatus
import pl.inpost.recruitmenttask.data.source.repository.ShipmentsRepository
import pl.inpost.recruitmenttask.utils.setState
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val shipmentsRepository: ShipmentsRepository,
    private val sharedPreferences: CustomSharedPreferences
) : ViewModel() {

    private val _initViewState = MutableLiveData<List<AdapterItem.Shipment>>(emptyList())
    val initViewState: LiveData<List<AdapterItem.Shipment>>
        get() = _initViewState

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private var currentFiltering = ShipmentStatus.ALL

    private val _getRawJson = MutableLiveData<Boolean>()
    val getRawJson: LiveData<Boolean>
        get() = _getRawJson

    private var rawJson: String? = null

    init {
        _dataLoading.value = true
        if (!sharedPreferences.isActionExecuted(FIRST_TIME_DATA_LOAD)) {
            initJson()
            sharedPreferences.setActionExecuted(FIRST_TIME_DATA_LOAD)
            rawJson = null
        } else {
            loadAllShipments()
        }
    }

    //only needed when raw json exist
    private fun initJson() {
        _getRawJson.value = true
    }

    private fun filterItems(
        shipments: List<AdapterItem.Shipment>
    ): List<AdapterItem.Shipment> {
        val shipmentsToShow = ArrayList<AdapterItem.Shipment>()
        val filteringTypeName = currentFiltering.name
        for (shipment in shipments) {
            val statusName = ShipmentStatus.valueOfOrNull(shipment.status)?.name
            when (currentFiltering) {
                ShipmentStatus.ALL -> {
                    shipmentsToShow.clear()
                    shipmentsToShow.addAll(shipments)
                }

                else -> {
                    if (statusName.equals(filteringTypeName)) shipmentsToShow.add(shipment)
                }
            }
        }
        return shipmentsToShow
    }

    private fun initData() {
        viewModelScope.launch {
            val shipments = shipmentsRepository.getShipments(json = rawJson)
            if (shipments is Result.Success) {
                _initViewState.setState { shipments.data }
            } else if (shipments is Result.Error) {
                throw shipments.exception
            }
            delay(300L)
            _dataLoading.value = false
        }
    }

    private fun setFiltering(requestType: ShipmentStatus) {
        _dataLoading.value = true
        currentFiltering = requestType
        loadShipments()
    }


    fun showReadyToPickUp() {
        setFiltering(ShipmentStatus.READY_TO_PICKUP)
    }

    fun showOtherShipments() {
        setFiltering(ShipmentStatus.OTHER)
    }

    fun loadAllShipments() {
        setFiltering(ShipmentStatus.ALL)
    }

    fun showArchived() {
        viewModelScope.launch {
            val archived = shipmentsRepository.getArchived()
            if (archived is Result.Success) {
                _initViewState.setState { archived.data }
            } else if (archived is Result.Error) {
                throw archived.exception
            }
            //delay  is used only to simulate a longer response from the API to show progress loader
            delay(500L)
            _dataLoading.value = false
        }
    }

    private fun loadShipments() {
        viewModelScope.launch {
            val shipments = shipmentsRepository.getShipments(null)
            if (shipments is Result.Success) {
                _initViewState.setState { filterItems(shipments.data) }
            } else if (shipments is Result.Error) {
                throw shipments.exception
            }
            delay(500L)
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

    companion object {
        const val FIRST_TIME_DATA_LOAD = "FIRST_TIME_DATA_LOAD"
    }
}
