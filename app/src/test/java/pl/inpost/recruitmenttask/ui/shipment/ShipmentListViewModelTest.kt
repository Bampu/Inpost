package pl.inpost.recruitmenttask.ui.shipment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.inpost.recruitmenttask.MockSharedPreferencesManager
import pl.inpost.recruitmenttask.TestUtils.Companion.isValidJson
import pl.inpost.recruitmenttask.data.source.model.AdapterItem
import pl.inpost.recruitmenttask.data.source.model.EventLog
import pl.inpost.recruitmenttask.data.source.model.Operations
import pl.inpost.recruitmenttask.data.source.model.Receiver
import pl.inpost.recruitmenttask.data.source.model.Sender
import pl.inpost.recruitmenttask.data.source.model.ShipmentStatus
import pl.inpost.recruitmenttask.getOrAwaitValue
import pl.inpost.recruitmenttask.repository.FakeShipmentRepository

@ExperimentalCoroutinesApi
class ShipmentListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ShipmentListViewModel
    private lateinit var shipmentsRepository: FakeShipmentRepository
    private lateinit var sharedPreferences: MockSharedPreferencesManager

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getCurrentFiltering_setAll_checkIfStateIsCorrect() {
        val allStatus = ShipmentStatus.ALL
        viewModel.loadAllShipments()
        assertThat(viewModel.getCurrentFiltering(), `is`(allStatus))
    }

    @Test
    fun getCurrentFiltering_setReadyToPickup_checkIfStateIsCorrect() {
        val allStatus = ShipmentStatus.READY_TO_PICKUP
        viewModel.showReadyToPickUp()
        assertThat(viewModel.getCurrentFiltering(), `is`(allStatus))
    }

    @Test
    fun getCurrentFiltering_setFilterAsNull_checkIfStateIsCorrect() {
        viewModel.showRemainingShipments()
        assertThat(viewModel.getCurrentFiltering(), `is`(nullValue()))
    }

    @Test
    fun setValidRawJson_checkIfIsCorrect_shouldReturnTrue() {
        val jsonString = """{ "key": "value" }"""
        viewModel.setRawJson(jsonString)
        assertTrue(isValidJson(viewModel.getRawJson()))
    }

    @Test
    fun setInvalidRawJson_checkIfCorrect_shouldReturnFalse() {
        val jsonString = """{ "key": "value", "missing": }"""
        viewModel.setRawJson(jsonString)
        assertFalse(isValidJson(viewModel.getRawJson()))
    }

    @Test
    fun showArchived_shouldNotBeEmpty() = runTest {
        viewModel.showArchived()
        advanceUntilIdle()
        assertThat(viewModel.items.getOrAwaitValue(), not(emptyList()))
    }

    @Test
    fun getAllShipments_shouldNotBeEmpty() = runTest {
        viewModel.loadAllShipments()
        advanceUntilIdle()
        assertThat(viewModel.items.getOrAwaitValue(), not(emptyList()))
    }

    @Test
    fun getShipments_setReadyToPickUpFilter_assertThatAnyExists() = runTest {
        viewModel.showReadyToPickUp()
        advanceUntilIdle()
        val items = viewModel.items.getOrAwaitValue()
        val containsReadyToPickUp = items.any { it.status == ShipmentStatus.READY_TO_PICKUP.name }
        assertTrue(containsReadyToPickUp)
    }

    @Before
    fun setupViewModel() {
        shipmentsRepository = FakeShipmentRepository()
        sharedPreferences = MockSharedPreferencesManager()

        Dispatchers.setMain(StandardTestDispatcher())

        val shipment1 = generateShipmentWithStatus(ShipmentStatus.DELIVERED)
        val shipment2 = generateShipmentWithStatus(ShipmentStatus.READY_TO_PICKUP)

        shipmentsRepository.addShipments(shipment1, shipment2)
        shipmentsRepository.addArchived(shipment1, shipment2)
        viewModel = ShipmentListViewModel(shipmentsRepository, sharedPreferences)
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