package pl.inpost.recruitmenttask

import androidx.lifecycle.LiveData
import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.source.model.AdapterItem
import pl.inpost.recruitmenttask.data.source.model.ShipmentRemote
import pl.inpost.recruitmenttask.data.source.remote.RemoteDataSource

class FakeRemoteDataSource(
    private var shipments: MutableList<AdapterItem.Shipment>? = mutableListOf()
) : RemoteDataSource {
    override suspend fun getShipments(json: String?): Result<ShipmentRemote>? {
        shipments?.let {
            return Result.Success(ShipmentRemote(it as ArrayList))
        } ?: return null
    }

    override fun observeShipments(): LiveData<Result<List<AdapterItem.Shipment>>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllShipments() {
        TODO("Not yet implemented")
    }

    override suspend fun saveShipment(shipment: AdapterItem.Shipment) {
        TODO("Not yet implemented")
    }
}