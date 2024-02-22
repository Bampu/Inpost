package pl.inpost.recruitmenttask.data.source

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.Result.Error
import pl.inpost.recruitmenttask.data.Result.Success
import pl.inpost.recruitmenttask.data.source.local.LocalDataSource
import pl.inpost.recruitmenttask.data.source.model.Shipment
import pl.inpost.recruitmenttask.data.source.remote.RemoteDataSource
import pl.inpost.recruitmenttask.data.source.repository.ShipmentsRepository
import javax.inject.Inject

class DefaultShipmentsRepository @Inject constructor(
    private val shipmentLocalDataSource: LocalDataSource,
    private val shipmentsRemoteDataSource: RemoteDataSource
) : ShipmentsRepository {
    override suspend fun getShipments(json: String?): Result<List<Shipment>> {
        try {
            updateTasksFromRemoteDataSource(json)
        } catch (ex: Exception) {
            return Error(ex)
        }
        return shipmentLocalDataSource.getShipments()
    }

    private suspend fun updateTasksFromRemoteDataSource(json: String?) {

        val remoteTasks = shipmentsRemoteDataSource.getShipments(json)

        if (remoteTasks is Success) {
            shipmentLocalDataSource.deleteAllTasks()
            remoteTasks.data.shipments.forEach { task ->
                shipmentLocalDataSource.saveShipment(task)
            }
        } else if (remoteTasks is Error) {
            throw remoteTasks.exception
        }
    }

    override fun observeShipments(): LiveData<Result<List<Shipment>>> {
        return shipmentLocalDataSource.observeShipments()
    }

    override suspend fun refreshShipments(json: String?) {
        updateTasksFromRemoteDataSource(json)
    }

    override suspend fun deleteAllTasks() {
        withContext(Dispatchers.IO) {
            coroutineScope {
                launch { shipmentsRemoteDataSource.deleteAllTasks() }
                launch { shipmentLocalDataSource.deleteAllTasks() }
            }
        }
    }
}