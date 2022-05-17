package com.example.allias.ui.fragment.game

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.allias.ui.utilily.Util.millsToDateFormat


class GameViewModel : ViewModel() {

    var _word = MutableLiveData<String>()

    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private var _timer = MutableLiveData<String>()
    val timer: LiveData<String>
        get() = _timer

    private var _eventTimer = MutableLiveData<Boolean>()
    val eventTimer: LiveData<Boolean>
        get() = _eventTimer

    var oneListOfWords = MutableLiveData<List<String>>()

    private val time: CountDownTimer
        get() = object : CountDownTimer(3000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                _timer.value = (millisUntilFinished).millsToDateFormat()
            }

            override fun onFinish() {
                _eventTimer.value = true
            }
        }

    init {
        _score.value = 0
        time.start()
    }

    fun printWord() = oneListOfWords.value?.random() ?: ""

    fun onCorrect() {
        _score.value = (_score.value)?.plus(1)
        _word.value = printWord()
    }

    fun onSkip() {
        if ((_score.value ?: 0) <= 0)
            _score.value = 0
        else
            _score.value = (_score.value)?.minus(1)
        _word.value = printWord()
    }

    fun hasFinished() {
        _eventTimer.value = false
    }
}