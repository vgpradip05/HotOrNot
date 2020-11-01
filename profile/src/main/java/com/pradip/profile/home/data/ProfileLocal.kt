package com.pradip.profile.home.data

import com.pradip.core.extensions.toDataResult
import com.pradip.core.networking.DataResult
import com.pradip.data.user.local.dao.ProfileDao
import com.pradip.data.user.local.entities.ProfileL
import com.pradip.profile.home.interfaces.FetchProfile

class ProfileLocal(private val dao: ProfileDao) : FetchProfile.Local {

    override suspend fun getProfileList(): DataResult<List<ProfileL>> {
        return dao.getProfiles().toDataResult()
    }

    override suspend fun addProfiles(list: List<ProfileL>): List<ProfileL> {
        dao.insert(list)
        return dao.getProfiles()
    }

    override suspend fun updateStatus(profile:ProfileL) {
        dao.update(profile)
    }
}