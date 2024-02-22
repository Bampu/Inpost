package pl.inpost.recruitmenttask.data.source.local

import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.source.ShipmentsDataSource
import pl.inpost.recruitmenttask.data.source.model.Shipment

interface LocalDataSource : ShipmentsDataSource {
    suspend fun getShipments(): Result<List<Shipment>>
}