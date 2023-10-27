package com.example.handa_huang_myruns5

import android.content.Context
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type



//inside of each GPS record
class MapHistoryEntry:AppCompatActivity(),OnMapReadyCallback {

    private lateinit var gmap: GoogleMap

    private lateinit var mapActivity: TextView
    private lateinit var avgSpeed: TextView
    private lateinit var currSpeed: TextView
    private lateinit var climb: TextView
    private lateinit var mapCalories: TextView
    private lateinit var mapDistance: TextView

    private lateinit var activityType:String
    private lateinit var inputType:String
    private var avgSpeedData:Float=-1f
    private var currentSpeedData:Float=-1f
    private var distanceData:Float=-1f
    private var climbData:Float=-1f
    private var caloriesData:Int=-1
    private var durationData:Float=-1f
    private var dateTime:String="empty"
    private lateinit var polyLineOptions: PolylineOptions
    private lateinit var polyList:ArrayList<Polyline>
    private lateinit var locationList: ArrayList<LatLng>

    private lateinit var markerOption: MarkerOptions
    lateinit var viewModel: DBViewModel
    var id:Long=-1

    private val markerPostion:LatLng= LatLng(0.0,0.0)
    private var isMapCentered = false
    private var isBound:Boolean=false
    private var newMap:Boolean=true
    private var prevMarker: Marker?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_history)

        mapActivity = findViewById(R.id.activityType)
        avgSpeed = findViewById(R.id.avg_speed)
        currSpeed = findViewById(R.id.cur_speed)
        climb = findViewById(R.id.climb)
        mapCalories = findViewById(R.id.gps_calories)
        mapDistance = findViewById(R.id.gps_distance)

        val mapFrag = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFrag.getMapAsync(this)


        polyList = ArrayList()
        locationList= ArrayList()


        val database = DataBase.getInstance(this)
        val dao = database.dataBaseDao
        val viewModelFactory = DBViewModel.TupleViewModelFactory(dao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DBViewModel::class.java)


        isMapCentered=false


    }


    override fun onMapReady(googleMap: GoogleMap) {
        gmap = googleMap
        gmap.mapType=GoogleMap.MAP_TYPE_NORMAL
        markerOption= MarkerOptions()
        polyLineOptions= PolylineOptions()
        polyList=ArrayList()

        val extra = intent.extras
        if (extra != null) {
            id = extra.getLong(History.ID)
            viewModel.allTupleLiveData.observe(this) {
                val tuple = it.find { entity -> entity.id == id }

                if (tuple != null) {
                    val bundle=Util.entryBundle(tuple.inputType,tuple.activityType,tuple.data_and_time,tuple.duration,
                    tuple.distance,tuple.calories,tuple.Heart_rate,tuple.comment,tuple.climb,tuple.currentSpeed,tuple.averageSpeed,
                        tuple.locationList)
                    draw(bundle)
                }
            }
        }
    }


    //sets up the markers
    fun draw(bundle:Bundle){

        Log.d("is drawing?","yes is drawing")
        locationList= ArrayList()
        val gson= Gson()
        val type: Type =object : TypeToken<ArrayList<LatLng>>() {}.type
        val temp=bundle.getString("locationList")
        val newList:ArrayList<LatLng> = gson.fromJson(temp,type)
        locationList=newList

        if (locationList.isEmpty()){
            Log.d("locationList","empty")
            gmap.addMarker(MarkerOptions().position(markerPostion).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        }
        else{
            Log.d("locationList","not empty")
            polyLineOptions= PolylineOptions()
            polyLineOptions.addAll(locationList)
            gmap.addPolyline(polyLineOptions)

            val latLng=locationList.last()


            if (!isMapCentered){
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17f)
                gmap.animateCamera(cameraUpdate)
                isMapCentered=true
            }

            if (newMap==true){
                gmap.addMarker(MarkerOptions().position(locationList.first()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)))
                newMap=false
            }


            prevMarker?.remove()
            markerOption.position(latLng)
            prevMarker=gmap.addMarker(markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
            Log.d("draw end","yes")
            setView(bundle)
            print("debug${bundle.isEmpty}")

        }

    }



//sets up the text
    fun setView(bundle: Bundle){
        activityType= bundle.getString("activityType").toString()
        avgSpeedData=bundle.getFloat("averageSpeed")
        currentSpeedData=bundle.getFloat("currentSpeed")
        distanceData=bundle.getFloat("distance")
        climbData=bundle.getFloat("climb")
        caloriesData=bundle.getInt("calories")
        durationData=bundle.getFloat("duration")
        dateTime=bundle.getString("dateTime","empty")

        if (bundle.isEmpty){
            println("empty bundle")
        }


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

            val kiloDistance = df.format(distanceData * 1.60934F)
            val kiloCurr= df.format(currentSpeedData * 1.60934F)
            val kiloAvg= df.format(avgSpeedData * 1.60934F)
            val mClimb=df.format(climbData*1609.34F)
            textDist = kiloDistance.toString() + " Km "
            textCurrSpeed=kiloCurr.toString()+" Km/h"
            textAvgSpeed=kiloAvg.toString()+"Km/h"
            textClimb=mClimb.toString()+"m"
        }



        mapActivity.text="Activity Type: ${activityType}"
        avgSpeed.text="Average Speed: ${textAvgSpeed}"
        currSpeed.text="Current Speed: ${textCurrSpeed}"
        climb.text="Climb: ${textClimb}"
        mapCalories.text="Calories: ${caloriesData}"
        mapDistance.text="Distance: ${textDist}"
    }



    fun onMapDeleteClicked(view: View){
        viewModel.deleteTuple(id)
        finish()
    }

}





















