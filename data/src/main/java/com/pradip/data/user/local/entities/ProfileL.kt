package com.pradip.data.user.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonObject
import com.pradip.data.user.local.models.*

@Entity(tableName = "profileLocal")
data class ProfileL(
    @PrimaryKey(autoGenerate = true) val profileId: Int,
    val gender: String,
    val name: JsonObject,
    val location: JsonObject,
    val email: String,
    val login: JsonObject,
    val dob: JsonObject,
    val registered: JsonObject,
    val phone: String,
    val cell: String,
    val id: JsonObject,
    val picture: JsonObject,
    val nat: String,
    var hotOrNot: Int

)
