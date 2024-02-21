package pl.inpost.recruitmenttask.data.source

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import dagger.Reusable
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@Reusable
class ApiTypeAdapter @Inject constructor() {

    companion object {
        private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        @FromJson
        fun toZonedDateTime(value: String): ZonedDateTime =
            formatter.parse(value, ZonedDateTime::from)

        @ToJson
        fun fromZonedDateTime(date: ZonedDateTime?): String? = date?.format(formatter)
    }
}
