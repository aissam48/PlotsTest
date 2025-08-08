package com.android.plottest.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlotDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlot(plot: PlotModel)

    @Query("SELECT * FROM plots_table")
    suspend fun getAllPlots(): List<PlotModel>

}