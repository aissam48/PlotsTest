package com.android.plottest.utils

import com.android.plottest.R
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polygon
import com.google.android.gms.maps.model.PolygonOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Utils {

    fun convertListToString(points: List<LatLng>): String {
        return Gson().toJson(points)
    }

    fun convertStringToList(json: String): List<LatLng> {
        val type = object : TypeToken<List<LatLng>>() {}.type
        return Gson().fromJson<List<LatLng>>(json, type)
    }

    fun createPolygonFromPoints(points: List<LatLng>): PolygonOptions {
        val polygonOptions = PolygonOptions()
            .addAll(points)
            .strokeColor(R.color.green)
            .fillColor(R.color.green2)
            .strokeWidth(5f)

        return polygonOptions
    }
}