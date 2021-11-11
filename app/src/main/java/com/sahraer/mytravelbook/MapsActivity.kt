package com.sahraer.mytravelbook

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
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
    private lateinit var locationManager: LocationManager // konum yöneticisi
    private lateinit var locationListener: LocationListener

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


       //casting
       locationManager  = this.getSystemService(LOCATION_SERVICE) as LocationManager

       locationListener = object : LocationListener{
           override fun onLocationChanged(location: Location) { //konum değiştiğinde yapılması
               TODO("Not yet implemented")
           }

           override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
               super.onStatusChanged(provider, status, extras)
           }

       }

       locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0f,locationListener)
           



    }
}