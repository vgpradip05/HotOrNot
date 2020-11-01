package com.pradip.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.pradip.data.user.local.dao.ProfileDao
import com.pradip.data.user.local.entities.ProfileL
import com.pradip.data.util.converters.StringListConverter

@Database(
    entities = [ProfileL::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DbTypeConverter::class, StringListConverter::class)
abstract class AppDb : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "ProfilesDb"

        private var sInstance: AppDb? = null

        @JvmStatic
        fun getDatabase(context: Context): AppDb {
            if (sInstance == null) {
                synchronized(this) {
                    if (sInstance == null)
                        sInstance = buildDatabase(context, AppDb::class.java, DATABASE_NAME).build()
                }
            }

            return sInstance as AppDb
        }

        private fun <T : RoomDatabase> buildDatabase(
            context: Context,
            dbClass: Class<T>,
            databaseName: String,
            inMemory: Boolean = false

        ): Builder<T> {

            // In Memory
            return if (inMemory) Room.inMemoryDatabaseBuilder(context, dbClass)

            // Persistent
            else Room.databaseBuilder(context, dbClass, databaseName)
        }
    }

    // User
    abstract fun profileDao(): ProfileDao

}
