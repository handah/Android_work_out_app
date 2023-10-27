package com.example.handa_huang_myruns5

import android.content.Context
import android.icu.text.DecimalFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider



//inside of each record
class HistoryEntry:AppCompatActivity() {


    lateinit var histInput:TextView
    lateinit var histActivity:TextView
    lateinit var histDate:TextView
    lateinit var histDuration:TextView
    lateinit var histDistance:TextView
    lateinit var histCalories:TextView
    lateinit var histHeartRate:TextView
    lateinit var histComment:TextView

    lateinit var viewModel: DBViewModel
    var id:Long=-1


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.history_items)

        histInput=findViewById(R.id.histInput)
        histActivity=findViewById(R.id.histActivity)
        histDate=findViewById(R.id.histDate)
        histDuration=findViewById(R.id.histDuration)
        histDistance=findViewById(R.id.histDistance)
        histCalories=findViewById(R.id.histCalories)
        histHeartRate=findViewById(R.id.histHeartRates)
        histComment=findViewById(R.id.histComment)

        val database=DataBase.getInstance(this)
        val dao=database.dataBaseDao
        val viewModelFactory=DBViewModel.TupleViewModelFactory(dao)
        viewModel=ViewModelProvider(this,viewModelFactory).get(DBViewModel::class.java)

        val extra=intent.extras
        if (extra!=null){
            id=extra.getLong(History.ID)
            viewModel.allTupleLiveData.observe(this) {
                val tuple = it.find { entity -> entity.id == id }

                if (tuple!=null) {

                    //get the unit
                    val sharedPreferences = getSharedPreferences(
                        MainActivity.prefKey,
                        Context.MODE_PRIVATE
                    )
                    val unit=sharedPreferences.getString("unitpreferencelist","Miles")
                    var textDist="empty"
                    val df = DecimalFormat("#.##")
                    if (unit.toString()=="Miles" || unit.toString()=="Imperial (Miles)") {
                        Log.d("unitPref","miles")
                        val mileDistance = df.format(tuple.distance)
                        textDist = mileDistance.toString() + " Miles "
                    }
                    else{

                        val kiloDistance = df.format(tuple.distance * 1.60934F)
                        textDist = kiloDistance.toString() + " Kilometers "
                    }

                    val min=tuple.duration.toInt()
                    val sec=((tuple.duration-min)*60).toInt()
                    var durationText=min.toString()+" mins "+sec.toString()+" secs"



                    //sets the text of each line
                    histInput.text = tuple.inputType
                    histActivity.text=tuple.activityType
                    histDate.text=tuple.data_and_time
                    histDuration.text=durationText
                    histDistance.text=textDist
                    histCalories.text="${tuple.calories} cals"
                    histHeartRate.text="${tuple.Heart_rate} bpm"
                    histComment.text=tuple.comment



                }
            }


        }






    }


    fun onDeleteClicked(view:View){
        viewModel.deleteTuple(id)
        finish()
    }



}