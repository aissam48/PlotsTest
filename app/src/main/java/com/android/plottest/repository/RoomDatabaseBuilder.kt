package com.android.plottest.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PlotModel::class], version = 1, exportSchema = false)

abstract class RoomDatabaseBuilder : RoomDatabase() {

    abstract fun plotDao(): PlotDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDatabaseBuilder? = null

        fun getDatabase(context: Context): RoomDatabaseBuilder {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    RoomDatabaseBuilder::class.java,
                    "plots_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}