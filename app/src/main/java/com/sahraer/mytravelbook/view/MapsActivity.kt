package com.sahraer.mytravelbook.view

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.sahraer.mytravelbook.R
import com.sahraer.mytravelbook.databinding.ActivityMapsBinding
import com.sahraer.mytravelbook.model.Place
import com.sahraer.mytravelbook.roomdb.PlaceDao
import com.sahraer.mytravelbook.roomdb.PlaceDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnMapLongClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var locationManager: LocationManager // konum yöneticisi
    private lateinit var locationListener: LocationListener
    private lateinit var permissionLauncher : ActivityResultLauncher<String>
    private lateinit var sharedPreferences: SharedPreferences
    private var trackBoolean:Boolean? = null
    private var selectedLongitude:Double? = null
    private var selectedLatitude:Double? = null
    private lateinit var db:PlaceDatabase
    private lateinit var placeDao: PlaceDao
    val compositeDisposible = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //harita tanımlanması ve başlatılması
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        registerLauncher()
        sharedPreferences = this.getSharedPreferences("com.sahraer.mytravelbook", MODE_PRIVATE)
        trackBoolean = false
        selectedLatitude = 0.0
        selectedLongitude = 0.0



        db= Room.databaseBuilder(applicationContext,PlaceDatabase::class.java,"Places")
            //.allowMainThreadQueries()
            .build()

        placeDao = db.placeDao()

    }

   //harita hazır olunca çağrılan fonk
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener(this)
        //lat->41.0055005,lang->28.7319869
        //latitude,langitude
        val istanbul = LatLng(41.1126293,29.0073562)
        mMap.addMarker(MarkerOptions().position(istanbul).title("İstanbul"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(istanbul,15f))


       //casting
       locationManager  = this.getSystemService(LOCATION_SERVICE) as LocationManager

       locationListener = object : LocationListener{
           override fun onLocationChanged(location: Location) { //konum değiştiğinde yapılması

               trackBoolean = sharedPreferences.getBoolean("trackBoolean",false)
               if(trackBoolean == false){
                   val userLocation = LatLng(location.latitude,location.longitude)
                   mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,15f))
                   sharedPreferences.edit().putBoolean("trackBoolean",true).apply()
               }
           }

           override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
               super.onStatusChanged(provider, status, extras)
           }

       }


           
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                 if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                     Snackbar.make(binding.root,"Permission needed for location",Snackbar.LENGTH_INDEFINITE).setAction("Give Permission"){
                         //request permission
                         permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                     }.show()
                 }else{
                     //request permission
                     permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                 }
        }else{
            //permisson granted
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0f,locationListener)
            val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) //son bilinen konumu alır
             if (lastLocation != null){
                   val lastUserLocation = LatLng(lastLocation.latitude,lastLocation.longitude)
                   mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,15f))
             }
            mMap.isMyLocationEnabled = true //konumum etkileştirildi mi -> evet etkinleştirildi

        }


    }

    private fun registerLauncher(){
          permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ result ->
             if(result){
                 //permission granted
                 if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                     locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0f,locationListener)
                     val lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER) //son bilinen konumu alır
                     if (lastLocation != null){
                         val lastUserLocation = LatLng(lastLocation.latitude,lastLocation.longitude)
                         mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,15f))
                     }
                     mMap.isMyLocationEnabled = true //konumum etkileştirildi mi -> evet etkinleştirildi
                 }
             }else{
                 //permission denied
                 Toast.makeText(this@MapsActivity,"Permission Needed!",Toast.LENGTH_LONG).show()
             }
        }
    }

    override fun onMapLongClick(p0: LatLng) {
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(p0))
        selectedLongitude = p0.longitude
        selectedLatitude = p0.latitude
    }

    fun save(view: View){
        if (selectedLatitude != null && selectedLongitude != null){
            val place = Place(binding.placeText.toString(),selectedLatitude!!,selectedLongitude!!)
            compositeDisposible.add(
                placeDao.insert(place)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse)
            )
        }

    }

    private fun handleResponse(){
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
    fun delete(view: View){

    }


    override fun onDestroy() {
        super.onDestroy()
        compositeDisposible.clear()
    }
}