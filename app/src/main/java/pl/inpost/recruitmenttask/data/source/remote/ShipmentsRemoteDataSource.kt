package pl.inpost.recruitmenttask.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.Result.Success
import pl.inpost.recruitmenttask.data.Result.Error
import pl.inpost.recruitmenttask.data.source.model.AdapterItem
import pl.inpost.recruitmenttask.data.source.model.ShipmentRemote
import javax.inject.Inject

class ShipmentsRemoteDataSource @Inject constructor(
    private val apiService: ShipmentApi
) : RemoteDataSource {

    private val observableShipments = MutableLiveData<Result<List<AdapterItem.Shipment>>>()
    override fun observeShipments(): LiveData<Result<List<AdapterItem.Shipment>>> {
        return observableShipments
    }

    override suspend fun getShipments(json: String?): Result<ShipmentRemote>? {
        //For that test task we do not fetch data from api - here is placeholder for that case
        // in other case we would use coroutines as suspend fun with endpoint to api
        //apiService.getShipments() - potential api call
        json?.let {
            val shipments = Gson().fromJson(json, ShipmentRemote::class.java)
            return Success(shipments)
        } ?: return null
    }

    override suspend fun deleteAllShipments() {
    }

    override suspend fun saveShipment(shipment: AdapterItem.Shipment) {
    }
}