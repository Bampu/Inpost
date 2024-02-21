package pl.inpost.recruitmenttask.data.source.model

data class Shipment(
    val eventLog: List<EventLog>,
    val expiryDate: String,
    val number: String,
    val openCode: String,
    val operations: Operations,
    val pickUpDate: String,
    val receiver: Receiver,
    val sender: Sender,
    val shipmentType: String,
    val status: String,
    val storedDate: String
)