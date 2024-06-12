package com.example.ppab_11_l0122018_alyzakhoirunnadif.ui.timer

import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import java.util.*

enum class TimerState {
    START,
    RUNNING,
    STOP
}

class TimerViewModel : ViewModel() {

    companion object {
        private const val ONE_SECOND = 1000
    }

    private val mElapsedTime = MutableLiveData<Long?>()
    private var timer: Timer? = null
    private var initialTimeStopped: Long? = null

    private val timerState = MutableLiveData(TimerState.START)

    fun getTimerState(): LiveData<TimerState> {
        return timerState
    }

    private fun setTimerState(state: TimerState) {
        timerState.value = state
    }

    fun startTimer() {
        setTimerState(TimerState.RUNNING)
        val currentTime = SystemClock.elapsedRealtime()
        initialTimeStopped = currentTime - (mElapsedTime.value ?: 0) * ONE_SECOND
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val elapsedTime = (SystemClock.elapsedRealtime() - initialTimeStopped!!) / ONE_SECOND
                mElapsedTime.postValue(elapsedTime)
            }
        }, ONE_SECOND.toLong(), ONE_SECOND.toLong())
    }

    fun stopTimer() {
        setTimerState(TimerState.STOP)
        timer?.cancel()
    }

    fun resetTimer() {
        setTimerState(TimerState.START)
        timer?.cancel()
        initialTimeStopped = null
        mElapsedTime.value = 0L
    }

    fun continueTimer() {
        setTimerState(TimerState.RUNNING)
        initialTimeStopped = SystemClock.elapsedRealtime() - (mElapsedTime.value ?: 0) * ONE_SECOND
        startTimer()
    }

    fun getFormattedElapsedTime(): LiveData<String> {
        return mElapsedTime.map { elapsedTime ->
            val minutes = elapsedTime?.rem(3600)?.div(60)
            val seconds = elapsedTime?.rem(60)
            String.format("%02d:%02d", minutes, seconds)
        }
    }
}
