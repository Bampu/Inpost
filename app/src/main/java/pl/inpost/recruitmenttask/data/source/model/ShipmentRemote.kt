package pl.inpost.recruitmenttask.data.source.model

import com.google.gson.annotations.SerializedName

data class ShipmentRemote(
    @SerializedName("shipments") var shipments: ArrayList<AdapterItem.Shipment> = arrayListOf()
)