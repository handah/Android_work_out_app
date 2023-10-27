package com.example.handa_huang_myruns5

import androidx.room.Database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.w3c.dom.Comment
import kotlinx.coroutines.flow.Flow

class Repository(private val dataBaseDao: DataBaseDao) {

    val allTuples: Flow<List<Tuple>> = dataBaseDao.getAllTuples()

    fun insert(tuple: Tuple){
        CoroutineScope(IO).launch {
            dataBaseDao.insertTuple(tuple)
        }
    }


    fun delete(id:Long){
        CoroutineScope(IO).launch {

            dataBaseDao.deleteTuple(id)
        }
    }


    fun deleteAll(){
        CoroutineScope(IO).launch {
            dataBaseDao.deleteAll()
        }
    }




}