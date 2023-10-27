package com.example.handa_huang_myruns5

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.DecimalFormat
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList


class MapActivity: AppCompatActivity(), OnMapReadyCallback {

    private lateinit var gmap: GoogleMap
    private val PERMISSION_REQUEST_CODE = 0
    private var isMapCentered = false
    private lateinit var markerOption:MarkerOptions
    private lateinit var activityType:String
    private lateinit var inputType:String
    private var avgSpeedData:Float=-1f
    private var currentSpeedData:Float=-1f
    private var distanceData:Float=-1f
    private var climbData:Float=-1f
    private var caloriesData:Int=-1
    private var durationData:Float=-1f
    private var dateTime:String="empty"
    private lateinit var serviceIntent:Intent
    private lateinit var mapViewModel: MapViewModel

var test:Int=0

    private lateinit var mapActivity: TextView
    private lateinit var avgSpeed:TextView
    private lateinit var currSpeed:TextView
    private lateinit var climb:TextView
    private lateinit var mapCalories:TextView
    private lateinit var mapDistance:TextView

    private lateinit var polyLineOptions:PolylineOptions
    private lateinit var polyList:ArrayList<Polyline>
    private lateinit var locationList: ArrayList<LatLng>
    private var isBound:Boolean=false
    private var newMap:Boolean=true
    private var prevMarker: Marker?=null

    private val markerPostion:LatLng= LatLng(0.0,0.0)




    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map)

        mapViewModel=ViewModelProvider(this).get(MapViewModel::class.java)

        mapActivity=findViewById(R.id.activityType)
        avgSpeed=findViewById(R.id.avg_speed)
        currSpeed=findViewById(R.id.cur_speed)
        climb=findViewById(R.id.climb)
        mapCalories=findViewById(R.id.gps_calories)
        mapDistance=findViewById(R.id.gps_distance)

        val extras=intent.extras


        if (extras!=null){
            activityType=extras.getString(MainActivity.ACTIVITY_TYPE,"empty")
            inputType=extras.getString(MainActivity.INPUT_TYPE,"empty")
        }


        val mapFrag = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFrag.getMapAsync(this)



        serviceIntent=Intent(this,TrackingService::class.java)

        //isMapCentered=false


    }


    override fun finish() {
        super.finish()
        if(isBound) {
            applicationContext.unbindService(mapViewModel)
            stopService(serviceIntent)
            isBound = false
        }
    }




//when map is ready to be used
    override fun onMapReady(googleMap: GoogleMap) {
        gmap = googleMap
        gmap.mapType=GoogleMap.MAP_TYPE_NORMAL
        markerOption= MarkerOptions()
        polyLineOptions= PolylineOptions()
        polyList=ArrayList()

        val extras=intent.extras
        if (extras!=null){
            checkPermission()
            activityType=extras.getString(MainActivity.ACTIVITY_TYPE,"empty")
            inputType=extras.getString(MainActivity.INPUT_TYPE,"empty")


            if (inputType=="Automatic"){
                activityType="Standing"
                mapViewModel.classfiedType.observe(this) {
                    activityType = it
                    println("autoo $activityType")
                }
            }

            gmap.addMarker(MarkerOptions().position(markerPostion).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))

            Log.d("observe draw","no")
            mapViewModel.bundle.observe(this, Observer {
                draw(it)
                println("debug2${it.getFloat("avg_speed")}")

            })


        }


    }


//check permission
    fun checkPermission() {
        if (Build.VERSION.SDK_INT < 23) return
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
        else{
            startTrackingService()
        }
            //initLocationManager()
    }



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) startTrackingService()
        }
    }


