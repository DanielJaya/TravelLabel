package com.example.travellabel.View.AddLocation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.travellabel.Request.CreateLocationRequest
import com.example.travellabel.Data.pref.Output
import com.example.travellabel.R
import com.example.travellabel.View.Map.MapsActivity
import com.example.travellabel.ViewModelFactory
import com.example.travellabel.databinding.ActivityAddLocationBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class AddLocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityAddLocationBinding

    private val viewModel: AddLocationViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.btnSave.setOnClickListener {
            val label = binding.edAddTitleLocation.text.toString()
            val description = binding.edAddDescLocation.text.toString()
            val latLng = mMap.cameraPosition.target

            val request = CreateLocationRequest(label, description, latLng.latitude, latLng.longitude)
            viewModel.createLocation(request)
        }

        viewModel.createLocationResult.observe(this) { result ->
            when (result) {
                is Output.Loading -> {
                    // Show loading indicator
                }
                is Output.Success -> {
                    Toast.makeText(this, "Location added successfully", Toast.LENGTH_SHORT).show()
                    moveToMap()
                    // Handle success
                }
                is Output.Error -> {
                    Toast.makeText(this, "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                    // Handle error
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    private fun moveToMap(){
        val intent = Intent(this@AddLocationActivity, MapsActivity::class.java)
        ViewModelFactory.clearInstance()
        startActivity(intent)
        finish()
    }
}