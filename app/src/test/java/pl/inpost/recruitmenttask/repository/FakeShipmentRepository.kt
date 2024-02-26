package pl.inpost.recruitmenttask.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.runBlocking
import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.source.model.AdapterItem
import pl.inpost.recruitmenttask.data.source.repository.ShipmentsRepository

class FakeShipmentRepository : ShipmentsRepository {
    val shipmentsServiceData: LinkedHashMap<String, AdapterItem.Shipment> = LinkedHashMap()
    val archivedServiceData: LinkedHashMap<String, AdapterItem.Shipment> = LinkedHashMap()
    private val observableShipments = MutableLiveData<Result<List<AdapterItem.Shipment>>>()
    override suspend fun getShipments(json: String?): Result<List<AdapterItem.Shipment>> {
        return Result.Success(shipmentsServiceData.values.toList())
    }

    override suspend fun getArchived(): Result<List<AdapterItem.Shipment>> {
        return Result.Success(archivedServiceData.values.toList())
    }

    override suspend fun archiveShipment(archive: AdapterItem.Shipment) {
        addArchived(archive)
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
            shipmentsServiceData[shipment.number] = shipment
        }
        runBlocking { refreshShipments(null) }
    }

    fun addArchived(vararg archived: AdapterItem.Shipment) {
        for (archive in archived) {
            archivedServiceData[archive.number] = archive
        }
    }
}