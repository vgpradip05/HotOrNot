package com.pradip.profile.home.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pradip.core.networking.DataResult
import com.pradip.data.user.local.entities.ProfileL
import com.pradip.data.user.remote.ProfileR
import com.pradip.profile.home.interfaces.FetchProfile
import java.io.IOException

class ProfileRepo(
    private val remote: FetchProfile.Remote,
    private val local: FetchProfile.Local

) : FetchProfile.Repository {
    override suspend fun getProfileList(): DataResult<List<ProfileL>> {
        val localData = local.getProfileList()
        if (localData is DataResult.Success && localData.data.isNotEmpty()) {
            return localData
        } else {
            return when (val apiCall = remote.getProfileList()) {
                is DataResult.Success -> {
                    val t = object : TypeToken<ProfileR>() {}.type
                    val data = Gson().fromJson<ProfileR>(apiCall.data, t)
                    return DataResult.Success(local.addProfiles(data.list))
                }
                else -> DataResult.Failure(IOException("Failed to get profiles !"))
            }
        }
    }

    override suspend fun updateStatus(profile:ProfileL) {
        local.updateStatus(profile)
    }


}
