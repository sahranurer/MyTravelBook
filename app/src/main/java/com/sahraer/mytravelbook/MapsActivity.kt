package com.sahraer.mytravelbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sahraer.mytravelbook.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //harita tanımlanması ve başlatılması
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

   //harita hazır olunca çağrılan fonk
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //lat->41.0055005,lang->28.7319869
        //latitude,langitude
        val istanbul = LatLng(41.1126293,29.0073562)
       mMap.addMarker(MarkerOptions().position(istanbul).title("İstanbul"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(istanbul,15f))

    }
}