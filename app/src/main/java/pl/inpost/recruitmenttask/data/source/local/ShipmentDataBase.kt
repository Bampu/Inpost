package pl.inpost.recruitmenttask.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.inpost.recruitmenttask.data.source.model.AdapterItem
import pl.inpost.recruitmenttask.utils.DataClassConverter

@Database(entities = [AdapterItem.Shipment::class], version = 1, exportSchema = false)
@TypeConverters(DataClassConverter::class)
abstract class ShipmentDataBase : RoomDatabase() {
    abstract fun shipmentDao(): ShipmentsDao
}