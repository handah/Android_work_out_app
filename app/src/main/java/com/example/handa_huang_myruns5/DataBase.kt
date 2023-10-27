package com.example.handa_huang_myruns5

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Tuple::class], version = 1)
abstract class DataBase:RoomDatabase() {
    abstract val dataBaseDao:DataBaseDao

    companion object{
        @Volatile
        private var INSTANCE:DataBase?=null

        fun getInstance(context: Context): DataBase{
            synchronized(this){
                var instance= INSTANCE
                if(instance==null){
                    instance= Room.databaseBuilder(context.applicationContext,
                    DataBase::class.java, "tuple_table").build()
                    INSTANCE=instance
                }
                return instance
            }
        }
    }
}