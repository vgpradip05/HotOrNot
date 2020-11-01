package com.pradip.data.user.local.models

import com.google.gson.annotations.SerializedName
data class Registered (

	@SerializedName("date") val date : String,
	@SerializedName("age") val age : Int
)