package com.example.travellabel.View.Map

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.travellabel.Data.pref.Output
import com.example.travellabel.R
import com.example.travellabel.View.AddLocation.AddLocationActivity
import com.example.travellabel.View.Bookmark.BookmarkActivity
import com.example.travellabel.View.Forum.ForumActivity
import com.example.travellabel.View.Fragment.DescLocationFragment.DescLocationFragment
import com.example.travellabel.View.Fragment.NoLocationFragment.NoLocationFragment
import com.example.travellabel.View.Main.MainActivity
import com.example.travellabel.ViewModelFactory

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.travellabel.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.launch

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel by viewModels<MapsViewModel>{
        ViewModelFactory.getInstance(this)
    }

    private val boundsBuilder = LatLngBounds.Builder()
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var isMarkerClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 200
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED,
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        // Update map interaction based on bottom sheet state
                        if (!isMarkerClicked){
                            showFragmentNoLocation()
                        }
                        isMarkerClicked = false
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // Optional: Handle intermediate slide states if needed
            }
        })

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fabAddLoc()
    }

    private fun updateMapInteraction(isMapInteractable: Boolean) {
        mMap.uiSettings.setAllGesturesEnabled(isMapInteractable)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))


        // Tampilkan Desc Lokasi Berdasarkan Marker Yang Dipilih
        mMap.setOnMarkerClickListener { marker ->
            isMarkerClicked = true
            binding.bottomSheet.visibility = View.VISIBLE
            showFragmentLocation()
            true
        }

        getLocPermission()
        getLocation()
    }

    private fun getLocPermission(){
        if(ContextCompat.checkSelfPermission(
            this.applicationContext,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )== PackageManager.PERMISSION_GRANTED
            ){
                mMap.isMyLocationEnabled = true
        }else{
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun getLocation() {
        lifecycleScope.launch {
            viewModel.getLocation().observe(this@MapsActivity) { location ->
                location?.let {
                    when (it) {
                        is Output.Loading -> Log.d(TAG, "getLocation: Loading")
                        is Output.Success -> {
                            it.value.locations.forEach { loc ->
                                val lat = loc.lat?.toDoubleOrNull()
                                val lon = loc.lon?.toDoubleOrNull()
                                if (lat != null && lon != null) {
                                    val latLng = LatLng(lat, lon)
                                    mMap.addMarker(
                                        MarkerOptions()
                                            .position(latLng)
                                            .title(loc.label)
                                            .snippet(loc.description)
                                    )
                                    boundsBuilder.include(latLng)
                                }
                            }

                            mMap.animateCamera(
                                CameraUpdateFactory.newLatLngBounds(
                                    boundsBuilder.build(),
                                    resources.displayMetrics.widthPixels,
                                    resources.displayMetrics.heightPixels,
                                    300
                                )
                            )
                        }

                        is Output.Error -> showToast(it.error)
                    }
                }
            }
        }
    }


    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) getLocPermission()
        }

    private fun showFragmentLocation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.bottomSheetContainer, DescLocationFragment())
            .commit()
    }

    private fun showFragmentNoLocation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.bottomSheetContainer, NoLocationFragment())
            .commit()
    }

    private fun fabAddLoc() {
        binding.fabAddLoc.setOnClickListener {
            startActivity(Intent(this@MapsActivity, AddLocationActivity::class.java))
        }
    }
}