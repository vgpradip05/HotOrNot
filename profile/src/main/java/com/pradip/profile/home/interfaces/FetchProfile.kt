package com.pradip.profile.home.interfaces

import com.google.gson.JsonObject
import com.pradip.core.networking.DataResult
import com.pradip.data.user.local.entities.ProfileL
import com.pradip.data.user.local.models.Profile
import com.pradip.data.user.remote.ProfileR


interface FetchProfile {

    interface Repository {
        suspend fun getProfileList(): DataResult<List<ProfileL>>
        suspend fun updateStatus(profile:ProfileL)
    }

    interface Local {
        suspend fun getProfileList(): DataResult<List<ProfileL>>
        suspend fun addProfiles(list: List<ProfileL>): List<ProfileL>
        suspend fun updateStatus(profile:ProfileL)
    }

    interface Remote {
        suspend fun getProfileList(): DataResult<JsonObject>
    }
}
