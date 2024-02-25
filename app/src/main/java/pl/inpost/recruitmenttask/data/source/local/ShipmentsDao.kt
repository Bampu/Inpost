package pl.inpost.recruitmenttask.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.inpost.recruitmenttask.data.source.model.Shipment

@Dao
interface ShipmentsDao {

    @Query("SELECT * FROM Shipments")
    fun observeShipments(): LiveData<List<Shipment>>

    @Query("SELECT * FROM Shipments")
    suspend fun getShipments(): List<Shipment>

    @Query("SELECT * FROM Shipments WHERE isArchived = :paramValue")
    suspend fun getArchived(paramValue: Boolean): List<Shipment>

    @Query("DELETE FROM Shipments")
    suspend fun deleteShipments()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShipment(shipment: Shipment)
}