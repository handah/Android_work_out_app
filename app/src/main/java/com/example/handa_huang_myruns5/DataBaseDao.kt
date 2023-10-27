package com.example.handa_huang_myruns5

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface DataBaseDao {

    @Insert
    fun insertTuple(tuple: Tuple)


    @Query("SELECT * FROM tuple_table")
    fun getAllTuples(): Flow<List<Tuple>>

    @Query("DELETE FROM tuple_table")
    fun deleteAll()

    @Query("DELETE FROM tuple_table WHERE id=:key")
    fun deleteTuple(key:Long)


}