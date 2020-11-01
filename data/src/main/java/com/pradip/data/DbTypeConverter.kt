package com.pradip.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.util.*

class DbTypeConverter {
    @TypeConverter
    fun getJsonObject(value: String?): JsonObject? {
        return if (value == null) null else Gson().fromJson(
            value,
            JsonObject::class.java
        )
    }

    @TypeConverter
    fun saveJsonObject(value: JsonObject?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun getStringArray(value: String?): ArrayList<String?>? {
        val t =
            object : TypeToken<ArrayList<String?>?>() {}.type
        return if (value == null) null else Gson().fromJson<ArrayList<String?>>(value, t)
    }

    @TypeConverter
    fun saveStringArray(value: ArrayList<String?>?): String? {
        return value?.toString()
    }
}