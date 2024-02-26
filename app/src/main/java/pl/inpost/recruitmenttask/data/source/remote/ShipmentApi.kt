package pl.inpost.recruitmenttask.data.source.remote

import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.source.model.AdapterItem
import retrofit2.http.GET

interface ShipmentApi {
    @GET("your/endpoint")
    suspend fun getShipments(): Result<List<AdapterItem.Shipment>>
}
