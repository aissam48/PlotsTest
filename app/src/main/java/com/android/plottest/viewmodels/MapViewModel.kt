package com.android.plottest.viewmodels

import androidx.lifecycle.ViewModel
import com.android.plottest.repository.PlotDao
import com.android.plottest.repository.PlotModel
import com.android.plottest.utils.Utils.convertListToString
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polygon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(private val roomDB: PlotDao) : ViewModel() {

    private val _uiStateFlowPlots = MutableStateFlow<List<PlotModel>>(emptyList())
    val uiStateFlowPlots = _uiStateFlowPlots.asStateFlow()

    suspend fun savePlot(name: String, points: List<LatLng>) {

        val plotModel = PlotModel(name = name, points = convertListToString(points))
        roomDB.insertPlot(plotModel)

    }

    suspend fun getAllPlots() {
        val plots = roomDB.getAllPlots()
        _uiStateFlowPlots.emit(plots)
    }
}