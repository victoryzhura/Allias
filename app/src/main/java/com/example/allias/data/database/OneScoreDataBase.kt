package com.example.allias.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.allias.data.entity.OneScore

@Database(entities = [OneScore::class], version = 1, exportSchema = false)
abstract class OneScoreDataBase : RoomDatabase() {

    abstract val oneScoreDataBaseDao: OneScoreDataBaseDao

    companion object {

        @Volatile
        private var INSTANCE: OneScoreDataBase? = null
        fun getInstance(context: Context):OneScoreDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        OneScoreDataBase::class.java,
                        "score_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
