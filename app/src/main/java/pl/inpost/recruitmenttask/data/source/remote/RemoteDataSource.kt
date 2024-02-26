package pl.inpost.recruitmenttask.data.source.remote

import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.source.model.ShipmentRemote
import pl.inpost.recruitmenttask.data.source.ShipmentsDataSource

interface RemoteDataSource : ShipmentsDataSource {
    suspend fun getShipments(json: String?): Result<ShipmentRemote>?
}