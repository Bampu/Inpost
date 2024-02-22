package pl.inpost.recruitmenttask.data.source

import androidx.lifecycle.LiveData
import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.source.model.Shipment

interface ShipmentsDataSource {
    fun observeShipments(): LiveData<Result<List<Shipment>>>
    suspend fun deleteAllTasks()
    suspend fun saveShipment(shipment: Shipment)
}