package pl.inpost.recruitmenttask.data.source

import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.source.model.Shipment

interface ShipmentApi {
    suspend fun getShipments(): Result<List<Shipment>>
}
