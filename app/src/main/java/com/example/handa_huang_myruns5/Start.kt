package com.example.handa_huang_myruns5

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlin.collections.Map

//inflate start layout
class Start: Fragment() {


    private lateinit var inputTypeSpinner : Spinner
    private lateinit var activityTypeSpinner: Spinner
    private lateinit var startButton: Button
    private lateinit var  historyStarter: ActivityResultLauncher<Intent>
    private lateinit var dbViewModel: DBViewModel
    private lateinit var arrayList: ArrayList<Tuple>

//insert tha tuples received from manual into the database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val database = DataBase.getInstance(requireContext())
        val dataBaseDao = database.dataBaseDao
        val viewModelFactory = DBViewModel.TupleViewModelFactory(dataBaseDao)
        dbViewModel = ViewModelProvider(this, viewModelFactory).get(DBViewModel::class.java)

        historyStarter =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                if (result.resultCode == Activity.RESULT_OK) {

                    val intent: Intent = result.data!!
                    val entry = Util.getIntent(intent)

                    dbViewModel.insert(entry)
                }

                arrayList=ArrayList()

            }

    }

//starts the manual entry
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater.inflate(R.layout.start, container,false)


        inputTypeSpinner = view.findViewById(R.id.inputType)
        activityTypeSpinner=view.findViewById(R.id.activityType)
        startButton=view.findViewById(R.id.START)

        startButton.setOnClickListener(){

            val currentInputType : String = inputTypeSpinner.selectedItem.toString()

            val currentActivityType:String=activityTypeSpinner.selectedItem.toString()

            if (currentInputType == "Manual Entry"){
                val intent : Intent = Intent(requireActivity().baseContext, ManualEntry::class.java)

                intent.putExtra(MainActivity.ACTIVITY_TYPE,currentActivityType)
                intent.putExtra(MainActivity.INPUT_TYPE,currentInputType)

                historyStarter.launch(intent)
                //startActivity(intent)
            }
            else {
                Log.d("current type",currentInputType)
                val intent = Intent(context, MapActivity::class.java)
                intent.putExtra(MainActivity.ACTIVITY_TYPE,currentActivityType)
                intent.putExtra(MainActivity.INPUT_TYPE,currentInputType)
                //val intent = Intent(context, Map::class.java)
               historyStarter.launch(intent)
            }
        }



    return view
        //return inflater.inflate(R.layout.start, container,false)
    }




}