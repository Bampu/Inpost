package pl.inpost.recruitmenttask.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.runBlocking
import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.source.model.AdapterItem
import pl.inpost.recruitmenttask.data.source.repository.ShipmentsRepository

class FakeShipmentRepository : ShipmentsRepository {
    val tasksServiceData: LinkedHashMap<String, AdapterItem.Shipment> = LinkedHashMap()
    private val observableShipments = MutableLiveData<Result<List<AdapterItem.Shipment>>>()
    override suspend fun getShipments(json: String?): Result<List<AdapterItem.Shipment>> {
        return Result.Success(tasksServiceData.values.toList())
    }

    override suspend fun getArchived(): Result<List<AdapterItem.Shipment>> {
        TODO("Not yet implemented")
    }

    override suspend fun archiveShipment(archive: AdapterItem.Shipment) {
        TODO("Not yet implemented")
    }

    override fun observeShipments(): LiveData<Result<List<AdapterItem.Shipment>>> {
        runBlocking {
            refreshShipments(null)
        }
        return observableShipments
    }

    override suspend fun refreshShipments(json: String?) {
        observableShipments.value = getShipments(json)
    }

    override suspend fun deleteAllShipments() {
        TODO("Not yet implemented")
    }

    fun addShipments(vararg shipments: AdapterItem.Shipment) {
        for (shipment in shipments) {
            tasksServiceData[shipment.number] = shipment
        }
        runBlocking { refreshShipments(null) }
    }
}