//draw the markers and polylines
    fun draw(bundle:Bundle){

        Log.d("is drawing?","yes is drawing")
        locationList= ArrayList()
        val gson= Gson()
        val type:Type=object : TypeToken<ArrayList<LatLng>>() {}.type
        val temp=bundle.getString("locationList")
        val newList:ArrayList<LatLng> = gson.fromJson(temp,type)
        locationList=newList

        if (locationList.isEmpty()){
            Log.d("locationList","empty")
            gmap.addMarker(MarkerOptions().position(markerPostion).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
        }
        else{
            Log.d("locationList","not empty")
            polyLineOptions= PolylineOptions()
            polyLineOptions.addAll(locationList)
            gmap.addPolyline(polyLineOptions)

            //var isMapCentered: Boolean
            val latLng=locationList.last()

            val visibleRegion=gmap.projection.visibleRegion
            isMapCentered = if (isBound){
                visibleRegion.latLngBounds.contains(latLng)
            } else{
                false
            }

            if (!isMapCentered){
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17f)
                gmap.animateCamera(cameraUpdate)
                isMapCentered=true
            }

            if (newMap==true){
                gmap.addMarker(MarkerOptions().position(locationList.first()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
                newMap=false
            }


            prevMarker?.remove()
            markerOption.position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            prevMarker=gmap.addMarker(markerOption)
            Log.d("draw end","yes")
            setView(bundle)
            print("debug${bundle.isEmpty}")

        }

    }

    fun startTrackingService(){
        try{
            applicationContext.startService(serviceIntent)
            applicationContext.bindService(serviceIntent,mapViewModel, Context.BIND_AUTO_CREATE)
            Log.d("MapActivity","service started")
            isBound=true
        }
        catch (e: SecurityException){
            Log.d("problem caught",e.toString())
        }
    }

    //set the text
    fun setView(bundle: Bundle){
        avgSpeedData=bundle.getFloat("averageSpeed")
        currentSpeedData=bundle.getFloat("currentSpeed")
        distanceData=bundle.getFloat("distance")
        climbData=bundle.getFloat("climb")
        caloriesData=bundle.getInt("calories")
        durationData=bundle.getFloat("duration")
        dateTime=bundle.getString("dateAndTime","empty")

        if (bundle.isEmpty){
            println("empty bundle")
        }


//adjust data by different units
        val sharedPreferences = getSharedPreferences(
            MainActivity.prefKey,
            Context.MODE_PRIVATE
        )
        val unit=sharedPreferences.getString("unitpreferencelist","Miles")
        var textDist="empty"
        var textCurrSpeed="empty"
        var textAvgSpeed="empty"
        var textClimb="empty"
        val df = DecimalFormat("#.##")
        if (unit.toString()=="Miles" || unit.toString()=="Imperial (Miles)") {
            Log.d("unitPref","miles")
            val mileDistance = df.format(distanceData)
            val mileCurr=df.format(currentSpeedData)
            val mileAvg=df.format(avgSpeedData)
            val mileClimb=df.format(climbData)
            textDist = mileDistance.toString() + " Miles "
            textCurrSpeed=mileCurr.toString()+" Miles/h"
            textAvgSpeed=mileAvg.toString()+"Miles/h"
            textClimb=mileClimb+"Miles"
        }
        else{
            val mClimb=df.format(climbData*1609.34F)
            val kiloDistance = df.format(distanceData * 1.60934F)
            val kiloCurr= df.format(currentSpeedData * 1.60934F)
            val kiloAvg= df.format(avgSpeedData * 1.60934F)
            textDist = kiloDistance.toString() + " Km "
            textCurrSpeed=kiloCurr.toString()+" Km/h"
            textAvgSpeed=kiloAvg.toString()+"Km/h"
            textClimb=mClimb.toString()+"m"
        }


    //set up the texts
        mapActivity.text="Activity Type: ${activityType}"
        avgSpeed.text="Average Speed: ${textAvgSpeed}"
        currSpeed.text="Current Speed: ${textCurrSpeed}"
        climb.text="Climb: ${textClimb}"
        mapCalories.text="Calories: ${caloriesData}"
        mapDistance.text="Distance: ${textDist}"
        //println("refresh: $activityType")
    }

    //when save button is clicked, store data to database and go back to previous activity
    fun onSaveClicked(view: View){

        val intent=Util.entityIntent(inputType,activityType,dateTime,durationData,distanceData,caloriesData,0,
            "",climbData,currentSpeedData,avgSpeedData,locationList)
        setResult(Activity.RESULT_OK,intent)

        this.finish()
    }
    //when cancel button is clicked, show "entry discarded" and go back to the previous activity
    fun onCancelClicked(view: View){
        Toast.makeText(this, "Entry discarded", Toast.LENGTH_LONG).show()

        this.finish()
    }

    private fun calendarToString(calendar: Calendar): String {
        @SuppressLint("SimpleDateFormat") val format = SimpleDateFormat("HH:mm:ss MMM d yyyy")
        return format.format(calendar.time)
    }














}