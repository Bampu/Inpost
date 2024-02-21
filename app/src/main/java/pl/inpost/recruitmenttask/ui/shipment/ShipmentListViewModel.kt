package pl.inpost.recruitmenttask.ui.shipment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.source.ShipmentApi
import pl.inpost.recruitmenttask.data.source.model.Shipment
import pl.inpost.recruitmenttask.utils.setState
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val shipmentApi: ShipmentApi
) : ViewModel() {

    private val _mutableViewState = MutableLiveData<List<Shipment>>(emptyList())
    val viewState: LiveData<List<Shipment>>
        get() = _mutableViewState

    init {
        refreshData()
    }

    private fun refreshData() {
        viewModelScope.launch {
            val shipments = shipmentApi.getShipments()
            if (shipments is Result.Success) {
                _mutableViewState.setState { shipments.data }
            } else if (shipments is Result.Error) {
                throw shipments.exception
            }
        }
    }
}
