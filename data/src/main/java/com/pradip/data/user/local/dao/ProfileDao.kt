package com.pradip.data.user.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.pradip.data.user.local.entities.ProfileL
import com.pradip.data.util.BaseDao

@Dao
abstract class ProfileDao : BaseDao<ProfileL> {

    @Query("SELECT * FROM profileLocal")
    abstract fun getProfiles(): List<ProfileL>

}
