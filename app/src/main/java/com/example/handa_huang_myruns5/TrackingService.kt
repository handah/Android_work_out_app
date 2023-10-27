package com.example.handa_huang_myruns5

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.icu.text.SimpleDateFormat
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.*
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Job
import java.util.*
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


//tracks live location and sends it to viewmodel
class TrackingService: Service(),LocationListener,SensorEventListener {

    private lateinit var notificationManager: NotificationManager

    private lateinit var locationManager:LocationManager
    private var messageHandler:Handler?=null
    private lateinit var serviceBinder:ServiceBinder
    private lateinit var sensorTask:OnSensorChangedTask

    private lateinit var locationList:ArrayList<LatLng>
    private lateinit var prevLocation:Location
    private var distance:Double=0.0
    private var duration: Float=-1f
    private var avgSpeed: Float=-1f
    private var curSpeed: Float=-1f
    private var climb: Float=-1f
    private var calories: Int=-1
    private var dateTime: String="emptyyy"
    private var sec:Int=0
    private lateinit var timer:Timer
    private lateinit var timer2:Timer
    private lateinit var tm: TimerTask
    private lateinit var tm2:TimerTask

    private val calendar:Calendar=Calendar.getInstance()


    private lateinit var executor:ExecutorService
    private lateinit var handler: Handler

    private var distTemp:Double=0.0
    private var startTime: Long=0L
    private var prevTime:Long=0L


    private lateinit var sensorManager:SensorManager
    private lateinit var mAccBuffer: ArrayBlockingQueue<Double>
    private lateinit var accelerometer: Sensor
    var classifyTask:Job?=null
    private lateinit var timertask:TimerTask

    private lateinit var mapActivity: TextView


    companion object{
        const val ACCELEROMETER_BUFFER_CAPACITY = 2048
        const val ACCELEROMETER_BLOCK_CAPACITY = 64


        val CLASS_ACTIVITY_ARRAY = arrayOf(
            "Standing",
            "Walking",
            "Running",
            "Other"
        )
        val MSG_VAL=0
        val CLASS_VAL=1
    }
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate() {
        super.onCreate()
        timer= Timer()
        tm=timerTask()
        timer.scheduleAtFixedRate(tm, 0, 500);
        mAccBuffer = ArrayBlockingQueue<Double>(ACCELEROMETER_BUFFER_CAPACITY)
//        messageHandler=null
//        locationList= ArrayList()
//        serviceBinder=ServiceBinder()
//        initLocationManager()
//
//        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//
//        sensorManager=getSystemService(SENSOR_SERVICE) as SensorManager
//        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
//        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
//
//        showNotification()
        print("debug shownotify")

        startTime=System.currentTimeMillis()
        dateTime=calendarToString(calendar)
        Log.d("TrackingService","on create")





    }


//    private fun backgroundThread() {
//        executor = Executors.newSingleThreadExecutor()
//        handler = Handler(Looper.getMainLooper());
//
//        executor.execute {
//            classify()
//            //Background work here
//            handler.post {
//                //UI Thread work here
//            }
//        }
//    }

        inner class timerTask: TimerTask(){
        override fun run() {
            sec++
        }

    }

    override fun onDestroy() {

        notificationManager.cancelAll()
        messageHandler= null
        if (locationManager != null)
            locationManager.removeUpdates(this)
            locationList.clear()
        classifyTask?.cancel()

        sensorTask.cancel(true)

        try {
            Thread.sleep(100)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        sensorManager.unregisterListener(this)
        super.onDestroy()
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        messageHandler=null
        locationList= ArrayList()
        serviceBinder=ServiceBinder()
        initLocationManager()

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        sensorManager=getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)

        showNotification()

            sensorTask=OnSensorChangedTask()
        sensorTask.execute()

        return START_STICKY
    }


    inner class ServiceBinder:Binder(){
        fun setMessageHandler(messageHandler:Handler){
            this@TrackingService.messageHandler=messageHandler
        }
    }

    //initialize location manager
    private fun initLocationManager(){
        try {
            locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            val criteria = Criteria()
            criteria.accuracy = Criteria.ACCURACY_FINE
            val provider : String? = locationManager.getBestProvider(criteria, false)
            if(provider != null) {
                val location = locationManager.getLastKnownLocation(provider)
                if (location != null)
                    onLocationChanged(location)
                locationManager.requestLocationUpdates(provider, 0, 0f, this)
            }

        } catch (e: SecurityException) {
            Log.d("TrackingService","not working")
        }

    }


    override fun onBind(intent: Intent?): IBinder? {
        return serviceBinder
    }


