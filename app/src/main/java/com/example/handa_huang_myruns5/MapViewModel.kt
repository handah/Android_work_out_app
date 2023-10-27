package com.example.handa_huang_myruns5

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


//map view model, basically calss code
class MapViewModel:ViewModel(), ServiceConnection {

    private  var messageHandler:MessageHandler
    private var _bundle=MutableLiveData<Bundle>()
    val bundle:LiveData<Bundle>
    get()= _bundle

    init {
        messageHandler = MessageHandler(Looper.getMainLooper())
    }

    private var _classifiedType=MutableLiveData<String>()
    val classfiedType:LiveData<String>
    get()=_classifiedType


    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        Log.d("MapViewModel","service Connected")
        val trackingBinder=service as TrackingService.ServiceBinder
        trackingBinder.setMessageHandler(messageHandler)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        Log.d("MapViewModel", "onServiceDisconnected")
    }


    inner class MessageHandler(looper: Looper):Handler(looper){
        override fun handleMessage(mess: Message) {
            if (mess.what == TrackingService.MSG_VAL) {
                _bundle.value = mess.data
            }
            else if(mess.what==TrackingService.CLASS_VAL){
                _classifiedType.value=mess.data.getString("classify")

            }

            else{
                Log.d("MapViewModel","mess what wrong")
            }
        }
    }

}