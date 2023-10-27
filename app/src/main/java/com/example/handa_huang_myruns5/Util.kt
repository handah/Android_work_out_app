package com.example.handa_huang_myruns5

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompatExtras
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.zip.Inflater

object Util {


    fun checkPermissions(activity: Activity?) {
        if (Build.VERSION.SDK_INT < 23) return
        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA), 0)
        }

        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 0)
        }
    }

    fun getBitmap(context: Context, imgUri: Uri): Bitmap {
        var bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(imgUri))
        val matrix = Matrix()
        matrix.setRotate(90f)
        var ret = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        return ret
    }


    fun entityIntent(inputType:String,activityType: String, dateAndTime:String,duration:Float,distance:Float,calories:Int,heartRates:Int,comment: String):Intent{


        val intent=Intent()
//        val bundle = entryBundle(inputType, activityType, dateAndTime, duration, distance, calories, heartRates, comment)
//        intent.putExtras(bundle)
//        return intent


        return entityIntent(inputType, activityType, dateAndTime, duration, distance, calories, heartRates, comment,0f,0f,0f,ArrayList<LatLng>())

    }

    fun entityIntent(inputType:String,activityType: String, dateAndTime:String,duration:Float,distance:Float,calories:Int,heartRates:Int,comment: String, climb:Float
    ,currentSpeed:Float,averageSpeed:Float,locationList:ArrayList<LatLng>):Intent{
        val intent=Intent()
        val bundle= entryBundle(inputType,activityType,dateAndTime, duration, distance, calories, heartRates, comment, climb, currentSpeed, averageSpeed, locationList)
        intent.putExtras(bundle)
        return intent
    }


    fun entryBundle(inputType:String,activityType: String, dateAndTime:String,duration:Float,distance:Float,calories:Int,heartRates:Int,comment:String,climb:Float
                    ,currentSpeed:Float,averageSpeed:Float,locationList:ArrayList<LatLng>):Bundle{

        val bundle=Bundle()
        bundle.putString("inputType", inputType)
        bundle.putString("activityType", activityType)
        bundle.putString("dateAndTime", dateAndTime)
        bundle.putFloat("duration", duration)
        bundle.putFloat("distance", distance)
        bundle.putInt("calories", calories)
        bundle.putInt("heartRates", heartRates)
        bundle.putString("comment",comment)
        bundle.putFloat("climb",climb)
        bundle.putFloat("currentSpeed",currentSpeed)
        bundle.putFloat("averageSpeed",averageSpeed)
        //bundle.putParcelableArrayList("locationList",locationList)
        bundle.putString("locationList", convertFromArray(locationList))

        return bundle

    }



    fun getBundle(extras:Bundle):Tuple{
        val tuple=Tuple()
        tuple.inputType=extras.getString("inputType","no")
        tuple.activityType=extras.getString("activityType","no")
        tuple.data_and_time=extras.getString("dateAndTime","no time")
        tuple.duration=extras.getFloat("duration", 0F)
        tuple.distance=extras.getFloat("distance", 0F)
        tuple.calories=extras.getInt("calories", 0)
        tuple.Heart_rate=extras.getInt("heartRates", 0)
        tuple.comment=extras.getString("comment","no")
        tuple.climb=extras.getFloat("climb",0f)
        tuple.currentSpeed=extras.getFloat("currentSpeed",0f)
        tuple.averageSpeed=extras.getFloat("averageSpeed",0f)
        //tuple.locationList= extras.getParcelableArrayList<LatLng>("locationList") as ArrayList<LatLng>
        tuple.locationList= convertToArray(extras.getString("locationList")!!)

        return tuple

    }


    fun getIntent(intent: Intent):Tuple{
        var tuple=Tuple()
        val extras=intent.extras
        if(extras!=null){
            tuple= getBundle(extras)
        }else{
            println("extras null!")
        }
        return tuple
    }


    fun convertToArray(json:String):ArrayList<LatLng>{
        val gson = Gson()
        val listType: Type = object : TypeToken<ArrayList<LatLng>>() {}.type
        val array: ArrayList<LatLng> = gson.fromJson(json, listType)
        return array
    }

    fun convertFromArray(array:ArrayList<LatLng>):String{
        val gson = Gson()
        val listType: Type = object : TypeToken<ArrayList<LatLng>>() {}.type
        val json: String = gson.toJson(array, listType)
        return json
    }

}
