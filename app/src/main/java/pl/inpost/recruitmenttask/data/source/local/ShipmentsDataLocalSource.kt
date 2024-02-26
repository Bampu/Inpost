package pl.inpost.recruitmenttask.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pl.inpost.recruitmenttask.data.Result.Success
import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.Result.Error
import pl.inpost.recruitmenttask.data.source.model.AdapterItem
import javax.inject.Inject

class ShipmentsDataLocalSource @Inject constructor(
    private val shipmentsDao: ShipmentsDao
) : LocalDataSource {
    override fun observeShipments(): LiveData<Result<List<AdapterItem.Shipment>>> {
        return shipmentsDao.observeShipments().map {
            Success(it)
        }
    }

    override suspend fun getShipments(): Result<List<AdapterItem.Shipment>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                Success(shipmentsDao.getShipments())
            } catch (e: Exception) {
                Error(e)
            }
        }

    override suspend fun getArchived(): Result<List<AdapterItem.Shipment>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                Success(shipmentsDao.getArchived())
            } catch (e: Exception) {
                Error(e)
            }
        }

    override suspend fun deleteAllShipments() {
        shipmentsDao.deleteShipments()
    }

    override suspend fun saveShipment(shipment: AdapterItem.Shipment) {
        shipmentsDao.insertShipment(shipment)
    }

    override suspend fun archiveShipment(archive: AdapterItem.Shipment) {
        shipmentsDao.removeShipmentByNumber(archive.number)
        shipmentsDao.insertShipment(archive)
    }
}