package pl.inpost.recruitmenttask.data.source.repository

import androidx.lifecycle.LiveData
import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.source.model.AdapterItem

interface ShipmentsRepository {
    suspend fun getShipments(json: String?): Result<List<AdapterItem.Shipment>>
    suspend fun getArchived(): Result<List<AdapterItem.Shipment>>

    suspend fun archiveShipment(archive: AdapterItem.Shipment)
    fun observeShipments(): LiveData<Result<List<AdapterItem.Shipment>>>
    suspend fun refreshShipments(json: String?)
    suspend fun deleteAllShipments()
}