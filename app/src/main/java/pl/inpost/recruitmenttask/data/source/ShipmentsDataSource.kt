package pl.inpost.recruitmenttask.data.source

import androidx.lifecycle.LiveData
import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.source.model.AdapterItem

interface ShipmentsDataSource {
    fun observeShipments(): LiveData<Result<List<AdapterItem.Shipment>>>
    suspend fun deleteAllShipments()
    suspend fun saveShipment(shipment: AdapterItem.Shipment)
}