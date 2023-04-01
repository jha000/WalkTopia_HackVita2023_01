package com.app.walktopia

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class map : AppCompatActivity() , OnMapReadyCallback {

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

        val lat = findViewById<TextView>(R.id.lat)
        val lon = findViewById<TextView>(R.id.lon)

        val sharedPreferences =
            this.getSharedPreferences("myKey", Context.MODE_PRIVATE)
        val value = sharedPreferences.getString("lat", "")
        lat.text = value
        val value1 = sharedPreferences.getString("lon", "")
        lon.text = value1

        // Add a marker in Sydney and move the camera
        val jorhat = LatLng(26.7455824, 94.2497349)
        mMap.addMarker(MarkerOptions().position(jorhat).title("Marker in Jorhat"))
        val zoomLevel = 16.0f // adjust this value to set the initial zoom level
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jorhat, zoomLevel))
    }
}