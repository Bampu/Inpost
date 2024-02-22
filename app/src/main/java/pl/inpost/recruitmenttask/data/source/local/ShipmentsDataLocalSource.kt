package pl.inpost.recruitmenttask.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pl.inpost.recruitmenttask.data.Result.Success
import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.Result.Error
import pl.inpost.recruitmenttask.data.source.model.Shipment
import javax.inject.Inject

class ShipmentsDataLocalSource @Inject constructor(
    private val shipmentsDao: ShipmentsDao
) : LocalDataSource {
    override fun observeShipments(): LiveData<Result<List<Shipment>>> {
        return shipmentsDao.observeShipments().map {
            Success(it)
        }
    }

    override suspend fun getShipments(): Result<List<Shipment>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Success(shipmentsDao.getShipments())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun deleteAllTasks() {
        shipmentsDao.deleteShipments()
    }

    override suspend fun saveShipment(shipment: Shipment) {
        shipmentsDao.insertShipment(shipment)
    }
}