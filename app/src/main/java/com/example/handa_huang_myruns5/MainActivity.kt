package com.example.handa_huang_myruns5

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Spinner
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.handa_huang_myruns5.ManualEntry
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.collections.Map


class MainActivity : AppCompatActivity() {

    private lateinit var start: Start
    private lateinit var history: History
    private lateinit var settings: Settings

    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout

    private lateinit var myFragmentStateAdapter: MyFragmentStateAdapter
    private lateinit var fragments: ArrayList<Fragment>
    private val tabTitles = arrayOf("START","HISTORY","SETTINGS")
    private lateinit var tabConfigurationStrategy: TabLayoutMediator.TabConfigurationStrategy
    private lateinit var tabLayoutMediator: TabLayoutMediator


    private lateinit var  historyStarter: ActivityResultLauncher<Intent>

    private lateinit var arrayList: ArrayList<Tuple>

    private lateinit var dbViewModel: DBViewModel

    private lateinit var listView: ListView


    companion object{


        const val INPUT_TYPE:String="INPUT_TYPE"
        const val ACTIVITY_TYPE:String="ACTIVITY_TYPE"
        const val prefKey="prefKey"
    }

    //class code, sets up the fragments and tabs
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager2 = findViewById(R.id.viewpager)
        tabLayout = findViewById(R.id.tab)

        start = Start()
        history = History()
        settings = Settings()

        fragments = ArrayList()
        fragments.add(start)
        fragments.add(history)
        fragments.add(settings)

        myFragmentStateAdapter = MyFragmentStateAdapter(this, fragments)
        viewPager2.adapter = myFragmentStateAdapter


        tabConfigurationStrategy =
            TabLayoutMediator.TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                tab.text = tabTitles[position]

            }
        tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager2, tabConfigurationStrategy)
        tabLayoutMediator.attach()


        //tells the history tab to refresh
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                //viewPager2!!.currentItem = tab.position
                if (tab.position==1){

                    history.arrayAdapter.notifyDataSetChanged()
                    //history.reload(history)



                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                print("not selected")
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                print("not reselected")
            }

        })



    }


    override fun onDestroy() {
        super.onDestroy()
        tabLayoutMediator.detach()

    }

    override fun onPause() {
        super.onPause()
    }

    //When start button is clicked, check the input type to decide which activity to start
    fun onStartClicked(v:View){
        val inputTypeSpinner : Spinner = findViewById(R.id.inputType)
        val currentInputType : String = inputTypeSpinner.selectedItem.toString()

        val activityTypeSpinner:Spinner=findViewById(R.id.activityType)
        val currentActivityType:String=activityTypeSpinner.selectedItem.toString()

        if (currentInputType == "Manual Entry"){
            val intent : Intent = Intent(this, ManualEntry::class.java)
            intent.putExtra(INPUT_TYPE,currentInputType)
            intent.putExtra(ACTIVITY_TYPE,currentActivityType)
            historyStarter.launch(intent)
            //startActivity(intent)
        }
        else {
            intent = Intent(this, Map::class.java)
            startActivity(intent)
        }
    }



}