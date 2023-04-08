package com.app.walktopia

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class map : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val sharedPreferences = this.getSharedPreferences("myKey", Context.MODE_PRIVATE)
        val latitude = sharedPreferences.getFloat("lat", 0.0f)
        val longitude = sharedPreferences.getFloat("lon", 0.0f)

        val location = LatLng(latitude.toDouble(), longitude.toDouble())

        mMap.addMarker(MarkerOptions().position(location).title("Marker"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18.0f))
    }
}

