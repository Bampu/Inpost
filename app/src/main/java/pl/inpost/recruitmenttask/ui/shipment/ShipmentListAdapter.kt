package pl.inpost.recruitmenttask.ui.shipment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.data.source.model.AdapterItem
import pl.inpost.recruitmenttask.data.source.model.ShipmentStatus
import pl.inpost.recruitmenttask.databinding.ReadyForPickupBinding
import pl.inpost.recruitmenttask.databinding.RemainingItemBinding
import pl.inpost.recruitmenttask.databinding.ShipmentItemBinding

class ShipmentListAdapter(private val onArchivedListener: OnArchivedListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var dataSet: MutableList<AdapterItem> = mutableListOf()
    private val readyToPickupItem = AdapterItem.ReadyForPickupItem()
    private val remainingItem = AdapterItem.RemainingItem()
//    fun setData(dataSet: Pair<List<AdapterItem>, List<AdapterItem>>) {
    fun setData(dataSet: List<AdapterItem>) {
        this.dataSet.clear()
        this.dataSet.add(FIRST_ITEM, readyToPickupItem)
        this.dataSet.addAll(dataSet)
        this.dataSet.add(remainingItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_READY_FOR_PICKUP -> {
                val view =
                    ReadyForPickupBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                ReadyForPickupViewHolder(view)
            }

            VIEW_TYPE_REMAINING -> {
                val view =
                    RemainingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                RemainingItemViewHolder(view)
            }

            VIEW_TYPE_NORMAL -> {
                val view =
                    ShipmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemViewHolder(view)
            }

            else -> throw IllegalArgumentException(INVALID_VIEW_TYPE)
        }
    }

    override fun getItemCount() = dataSet.size

    class ItemViewHolder(private var binding: ShipmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AdapterItem.Shipment, statusLabel: String) {
            binding.shipmentNumber.text = item.number
            binding.statusValue.text = statusLabel
            binding.senderValue.text =
                item.sender?.email ?: item.sender?.name ?: item.sender?.phoneNumber
            binding.eventLabel.text = item.eventLog?.lastOrNull()?.name
            binding.eventDate.text = item.eventLog?.lastOrNull()?.date
        }
    }

    class ReadyForPickupViewHolder(binding: ReadyForPickupBinding) :
        RecyclerView.ViewHolder(binding.root)

    class RemainingItemViewHolder(binding: RemainingItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return when (dataSet[position]) {
            is AdapterItem.ReadyForPickupItem -> VIEW_TYPE_READY_FOR_PICKUP
            is AdapterItem.RemainingItem -> VIEW_TYPE_REMAINING
            is AdapterItem.Shipment -> VIEW_TYPE_NORMAL
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        when (val item = dataSet[position]) {
            is AdapterItem.ReadyForPickupItem -> {
                holder as ReadyForPickupViewHolder
            }

            is AdapterItem.RemainingItem -> {
                holder as RemainingItemViewHolder
            }

            is AdapterItem.Shipment -> {
                val itemViewHolder = holder as ItemViewHolder
                val nameRes = ShipmentStatus.getResForStatus(item.status)
                val statusLabel = nameRes?.let {
                    holder.itemView.resources.getString(it)
                } ?: holder.itemView.resources.getString(R.string.status_uknown)
                itemViewHolder.bind(item, statusLabel)
            }
        }
    }

    fun getItemAtPosition(position: Int): AdapterItem.Shipment? {
        if (position in 0 until dataSet.size) {
            return dataSet[position] as AdapterItem.Shipment
        }
        return null
    }

    fun removeItem(position: Int) {
        val item = dataSet[position] as AdapterItem.Shipment
        this.dataSet.removeAt(position)
        onArchivedListener.onArchived(item.copy(isArchived = true))
        notifyDataSetChanged()
    }

    companion object {
        private const val VIEW_TYPE_READY_FOR_PICKUP = 0
        private const val VIEW_TYPE_REMAINING = 1
        private const val VIEW_TYPE_NORMAL = 2

        private const val INVALID_VIEW_TYPE = "Invalid view type"
        private const val FIRST_ITEM = 0
    }
}