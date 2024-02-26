package pl.inpost.recruitmenttask.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.inpost.recruitmenttask.data.source.model.AdapterItem

@Dao
interface ShipmentsDao {

    @Query("SELECT * FROM Shipments")
    fun observeShipments(): LiveData<List<AdapterItem.Shipment>>

    @Query("SELECT * FROM Shipments WHERE isArchived = :paramValue")
    suspend fun getShipments(paramValue: Boolean = false): List<AdapterItem.Shipment>

    @Query("SELECT * FROM Shipments WHERE isArchived = :paramValue")
    suspend fun getArchived(paramValue: Boolean = true): List<AdapterItem.Shipment>

    @Query("DELETE FROM Shipments")
    suspend fun deleteShipments()

    @Query("DELETE FROM Shipments WHERE number = :number")
    suspend fun removeShipmentByNumber(number: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShipment(shipment: AdapterItem.Shipment)
}