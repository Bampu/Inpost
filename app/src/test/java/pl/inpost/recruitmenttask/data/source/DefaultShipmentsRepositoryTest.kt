package pl.inpost.recruitmenttask.data.source

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test
import pl.inpost.recruitmenttask.FakeRemoteDataSource
import pl.inpost.recruitmenttask.data.Result.Success
import pl.inpost.recruitmenttask.data.source.model.AdapterItem
import pl.inpost.recruitmenttask.data.source.model.EventLog
import pl.inpost.recruitmenttask.data.source.model.Operations
import pl.inpost.recruitmenttask.data.source.model.Receiver
import pl.inpost.recruitmenttask.data.source.model.Sender
import pl.inpost.recruitmenttask.data.source.model.ShipmentStatus
import pl.inpost.recruitmenttask.data.source.repository.ShipmentsRepository
import pl.inpost.recruitmenttask.repository.FakeLocalDataSource

@ExperimentalCoroutinesApi
class DefaultShipmentsRepositoryTest {

    private val shipment1 = generateShipmentWithStatus(ShipmentStatus.DELIVERED)
    private val shipment2 = generateShipmentWithStatus(ShipmentStatus.OUT_FOR_DELIVERY)
    private val shipment3 = generateShipmentWithStatus()
    private val remoteShipments = listOf(shipment1, shipment2).sortedBy { it.number }
    private val localShipments = listOf(shipment3).sortedBy { it.number }
    private val newShipment = listOf(shipment3).sortedBy { it.number }


    private lateinit var shipmentsRemoteRepository: FakeRemoteDataSource
    private lateinit var shipmentsLocalRepository: FakeLocalDataSource
    private lateinit var shipmentsRepository: ShipmentsRepository

    @Before
    fun createRepository() {

        shipmentsRemoteRepository = FakeRemoteDataSource(remoteShipments.toMutableList())
        shipmentsLocalRepository = FakeLocalDataSource(localShipments.toMutableList())
        shipmentsRepository = DefaultShipmentsRepository(
            shipmentLocalDataSource = shipmentsLocalRepository,
            shipmentsRemoteDataSource = shipmentsRemoteRepository,
            defaultDispatcher = Dispatchers.Unconfined
        )
    }

    @Test
    fun getShipments_requestAllShipmentsLocalDataSource() = runTest {
        val shipments = shipmentsRepository.getShipments(null) as Success
        advanceUntilIdle()
        assertThat(shipments.data, IsEqual(localShipments))
    }

    @Test
    fun getShipments_requestAllShipmentsRemoteDataSource() = runTest {
        val shipments = shipmentsRepository.getShipments("json") as Success
        advanceUntilIdle()
        assertThat(shipments.data, IsEqual(remoteShipments))
    }

    private fun generateShipmentWithStatus(status: ShipmentStatus = ShipmentStatus.READY_TO_PICKUP): AdapterItem.Shipment {
        return AdapterItem.Shipment(
            arrayListOf(EventLog("date", "name")),
            "expiry date",
            "112313213121",
            "openCode",
            Operations(
                true,
                false,
                false,
                false,
                true,
                false
            ),
            "pickUpDate",
            Receiver("email", "name", "898888888"),
            Sender("email", "phone", "884482882"),
            "shipmentType",
            status.name,
            "storedDate",
            false
        )
    }
}