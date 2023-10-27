package com.example.handa_huang_myruns5

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager





//history fragment, inflates history layout
class History: Fragment() {

    private lateinit var listView:ListView
    private lateinit var arrayList:ArrayList<Tuple>
    lateinit var arrayAdapter: HistoryDBAdapter

    private lateinit var entryBundle: Bundle
    private lateinit var tuple: Tuple
    private lateinit var database: DataBase
    private lateinit var dataBaseDao: DataBaseDao
    private lateinit var dbViewModel: DBViewModel
    private lateinit var repository: Repository
    private lateinit var viewModelFactory: DBViewModel.TupleViewModelFactory
    private lateinit var tupleList:List<Tuple>


    companion object{
        val ID="ID"
    }


    //,LoaderManager.LoaderCallbacks<Cursor>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.history,container,false)


        database=DataBase.getInstance(requireActivity())
        dataBaseDao= database.dataBaseDao
        //repository= Repository(dataBaseDao)
        viewModelFactory= DBViewModel.TupleViewModelFactory(dataBaseDao)
        dbViewModel= ViewModelProvider(requireActivity(), viewModelFactory).get(DBViewModel::class.java)

        listView=view.findViewById(R.id.historyList)



        arrayList= ArrayList()

        tupleList=arrayList

        arrayAdapter= HistoryDBAdapter(requireActivity(), arrayList)
        listView.adapter= arrayAdapter

        dbViewModel.allTupleLiveData.observe(requireActivity(),Observer{list->
            arrayAdapter.replace(list)
            arrayAdapter.notifyDataSetChanged()

        })

        val deleteAll: Button =view.findViewById(R.id.deleteAll)

        deleteAll.setOnClickListener(){
            dbViewModel.deleteAll()
        }



//        listView.setOnItemClickListener{
//                parent: AdapterView<*>, view: View, position: Int, id: Long ->
//
//            val intent:Intent
//
//            val currentTuple= tupleList[position]
//            if (currentTuple.inputType=="Manual Entry"){
//                intent=Intent(context,HistoryEntry::class.java)
//            }
//            else{
//                intent=Intent(context,Map::class.java)
//            }
//
//            intent.putExtra(ID,currentTuple.id)
//            startActivity(intent)
//
//        }




        return view
    }






}