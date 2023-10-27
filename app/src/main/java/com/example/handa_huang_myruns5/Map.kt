package com.example.handa_huang_myruns5

import android.Manifest
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Transformations.map
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import java.util.ArrayList

//the map interface

class Map: AppCompatActivity(){
//class Map : AppCompatActivity(), OnMapReadyCallback, LocationListener{

    private lateinit var mMap: GoogleMap
    private val PERMISSION_REQUEST_CODE = 0
    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map)


//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)


    }

    //when save button is clicked, go back to previous activity
    fun onSaveClicked(view: View){
        this.finish()
    }
    //when cancel button is clicked, show "entry discarded" and go back to the previous activity
    fun onCancelClicked(view: View){
        Toast.makeText(this, "Entry discarded", Toast.LENGTH_LONG).show()

        this.finish()
    }


//    fun initLocationManager() {
//        try {
//            locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
//            val criteria = Criteria()
//            criteria.accuracy = Criteria.ACCURACY_FINE
//            val provider = locationManager.getBestProvider(criteria, true)
//            val location = locationManager.getLastKnownLocation(provider!!)
//            if(location != null)
//                onLocationChanged(location)
//            locationManager.requestLocationUpdates(provider, 0, 0f, this)
//        } catch (e: SecurityException) {
//        }
//    }
//
//
//    override fun onDestroy() {
//        super.onDestroy()
//        if (locationManager != null)
//            locationManager.removeUpdates(this)
//    }
//
//    fun checkPermission() {
//        if (Build.VERSION.SDK_INT < 23) return
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
//        else
//            initLocationManager()
//    }
//
//
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == PERMISSION_REQUEST_CODE) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) initLocationManager()
//        }
//    }
//    override fun onProviderDisabled(provider: String) {}
//    override fun onLocationChanged(location: Location) {
//    }
//
//    override fun onProviderEnabled(provider: String) {}
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
//        checkPermission()
//    }
}