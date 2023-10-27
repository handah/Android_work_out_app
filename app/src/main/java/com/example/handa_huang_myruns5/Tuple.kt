package com.example.handa_huang_myruns5

import android.view.ViewDebug
import androidx.room.*
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@TypeConverters(Converter::class)
@Entity(tableName = "tuple_table")
data class Tuple(
    @PrimaryKey(autoGenerate = true)
    var id:Long=0L,

    @ColumnInfo(name="input_type_column")
    var inputType:String="",

    @ColumnInfo(name = "activity_type_column")
    var  activityType:String="",

    @ColumnInfo(name="data_and_time")
    var  data_and_time:String="",

    @ColumnInfo(name = "duration")
    var duration:Float=0F,

    @ColumnInfo(name="distance")
    var distance:Float=0F,

    @ColumnInfo(name="Calories")
    var calories:Int=0,

    @ColumnInfo(name="Heart_rate")
    var Heart_rate:Int=0,

    @ColumnInfo(name="Comment")
    var comment:String="",

    @ColumnInfo(name="Climb")
    var climb:Float=-1f,

    @ColumnInfo(name="CurrentSpeed")
    var currentSpeed:Float=-1f,

    @ColumnInfo(name="AverageSpeed")
    var averageSpeed:Float=-1f,

    @ColumnInfo(name="locationList")
    var locationList: ArrayList<LatLng> = ArrayList()
//    @ColumnInfo(name="comment")
//    var comment:String=""

)

class Converter {

    @TypeConverter
    fun toArrayList(json : String ): ArrayList<LatLng>{
        val gson = Gson()
        val listType: Type = object : TypeToken<ArrayList<LatLng>>() {}.type
        val array: ArrayList<LatLng> = gson.fromJson(json, listType)
        return array
    }

    @TypeConverter
    fun fromArrayList(array : ArrayList<LatLng>): String{
        val gson = Gson()
        val listType: Type = object : TypeToken<ArrayList<LatLng>>() {}.type
        val json: String = gson.toJson(array, listType)
        return json
    }

    //Gson
    //Gregorian Calendar
}