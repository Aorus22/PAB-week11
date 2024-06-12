package com.example.ppab_11_l0122018_alyzakhoirunnadif.ui.timer

import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

enum class TimerState {
    START,
    RUNNING,
    STOPPED
}

class TimerViewModel : ViewModel() {

    companion object {
        private const val ONE_SECOND = 1000
    }

    private val mElapsedTime = MutableLiveData<Long?>()
    private var timer: Timer? = null
    private var initialTimeStopped: Long? = null

    private val _timerState = MutableLiveData(TimerState.START)
    val timerState: LiveData<TimerState> = _timerState

    fun setTimerState(state: TimerState) {
        _timerState.value = state
    }

    fun startTimer() {
        setTimerState(TimerState.RUNNING)
        val currentTime = SystemClock.elapsedRealtime()
        initialTimeStopped = currentTime - (mElapsedTime.value ?: 0) * 1000
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val elapsedTime = (SystemClock.elapsedRealtime() - initialTimeStopped!!) / 1000
                mElapsedTime.postValue(elapsedTime)
            }
        }, ONE_SECOND.toLong(), ONE_SECOND.toLong())
    }

    fun stopTimer() {
        setTimerState(TimerState.STOPPED)
        timer?.cancel()
    }

    fun resetTimer() {
        setTimerState(TimerState.START)
        timer?.cancel()
        initialTimeStopped = null
        mElapsedTime.value = 0L // Reset elapsed time to 0
    }

    fun continueTimer() {
        setTimerState(TimerState.RUNNING)
        initialTimeStopped = SystemClock.elapsedRealtime() - (mElapsedTime.value ?: 0) * 1000
        startTimer()
    }

    fun getElapsedTime(): LiveData<Long?> {
        return mElapsedTime
    }
}