    //adjusts the newest data to be sent to viewmodel
    override fun onLocationChanged(location: Location) {

        val lat = location.latitude
        val lng = location.longitude
        val latLng = LatLng(lat, lng)
        locationList.add(latLng)
        if (::prevLocation.isInitialized) {

            val totalDistanceTemp=prevLocation.distanceTo(location)
            val totalDistance=totalDistanceTemp/1609.344F
            distance+=totalDistance

            val curTime=System.currentTimeMillis()

            val tempVal=((curTime-startTime)/1000).toFloat()

            duration=(tempVal/60).toFloat()
            val tempHours=(tempVal/3600).toFloat()

            avgSpeed=(distance/tempHours).toFloat()

            val pastTime=(tempVal/3600).toFloat()
            if (pastTime>0){
                curSpeed=totalDistance/pastTime
            }
            else{
                curSpeed=0f
            }

            val climbTemp=(prevLocation.altitude - location.altitude).toFloat()
            climb=(climbTemp/1609.344F).toFloat()
            calories=(distance*120).toInt()

            //println("debug")


        }
        sendMessage()
        //Log.d("TrackingService","message sent")
        prevLocation=location
        prevTime=System.currentTimeMillis()
    }


    //sends the message to the view model through thread
    private fun sendMessage() {
        try {
            val tempHandler = messageHandler
            if (tempHandler != null) {
                val bundle = Util.entryBundle(
                    "GPS", "", dateTime, duration, distance.toFloat(), calories,
                    0, "", climb, curSpeed, avgSpeed, locationList
                )

                val message: Message = tempHandler.obtainMessage()
                message.data = bundle
                message.what = MSG_VAL
                tempHandler.sendMessage(message)

            }
        } catch (t: Throwable) {
            Log.d("TrackingService", t.toString())
        }
    }

//sets up the notification
    private fun showNotification(){
        val intent = Intent(this, MapActivity::class.java)


        var pendingIntent: PendingIntent? = null
        pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(this, 0,intent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        }


        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(
            this,
            "CHANNEL_ID"
        )
        notificationBuilder.setContentTitle("title")
        notificationBuilder.setContentText("content")
        notificationBuilder.setContentIntent(pendingIntent)
        notificationBuilder.setSmallIcon(R.drawable.sfu_logo)
        //notificationBuilder.setAutoCancel(true)
        val notification = notificationBuilder.build()

        if (Build.VERSION.SDK_INT > 26) {
            val notificationChannel = NotificationChannel("CHANNEL_ID", "channel name", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(11, notification)
    }




    @RequiresApi(Build.VERSION_CODES.N)
    private fun calendarToString(calendar: Calendar): String {
        @SuppressLint("SimpleDateFormat") val format = SimpleDateFormat("HH:mm:ss MMM d yyyy")
        return format.format(calendar.time)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_LINEAR_ACCELERATION) {
            val m = Math.sqrt((event.values[0] * event.values[0] + event.values[1] * event.values[1] + (event.values[2]
                    * event.values[2])).toDouble())

            // Inserts the specified element into this queue if it is possible
            // to do so immediately without violating capacity restrictions,
            // returning true upon success and throwing an IllegalStateException
            // if no space is currently available. When using a
            // capacity-restricted queue, it is generally preferable to use
            // offer.
            try {
                mAccBuffer.add(m)
            } catch (e: IllegalStateException) {

                // Exception happens when reach the capacity.
                // Doubling the buffer. ListBlockingQueue has no such issue,
                // But generally has worse performance
                val newBuf = ArrayBlockingQueue<Double>(mAccBuffer.size * 2)
                mAccBuffer.drainTo(newBuf)
                mAccBuffer = newBuf
                mAccBuffer.add(m)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //lmao
    }





    inner class OnSensorChangedTask : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg arg0: Void?): Void? {
            var blockSize = 0
            val fft = FFT(ACCELEROMETER_BLOCK_CAPACITY)
            val accBlock = DoubleArray(ACCELEROMETER_BLOCK_CAPACITY)
            val im = DoubleArray(ACCELEROMETER_BLOCK_CAPACITY)
            val featureVector=ArrayList<Double>(ACCELEROMETER_BLOCK_CAPACITY)

            var max:Double = Double.MIN_VALUE
            while (true) {
                try {
                    accBlock[blockSize++] = mAccBuffer.take().toDouble()
                    if (blockSize == ACCELEROMETER_BLOCK_CAPACITY) {
                        blockSize = 0

                        // time = System.currentTimeMillis();
                        max = .0
                        for (`val` in accBlock) {
                            if (max < `val`) {
                                max = `val`
                            }
                        }
                        fft.fft(accBlock, im)
                        for (i in accBlock.indices) {
                            val mag = Math.sqrt(accBlock[i] * accBlock[i] + im[i]
                                    * im[i])
                            im[i] = .0 // Clear the field
                            featureVector.add(mag)
                        }

                        // Append max after frequency component
                        featureVector.add(max)


                        val  classified=WekaClassifier2.classify(featureVector.toArray()).toInt()

                        //val classified=WekaClassifier.classify(max).toInt()


                        val tempHandler = messageHandler
                        if (tempHandler != null) {
                            val bundle = Bundle()
                            bundle.putString("classify", CLASS_ACTIVITY_ARRAY[classified])
                            featureVector.clear()
                            val message: Message = tempHandler.obtainMessage()
                            message.data = bundle
                            message.what = CLASS_VAL
                            tempHandler.sendMessage(message)

                        }

                        featureVector.clear()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }











}