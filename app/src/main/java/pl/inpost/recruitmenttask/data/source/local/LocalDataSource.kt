package pl.inpost.recruitmenttask.data.source.local

import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.source.ShipmentsDataSource
import pl.inpost.recruitmenttask.data.source.model.AdapterItem

interface LocalDataSource : ShipmentsDataSource {
    suspend fun getShipments(): Result<List<AdapterItem.Shipment>>
    suspend fun getArchived(): Result<List<AdapterItem.Shipment>>

    suspend fun archiveShipment(archive: AdapterItem.Shipment)
}