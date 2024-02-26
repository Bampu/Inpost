package pl.inpost.recruitmenttask.data.source.model

import androidx.annotation.StringRes
import pl.inpost.recruitmenttask.R

/**
 * Order of statuses
 * 1. CREATED
 * 2. CONFIRMED
 * 3. ADOPTED_AT_SOURCE_BRANCH
 * 4. SENT_FROM_SOURCE_BRANCH
 * 5. ADOPTED_AT_SORTING_CENTER
 * 6. SENT_FROM_SORTING_CENTER
 * 7. OTHER
 * 8. DELIVERED
 * 9. RETURNED_TO_SENDER
 * 10. AVIZO
 * 11. OUT_FOR_DELIVERY
 * 12. READY_TO_PICKUP
 * 13. PICKUP_TIME_EXPIRED
 */
enum class ShipmentStatus(
    val priority: Int,
    @StringRes val nameRes: Int
) {
    CREATED(1, R.string.status_created),
    CONFIRMED(2, R.string.status_confirmed),
    ADOPTED_AT_SOURCE_BRANCH(3, R.string.status_adopted_at_source_branch),
    SENT_FROM_SOURCE_BRANCH(4, R.string.status_sent_from_source_branch),
    ADOPTED_AT_SORTING_CENTER(5, R.string.status_adopted_at_sorting_center),
    SENT_FROM_SORTING_CENTER(6, R.string.status_sent_from_sorting_center),
    OTHER(7, R.string.status_other),
    DELIVERED(8, R.string.status_delivered),
    RETURNED_TO_SENDER(9, R.string.status_returned_to_sender),
    AVIZO(10, R.string.status_avizo),
    OUT_FOR_DELIVERY(11, R.string.status_out_for_delivery),
    PICKUP_TIME_EXPIRED(12, R.string.status_pickup_time_expired),
    READY_TO_PICKUP(13, R.string.status_ready_to_pickup),
    NOT_READY(15, R.string.not_ready),
    ALL(16, R.string.status_all_types);

    companion object {
        fun valueOfOrNull(name: String?): ShipmentStatus? {
            if (name == null) {
                return null
            }

            return try {
                valueOf(name)
            } catch (e: IllegalArgumentException) {
                null
            }
        }

        fun getResForStatus(name: String?): Int? {
            name?.let {
                return valueOf(name).nameRes
            } ?: return null
        }

        fun getPriority(name: String?): Int? {
            name?.let {
                return valueOf(name).priority
            } ?: return null
        }
    }
}


