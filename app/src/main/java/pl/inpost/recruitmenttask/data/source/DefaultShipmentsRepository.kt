package pl.inpost.recruitmenttask.data.source

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.Result.Error
import pl.inpost.recruitmenttask.data.Result.Success
import pl.inpost.recruitmenttask.data.source.local.LocalDataSource
import pl.inpost.recruitmenttask.data.source.model.AdapterItem
import pl.inpost.recruitmenttask.data.source.remote.RemoteDataSource
import pl.inpost.recruitmenttask.data.source.repository.ShipmentsRepository
import javax.inject.Inject

class DefaultShipmentsRepository @Inject constructor(
    private val shipmentLocalDataSource: LocalDataSource,
    private val shipmentsRemoteDataSource: RemoteDataSource,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ShipmentsRepository {


    override suspend fun getShipments(json: String?): Result<List<AdapterItem.Shipment>> {
        json?.let {
            try {
                updateShipmentsFromRemoteDataSource(json)
            } catch (ex: Exception) {
                return Error(ex)
            }
        }
        return shipmentLocalDataSource.getShipments()
    }

    override suspend fun getArchived(): Result<List<AdapterItem.Shipment>> {
        return try {
            shipmentLocalDataSource.getArchived()
        } catch (e: Exception) {
            return Error(e)
        }
    }

    override suspend fun archiveShipment(archive: AdapterItem.Shipment) {
        shipmentLocalDataSource.archiveShipment(archive)
    }

    private suspend fun updateShipmentsFromRemoteDataSource(json: String?) {
        val remoteShipments = shipmentsRemoteDataSource.getShipments(json)

        if (remoteShipments is Success) {
            shipmentLocalDataSource.deleteAllShipments()
            remoteShipments.data.shipments.forEach { task ->
                shipmentLocalDataSource.saveShipment(task)
            }
        } else if (remoteShipments is Error) {
            throw remoteShipments.exception
        }
    }

    override fun observeShipments(): LiveData<Result<List<AdapterItem.Shipment>>> {
        return shipmentLocalDataSource.observeShipments()
    }

    override suspend fun refreshShipments(json: String?) {
        updateShipmentsFromRemoteDataSource(json)
    }

    override suspend fun deleteAllShipments() {
        withContext(Dispatchers.IO) {
            coroutineScope {
                launch { shipmentsRemoteDataSource.deleteAllShipments() }
                launch { shipmentLocalDataSource.deleteAllShipments() }
            }
        }
    }
}