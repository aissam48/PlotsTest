package com.android.plottest.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plots_table")
data class PlotModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val points: String
)
