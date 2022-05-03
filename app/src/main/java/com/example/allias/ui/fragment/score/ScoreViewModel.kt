package com.example.allias.ui.fragment.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.allias.data.entity.OneScore
import com.example.allias.data.database.OneScoreDataBaseDao
import kotlinx.coroutines.*

class ScoreViewModel(
    private val database: OneScoreDataBaseDao
) : ViewModel() {

    val allScore = database.getAllScore()

    fun insert(score: OneScore) {
        viewModelScope.launch(Dispatchers.IO) {
            database.insert(score)
        }
    }

    fun onClear() {
        viewModelScope.launch(Dispatchers.IO) {
            database.clear()
        }
    }
}