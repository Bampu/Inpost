package pl.inpost.recruitmenttask.data.source

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import pl.inpost.recruitmenttask.data.Result
import pl.inpost.recruitmenttask.data.source.model.EventLog
import pl.inpost.recruitmenttask.data.source.model.Operations
import pl.inpost.recruitmenttask.data.source.model.Receiver
import pl.inpost.recruitmenttask.data.source.model.Sender
import pl.inpost.recruitmenttask.data.source.model.Shipment
import pl.inpost.recruitmenttask.data.source.model.ShipmentStatus
import pl.inpost.recruitmenttask.data.source.model.ShipmentType
import java.time.ZonedDateTime
import kotlin.random.Random

class MockShipmentApi(
    @ApplicationContext private val context: Context,
    apiTypeAdapter: ApiTypeAdapter
) : ShipmentApi {


    override suspend fun getShipments(): Result<List<Shipment>> {
        delay(1000)
        return Result.Success(arrayListOf(mockShipmentNetwork()))
    }

    companion object {
        fun mockShipmentNetwork(
            number: String = Random.nextLong(1, 9999_9999_9999_9999).toString(),
            type: ShipmentType = ShipmentType.PARCEL_LOCKER,
            status: ShipmentStatus = ShipmentStatus.DELIVERED,
            sender: Sender = mockSender(),
            receiver: Receiver = mockReceiver(),
            operations: Operations = mockOperationsNetwork(),
            eventLog: List<EventLog> = emptyList(),
            openCode: String = "33333",
            expireDate: ZonedDateTime = mockDate(),
            storedDate: ZonedDateTime = mockDate(),
            pickupDate: ZonedDateTime = mockDate()
        ) = Shipment(
            number = number,
            shipmentType = type.name,
            status = status.name,
            eventLog = eventLog,
            openCode = openCode,
            expiryDate = expireDate.toString(),
            storedDate = storedDate.toString(),
            pickUpDate = pickupDate.toString(),
            receiver = receiver,
            sender = sender,
            operations = operations
        )

        private fun mockReceiver(
            email: String = "name@email.com",
            phoneNumber: String = "123 123 123",
            name: String = "Jan Kowalski"
        ) = Receiver(
            email = email,
            phoneNumber = phoneNumber,
            name = name
        )

        private fun mockSender(
            email: String = "sender@email.com",
            phoneNumber: String = "123 123 123",
            name: String = "Jan Kowalski"
        ) = Sender(
            email = email,
            phoneNumber = phoneNumber,
            name = name
        )

        private fun mockDate(): ZonedDateTime {
            val dateString = "2024-02-01T12:30:00+02:00"
            return ApiTypeAdapter.toZonedDateTime(dateString)
        }

        private fun mockOperationsNetwork(
            manualArchive: Boolean = false,
            delete: Boolean = false,
            collect: Boolean = false,
            highlight: Boolean = false,
            expandAvizo: Boolean = false,
            endOfWeekCollection: Boolean = false
        ) = Operations(
            manualArchive = manualArchive,
            delete = delete,
            collect = collect,
            highlight = highlight,
            expandAvizo = expandAvizo,
            endOfWeekCollection = endOfWeekCollection
        )
    }
}
