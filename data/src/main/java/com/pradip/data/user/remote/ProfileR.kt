package com.pradip.data.user.remote

import com.google.gson.annotations.SerializedName
import com.pradip.data.user.local.entities.ProfileL
import com.pradip.data.user.local.models.Info
import com.pradip.data.user.local.models.Profile

data class ProfileR(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val list: List<ProfileL>
)
