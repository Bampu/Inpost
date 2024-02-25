package pl.inpost.recruitmenttask.data.source.repository

import androidx.lifecycle.LiveData
import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.source.model.Shipment

interface ShipmentsRepository {
    suspend fun getShipments(json: String?): Result<List<Shipment>>
    suspend fun getArchived(): Result<List<Shipment>>
    fun observeShipments(): LiveData<Result<List<Shipment>>>
    suspend fun refreshShipments(json: String?)
    suspend fun deleteAllShipments()
}