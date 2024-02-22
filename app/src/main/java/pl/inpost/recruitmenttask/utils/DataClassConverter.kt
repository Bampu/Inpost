package pl.inpost.recruitmenttask.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pl.inpost.recruitmenttask.data.source.model.EventLog
import pl.inpost.recruitmenttask.data.source.model.Operations
import pl.inpost.recruitmenttask.data.source.model.Receiver
import pl.inpost.recruitmenttask.data.source.model.Sender
import pl.inpost.recruitmenttask.data.source.model.Shipment

class DataClassConverter {

    @TypeConverter
    fun fromEventLogList(json: String?): List<EventLog>? {
        val type = object : TypeToken<List<EventLog>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toEventLogList(list: List<EventLog>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromOperations(json: String?): Operations? {
        return Gson().fromJson(json, Operations::class.java)
    }

    @TypeConverter
    fun toOperations(innerData: Operations?): String? {
        return Gson().toJson(innerData)
    }

    @TypeConverter
    fun fromReceiver(json: String?): Receiver? {
        return Gson().fromJson(json, Receiver::class.java)
    }

    @TypeConverter
    fun toReceiver(innerData: Receiver?): String? {
        if (innerData != null) {
            return Gson().toJson(innerData)
        }
        return null
    }

    @TypeConverter
    fun fromRSender(json: String?): Sender? {
        return Gson().fromJson(json, Sender::class.java)
    }

    @TypeConverter
    fun toSender(innerData: Sender?): String? {
        return Gson().toJson(innerData)
    }

    @TypeConverter
    fun fromShipmentsList(json: String?): List<Shipment>? {
        val type = object : TypeToken<List<Shipment>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toShipmentsList(list: List<Shipment>?): String? {
        return Gson().toJson(list)
    }
}
