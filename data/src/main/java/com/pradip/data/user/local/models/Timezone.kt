package com.pradip.data.user.local.models

import com.google.gson.annotations.SerializedName
data class Timezone (

	@SerializedName("offset") val offset : String,
	@SerializedName("description") val description : String
)