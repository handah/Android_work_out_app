package com.example.handa_huang_myruns5

//import android.icu.util.Calendar

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Database
import java.util.*
import kotlin.collections.ArrayList


class ManualEntry: AppCompatActivity() {
    private val entires = arrayOf(
        "Date", "Time", "Duration", "Distance", "Calories", "Heart Rates", "Comment"
    )

    private lateinit var entryList: ListView

    private val calendar = Calendar.getInstance()

    private lateinit var current:String

    companion object {
        var currentActivityType:String="empty"
        var currentDuration:Float=0F
        var currentDistance:Float=0F
        var currentCalories:Int=0
        var currentHeartRate:Int=0
        var currentInputType:String="none"
        var currentDateTime:String="none"
        var currentComment:String="none"
        const val DIALOG_KEY = "dialog"

    }

    //var inputType:String="empty"
    var activityType:String="empty"
    var currentYear:Int=0
    var currentMonth: Int=0
    var currentDay:Int=0
    var currentHour:Int=0
    var currentMinute:Int=0


    lateinit var arrayAdapterDB: HistoryDBAdapter

    lateinit var arrayList:ArrayList<Tuple>

    //class code
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manual_entry)

        entryList = findViewById(R.id.manualList)


        val arrayAdapter = ArrayAdapter<String>(this, R.layout.listview_layout, entires)
        entryList.adapter = arrayAdapter


        val extra=intent.extras
        if (extra != null) {
            currentInputType=extra.getString(MainActivity.INPUT_TYPE,"none")
            currentActivityType=extra.getString(MainActivity.ACTIVITY_TYPE,"none")
            activityType= currentActivityType
            Log.d("onCreateActivity", currentActivityType)
        }




        entryList.setOnItemClickListener() {
                parent: AdapterView<*>, textView: View, position: Int, id: Long ->
            val  currentEntry:String=(textView as TextView).text.toString() //detect the tapped entry
            println("debug: current entry is: ${currentEntry}")
            current = currentEntry

            //if the tapped entry is date, show date dialog box
            if (currentEntry == "Date") {
                val datePickerDialog = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, monthOfYear)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }


                DatePickerDialog(
                    this, datePickerDialog,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()

                currentYear=calendar.get(Calendar.YEAR)
                currentMonth=calendar.get(Calendar.MONTH)
                currentDay=calendar.get(Calendar.DAY_OF_MONTH)

            }

            //if the tapped entry is time, show the time dialog box
            else if (currentEntry == "Time"){
                val timePickerDialog = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY,hourOfDay)
                    calendar.set(Calendar.MINUTE,minute)
                }

                TimePickerDialog(
                    this,timePickerDialog,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()

                currentHour= calendar.get(Calendar.HOUR_OF_DAY)
                currentMinute=calendar.get(Calendar.MINUTE)
            }



            //if the tapped entry is not date nor time, show a general dialog box
            else {
                val myDialog = ManualEntryDialog()
                val bundle = Bundle()
                var dialogType : String = "nothing"
                if (currentEntry == "Duration"){
                    dialogType = "Duration"
                }
                else if (currentEntry == "Distance"){
                    dialogType = "Distance"
                }
                else if(currentEntry == "Calories"){
                    dialogType = "Calories"
                }
                else if(currentEntry == "Heart Rates"){
                    dialogType = "Heart Rates"
                }
                else if(currentEntry=="Comment"){
                    dialogType = "Comment"
                }

                bundle.putString(DIALOG_KEY,dialogType)
                myDialog.arguments=bundle
                myDialog.show(supportFragmentManager,"my dialog")
            }

        }

        arrayList= ArrayList()
        arrayAdapterDB = HistoryDBAdapter(this,arrayList)


    }

    //when save button is clicked, go back to the previous activity
    //it also sends back data for inserting into the database
    @RequiresApi(Build.VERSION_CODES.N)
    fun onSaveeClicked(view:View){

        currentDateTime=calendarToString(calendar)

        val intent=Util.entityIntent(
            currentInputType,
            currentActivityType,
            currentDateTime,
            currentDuration,
            currentDistance,
            currentCalories,
            currentHeartRate,
            currentComment)



        if (activityType!="empty"){
            Log.d("activitytype","not empty")
            Log.d("activitytype","$currentActivityType")
            Log.d("activitytype","$activityType")
        }
        else{

            Log.d("activitytype","empty")
        }


        Log.d("afteract","after")

        val intent2=Intent()
        intent2.putExtra("TEST","sent")

        setResult(Activity.RESULT_OK,intent)

        finish()
    }
    //when cancel button is clicked, show "entry discarded" and go back to the previous activity
    fun onCancelClicked(view:View){
        setResult(Activity.RESULT_CANCELED)
        Toast.makeText(this, "Entry discarded", Toast.LENGTH_LONG).show()

        this.finish()
    }


   // sets of the calendar and time into the required format
   @RequiresApi(Build.VERSION_CODES.N)
   private fun calendarToString(calendar: Calendar): String {
        @SuppressLint("SimpleDateFormat") val format = SimpleDateFormat("HH:mm:ss MMM d yyyy")
        return format.format(calendar.time)
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}