package pl.inpost.recruitmenttask.ui.shipment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.Event
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.source.repository.ShipmentsRepository
import pl.inpost.recruitmenttask.data.source.model.Shipment
import pl.inpost.recruitmenttask.data.source.model.ShipmentStatus
import pl.inpost.recruitmenttask.utils.setState
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val shipmentsRepository: ShipmentsRepository
) : ViewModel() {

    private val _initViewState = MutableLiveData<List<Shipment>>(emptyList())
    val initViewState: LiveData<List<Shipment>>
        get() = _initViewState

    private val _forceUpdate = MutableLiveData<Boolean>(false)

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val isDataLoadingError = MutableLiveData<Boolean>()


    private var currentFiltering = ShipmentStatus.ALL

    private val _items: LiveData<List<Shipment>> = _forceUpdate.switchMap { forceUpdate ->
        if (forceUpdate) {
            _dataLoading.value = true
            viewModelScope.launch {
                shipmentsRepository.refreshShipments(json = rawJson)
                _dataLoading.value = false
            }
        }
        shipmentsRepository.observeShipments().switchMap { filterShipments(it) }
    }

    val items: LiveData<List<Shipment>> = _items


    private val _getRawJson = MutableLiveData<Boolean>()
    val getRawJson: LiveData<Boolean>
        get() = _getRawJson

    private var rawJson: String? = null

    init {
        initJson()
    }

    //only needed when raw json exist
    private fun initJson() {
        _getRawJson.value = true
    }

    private fun filterShipments(tasksResult: Result<List<Shipment>>): LiveData<List<Shipment>> {
        val result = MutableLiveData<List<Shipment>>()

        if (tasksResult is Result.Success) {
            isDataLoadingError.value = false
            viewModelScope.launch {
                result.value = filterItems(tasksResult.data, currentFiltering)
            }
        } else {
            result.value = emptyList()
            isDataLoadingError.value = true
        }

        return result
    }

    private fun filterItems(tasks: List<Shipment>, filteringType: ShipmentStatus): List<Shipment> {
        val tasksToShow = ArrayList<Shipment>()
        val filteringTypeName = filteringType.name
        // We filter the tasks based on the requestType
        for (task in tasks) {
            val statusName = ShipmentStatus.valueOfOrNull(task.status)?.name
            when (filteringType) {
                ShipmentStatus.ALL -> {
                    tasksToShow.clear()
                    tasksToShow.addAll(tasks)
                }

                else -> {/* just show all */
                    if (statusName.equals(filteringTypeName)) tasksToShow.add(task)
                }
            }
        }
        return tasksToShow
    }

    private fun initData() {
        viewModelScope.launch {
            val shipments = shipmentsRepository.getShipments(json = rawJson)
            if (shipments is Result.Success) {
                _initViewState.setState { shipments.data }
            } else if (shipments is Result.Error) {
                throw shipments.exception
            }
        }
    }

    private fun setFiltering(requestType: ShipmentStatus, forceUpdate: Boolean = false) {
        currentFiltering = requestType
        loadShipments(forceUpdate)
    }


    fun showReadyToPickUp() {
        setFiltering(ShipmentStatus.READY_TO_PICKUP)
    }

    fun showOtherShipments() {
        setFiltering(ShipmentStatus.OTHER)
    }

    fun loadAllShipments() {
        setFiltering(ShipmentStatus.ALL, forceUpdate = true)
    }

    private fun loadShipments(forceUpdate: Boolean) {
        _forceUpdate.value = forceUpdate
    }

    fun setRawJson(json: String?) {
        this.rawJson = json
        initData()
    }
}
