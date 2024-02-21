package pl.inpost.recruitmenttask.ui.shipment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.inpost.recruitmenttask.databinding.ShipmentItemBinding
import pl.inpost.recruitmenttask.data.source.model.Shipment

class ShipmentListAdapter :
    RecyclerView.Adapter<ShipmentListAdapter.ViewHolder>() {
    private var dataSet: List<Shipment> = emptyList()
    fun setData(dataSet: List<Shipment>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ShipmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = dataSet.size

    class ViewHolder(private var binding: ShipmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Shipment) {
            binding.shipmentNumber.text = item.number
            binding.status.text = item.status
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item)
    }
}