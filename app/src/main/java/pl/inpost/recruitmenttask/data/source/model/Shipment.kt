package pl.inpost.recruitmenttask.data.source.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shipments")
data class Shipment(
    val eventLog: List<EventLog>?,
    val expiryDate: String?,
    @PrimaryKey val number: String,
    val openCode: String?,
    val operations: Operations?,
    val pickUpDate: String?,
    val receiver: Receiver?,
    val sender: Sender?,
    val shipmentType: String?,
    val status: String?,
    val storedDate: String?,
    val isArchived: Boolean = false
)