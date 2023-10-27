package com.example.handa_huang_myruns5

import android.content.Context
import android.content.Intent
import android.icu.text.DecimalFormat
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import org.w3c.dom.Text
import kotlin.collections.Map


//deals with the format of records inside the history fragment and enters the records
class HistoryDBAdapter(private val context: Context, var tupleList:List<Tuple>):BaseAdapter() {
    private lateinit var listView: ListView

    override fun getCount(): Int {
        return tupleList.size
    }

    override fun getItem(position: Int): Any {
        return tupleList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View=View.inflate(context,R.layout.history_db_adapter,null)

        val firstLine=view.findViewById<TextView>(R.id.firstLine)
        val secondLine=view.findViewById<TextView>(R.id.secondLine)


        val sharedPreferences = context.getSharedPreferences(
            MainActivity.prefKey,
            Context.MODE_PRIVATE
        )


        //gets the unit
        val unit=sharedPreferences.getString("unitpreferencelist","Miles")
        var textDist="empty"
        val df = DecimalFormat("#.##")
        if (unit.toString()=="Miles" || unit.toString()=="Imperial (Miles)") {
            Log.d("unitPref","miles")
            val mileDistance = df.format(tupleList.get(position).distance)
            textDist = mileDistance.toString() + " Miles, "
        }
            else{

                val kiloDistance = df.format(tupleList.get(position).distance * 1.60934F)
                textDist = kiloDistance.toString() + " Kilometers, "
            }




        var min=tupleList.get(position).duration.toInt()
        var sec=((tupleList.get(position).duration-min)*60).toInt()

//        if(tupleList.get(position).inputType=="GPS"){
//            min=((tupleList.get(position).duration)/60).toInt()
//            sec=((tupleList.get(position).duration)%60).toInt()
//        }
        val durationText=min.toString()+" mins "+sec.toString()+" secs"

        //the format of the two lines of each record
        val firstLineText:String=tupleList.get(position).inputType+": "+tupleList.get(position).activityType+", "+tupleList.get(position).data_and_time
        val secondLineText:String=textDist+durationText


        firstLine.text=firstLineText
        secondLine.text=secondLineText





        ///////////////////////////////////////////////////////////////

        //when clicked on the records, enter the records


        view.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {

                val currentTuple= tupleList[position]
                val intent : Intent
                if (currentTuple.inputType=="Manual Entry"){
                    intent= Intent(context,HistoryEntry::class.java)
                }
                else{
                    intent= Intent(context, MapHistoryEntry::class.java)
                }

                intent.putExtra(History.ID,currentTuple.id)
                context.startActivity(intent)
            }
        })



        return view

    }

    fun replace(newTupleList: List<Tuple>){
        tupleList=newTupleList
        this.notifyDataSetChanged()
    }

}