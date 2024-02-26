package pl.inpost.recruitmenttask.ui.shipment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Before
import org.junit.Rule
import pl.inpost.recruitmenttask.data.source.model.EventLog
import pl.inpost.recruitmenttask.data.source.model.Operations
import pl.inpost.recruitmenttask.data.source.model.Receiver
import pl.inpost.recruitmenttask.data.source.model.Sender
import pl.inpost.recruitmenttask.repository.FakeShipmentRepository

class ShipmentListViewModelTest {

    private lateinit var viewModel: ShipmentListViewModel
    private lateinit var shipmentsRepository: FakeShipmentRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        shipmentsRepository = FakeShipmentRepository()

        val shipment1 = Shipment(
            arrayListOf(EventLog("date", "name")),
            "expiry date",
            "112313213121",
            "openCode",
            Operations(
                false,
                false,
                false,
                false,
                false,
                false
            ),
            "pickUpDate",
            Receiver("email", "name", "898888888"),
            Sender("email", "phone", "884482882"),
            "shipmentType",
            "status",
            "storedDate",
            false
        )

        val shipment2 = Shipment(
            arrayListOf(EventLog("date2", "name2")),
            "expiry date2",
            "22222222222",
            "openCode2",
            Operations(
                false,
                false,
                false,
                false,
                false,
                false
            ),
            "pickUpDate2",
            Receiver("email2", "name2", "898888888"),
            Sender("email2", "phone2", "884482882"),
            "shipmentType2",
            "status2",
            "storedDate2",
            false
        )

        shipmentsRepository.addShipments(shipment1, shipment2)

        viewModel = ShipmentListViewModel(shipmentsRepository)
    }
}