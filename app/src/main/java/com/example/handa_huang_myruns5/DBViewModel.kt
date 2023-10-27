package com.example.handa_huang_myruns5

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.lang.reflect.Type

class DBViewModel(private val dataBaseDao: DataBaseDao):ViewModel() {

    val allTupleLiveData: LiveData<List<Tuple>> = dataBaseDao.getAllTuples().asLiveData()

    fun insert(tuple: Tuple){
        //repository.insert(tuple)
        CoroutineScope(Dispatchers.IO).launch {
            dataBaseDao.insertTuple(tuple)
        }
    }


    fun deleteAll(){allTupleLiveData.value
        val tupleList=allTupleLiveData.value
        if (tupleList != null && tupleList.isNotEmpty())
            //repository.deleteAll()
        CoroutineScope(Dispatchers.IO).launch {
            dataBaseDao.deleteAll()
        }
    }


    fun deleteTuple(id: Long){allTupleLiveData.value
        val tupleList=allTupleLiveData.value
        if (tupleList != null && tupleList.isNotEmpty())
        //repository.deleteAll()
            CoroutineScope(Dispatchers.IO).launch {
                dataBaseDao.deleteTuple(id)
            }

    }


    class TupleViewModelFactory(private val dataBaseDao: DataBaseDao):ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DBViewModel::class.java))
                return DBViewModel(dataBaseDao) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}