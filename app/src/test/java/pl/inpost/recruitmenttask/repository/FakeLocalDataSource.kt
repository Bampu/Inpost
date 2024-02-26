package pl.inpost.recruitmenttask.repository

import androidx.lifecycle.LiveData
import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.source.local.LocalDataSource
import pl.inpost.recruitmenttask.data.source.model.AdapterItem
import java.lang.Exception

class FakeLocalDataSource(
    private var shipments: MutableList<AdapterItem.Shipment>? = mutableListOf()
): LocalDataSource {
    override suspend fun getShipments(): Result<List<AdapterItem.Shipment>> {
        shipments?.let {
            return Result.Success(it)
        } ?: return Result.Error(Exception("Shipments not found"))
    }

    override suspend fun getArchived(): Result<List<AdapterItem.Shipment>> {
        TODO("Not yet implemented")
    }

    override suspend fun archiveShipment(archive: AdapterItem.Shipment) {
        TODO("Not yet implemented")
    }

    override fun observeShipments(): LiveData<Result<List<AdapterItem.Shipment>>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllShipments() {
        shipments?.clear()
    }

    override suspend fun saveShipment(shipment: AdapterItem.Shipment) {
        shipments?.add(shipment)
    }
}