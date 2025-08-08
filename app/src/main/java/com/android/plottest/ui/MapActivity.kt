package com.android.plottest.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.plottest.R
import com.android.plottest.databinding.ActivityMapBinding
import com.android.plottest.repository.PlotModel
import com.android.plottest.ui.customViews.CustomAlertDialog
import com.android.plottest.ui.customViews.CustomBottomSheet
import com.android.plottest.utils.Utils.convertStringToList
import com.android.plottest.utils.Utils.createPolygonFromPoints
import com.android.plottest.viewmodels.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polygon
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    private lateinit var binding: ActivityMapBinding
    private val plotPosition = mutableListOf<LatLng>()

    private val viewModel: MapViewModel by viewModels()

    private var polygon: Polygon? = null

    private lateinit var customAlertDialog: CustomAlertDialog
    private lateinit var customBottomSheet: CustomBottomSheet


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapView) as SupportMapFragment
        mapFragment.getMapAsync(this)

        initDialog()
        initButtons()

    }

    private fun initButtons() {
        binding.btnSave.setOnClickListener {
            customAlertDialog.show(supportFragmentManager, "customAlertDialog")
        }

        binding.btnReturn.setOnClickListener {
            clearMap()
        }

        binding.btnList.setOnClickListener {
            customBottomSheet.show(supportFragmentManager, "customBottomSheet")
        }
    }

    private fun initDialog() {
        customAlertDialog = CustomAlertDialog { name ->
            lifecycleScope.launch {
                polygon?.let {
                    viewModel.savePlot(name, it.points)
                    clearMap()
                }
            }
        }

        customBottomSheet = CustomBottomSheet { item ->
            handleItemPressed(item)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        googleMap.setOnMapLongClickListener { latLng ->
            plotPosition.add(latLng)
            createPolygon()

            if (plotPosition.size >= 3) {
                binding.btnSave.visibility = View.VISIBLE
            }
        }
    }

    private fun createPolygon() {
        polygon?.remove()

        val polygonOptions = createPolygonFromPoints(points = plotPosition)

        polygon = googleMap.addPolygon(polygonOptions)

        for (item in plotPosition) {
            googleMap.addMarker(MarkerOptions().position(item))
        }
    }

    private fun clearMap() {
        binding.btnSave.visibility = View.GONE
        plotPosition.clear()
        googleMap.clear()
    }

    private fun handleItemPressed(item: PlotModel) {
        clearMap()
        plotPosition.addAll(convertStringToList(item.points))
        val polygonOptions = createPolygonFromPoints(points = plotPosition)
        polygon = googleMap.addPolygon(polygonOptions)

        val boundsBuilder = LatLngBounds.Builder()

        for (item in plotPosition) {
            googleMap.addMarker(MarkerOptions().position(item))
            boundsBuilder.include(item)

        }
        val bounds = boundsBuilder.build()
        val padding = 100
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
    }
}




