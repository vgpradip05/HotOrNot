package com.pradip.data.util.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import org.koin.java.KoinJavaComponent.inject

/**
 * List<String> <-> String TypeConverter for Room
 *
 */

class StringListConverter {
    val gson by inject(Gson::class.java)

    @TypeConverter
    fun listToJson(stringList: List<String>?): String? {
        stringList?.let {
            return gson.toJson(it)
        }
        return null
    }

    @TypeConverter
    fun jsonToStringList(listAsString: String?): List<String>? {
        listAsString?.let {
            return gson.fromJson(it, ArrayList<String>().javaClass)
        }
        return null
    }

}