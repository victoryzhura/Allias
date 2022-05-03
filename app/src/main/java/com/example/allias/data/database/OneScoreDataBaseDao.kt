package com.example.allias.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.allias.data.entity.OneScore

@Dao
interface OneScoreDataBaseDao {

    @Insert
    suspend fun insert(score: OneScore)

    @Query("DELETE FROM score_table")
    suspend fun clear()

    @Query("SELECT * FROM score_table ORDER BY scoreId DESC")
    fun getAllScore(): LiveData<List<OneScore>>

}