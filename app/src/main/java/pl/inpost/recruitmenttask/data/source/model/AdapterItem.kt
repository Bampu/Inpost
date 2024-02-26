package pl.inpost.recruitmenttask.data.source.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.inpost.recruitmenttask.utils.EMPTY_STRING

sealed class AdapterItem {
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
    ) : AdapterItem()

    data class ReadyForPickupItem(val data: String = EMPTY_STRING) : AdapterItem()
    data class RemainingItem(val data: String = EMPTY_STRING) : AdapterItem()
}