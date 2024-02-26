package pl.inpost.recruitmenttask.data.source.model

enum class Shipment {
    COURIER,
    PARCEL_LOCKER;

    companion object {
        fun valueOfOrNull(name: String?): Shipment? {
            if (name == null) {
                return null
            }

            return try {
                Shipment.valueOf(name)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}