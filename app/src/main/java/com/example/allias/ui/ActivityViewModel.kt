package com.example.allias.ui

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.allias.App
import com.example.allias.data.retrofit.WordListApi
import com.example.allias.data.retrofit.WordListLang
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ActivityViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val listOfWord = MutableLiveData<List<String>>()

    var scoreState = false

    val letsGoButtonVisible = Transformations.map(listOfWord) {
        it != null
    }

    fun getListOfWords(lang: WordListLang = WordListLang.CHOOSE_EN) {
        listOfWord.value = null
        val getWordList = WordListApi.retrofitService.getWordListAsync(lang.value)
        coroutineScope.launch {
            try {
                val listResult = getWordList.await()
                listOfWord.value = listResult
            } catch (t: Throwable) {
                listOfWord.value = listOf("No Internet")
                t.printStackTrace()
            }
        }
    }

    val itemSelectedObject = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            arg0: AdapterView<*>?, arg1: View?,
            position: Int, id: Long
        ) {
            val prevPosition = App.pref.getInt("position", 0)
            App.pref.edit().putInt("position", position).apply()
            if (prevPosition != position || listOfWord.value == null) {
                getListOfWords(
                    when (position) {
                        0 -> WordListLang.CHOOSE_EN
                        1 -> WordListLang.CHOOSE_ES
                        2 -> WordListLang.CHOOSE_ZH
                        else -> WordListLang.CHOOSE_DE
                    }
                )
            }
        }

        override fun onNothingSelected(arg0: AdapterView<*>?) = Unit
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}