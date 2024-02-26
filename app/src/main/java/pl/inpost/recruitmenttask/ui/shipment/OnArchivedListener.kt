package pl.inpost.recruitmenttask.ui.shipment

import pl.inpost.recruitmenttask.data.source.model.AdapterItem

interface OnArchivedListener {
    fun onArchived(archive: AdapterItem.Shipment)
}