package com.pradip.profile.home.interfaces

import com.google.gson.JsonObject
import com.pradip.core.networking.DataResult
import com.pradip.core.networking.callApi
import com.pradip.data.ApiService


class FetchProfilesRemote(
    private val apiService: ApiService

) : FetchProfile.Remote {

    override suspend fun getProfileList(): DataResult<JsonObject> =
        callApi { apiService.getProfile(10) }

}
