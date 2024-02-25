package pl.inpost.recruitmenttask.ui.shipment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.databinding.FragmentShipmentListBinding
import pl.inpost.recruitmenttask.utils.gone
import pl.inpost.recruitmenttask.utils.visible

@AndroidEntryPoint
class ShipmentListFragment : Fragment() {

    private val viewModel: ShipmentListViewModel by viewModels()
    private var _binding: FragmentShipmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var shipmentAdapter: ShipmentListAdapter
    private lateinit var noResultsFound: TextView
    private lateinit var progressLoader: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shipment_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.readyToPickupShipmentsMenu -> {
            viewModel.showReadyToPickUp()
            true
        }

        R.id.otherShipmentsMenu -> {
            viewModel.showOtherShipments()
            true
        }

        R.id.allShipmentsMenu -> {
            viewModel.loadAllShipments()
            true
        }

        R.id.archived -> {
            viewModel.showArchived()
            true
        }

        else -> false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShipmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListeners() {
        viewModel.initViewState.observe(viewLifecycleOwner) {
            shipmentAdapter.setData(it)
        }
        viewModel.items.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                shipmentAdapter.setData(it)
                hideFilterInfo()
            } else {
                showEmptyFilterInfo()
            }
        }
        viewModel.dataLoading.observe(viewLifecycleOwner) {
            when(it) {
                true -> progressLoader.visible()
                false -> progressLoader.gone()
            }
        }
        viewModel.getRawJson.observe(viewLifecycleOwner) {
            val json = requireContext().resources.openRawResource(R.raw.mock_shipment_api_response)
                .bufferedReader().use { it.readText() }
            //when there is no raw json we do not need json param below function
            viewModel.setRawJson(json)
        }
    }

    private fun showEmptyFilterInfo() {
        recyclerView.gone()
        noResultsFound.visible()
    }

    private fun hideFilterInfo() {
        recyclerView.visible()
        noResultsFound.gone()
    }

    private fun setupViews() {
        noResultsFound = binding.noResultsFound
        progressLoader = binding.progressBar
        shipmentAdapter = ShipmentListAdapter()
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        setupAdapter()
    }

    private fun setupAdapter() {
        recyclerView.adapter = shipmentAdapter
    }

    companion object {
        fun newInstance() = ShipmentListFragment()
    }
}
