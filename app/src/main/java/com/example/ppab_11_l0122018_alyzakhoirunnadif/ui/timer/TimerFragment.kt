package com.example.ppab_11_l0122018_alyzakhoirunnadif.ui.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ppab_11_l0122018_alyzakhoirunnadif.R
import com.example.ppab_11_l0122018_alyzakhoirunnadif.databinding.FragmentTimerBinding

class TimerFragment : Fragment() {

    private lateinit var liveDataTimerViewModel: TimerViewModel
    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        liveDataTimerViewModel = ViewModelProvider(this).get(TimerViewModel::class.java)
        subscribe()
        subscribeToTimerState()

        binding.startButton.setOnClickListener {
            liveDataTimerViewModel.startTimer()
        }

        binding.stopButton.setOnClickListener {
            liveDataTimerViewModel.stopTimer()
        }

        binding.continueButton.setOnClickListener {
            liveDataTimerViewModel.continueTimer()
        }

        binding.resetButton.setOnClickListener {
            liveDataTimerViewModel.resetTimer()
            binding.timerTextview.text = "0"
        }
    }

    private fun subscribe() {
        val elapsedTimeObserver = Observer<Long?> { aLong ->
            val newText = requireContext().resources.getString(R.string.seconds, aLong)
            binding.timerTextview.text = newText
        }
        liveDataTimerViewModel.getElapsedTime().observe(viewLifecycleOwner, elapsedTimeObserver)
    }

    private fun subscribeToTimerState() {
        val timerStateObserver = Observer<TimerState> { state ->
            toggleButtonVisibility(state)
        }
        liveDataTimerViewModel.timerState.observe(viewLifecycleOwner, timerStateObserver)
    }

    private fun toggleButtonVisibility(state: TimerState) {
        when (state) {
            TimerState.START -> {
                binding.startButton.visibility = View.VISIBLE
                binding.stopButton.visibility = View.GONE
                binding.continueButton.visibility = View.GONE
                binding.resetButton.visibility = View.GONE
            }
            TimerState.RUNNING -> {
                binding.startButton.visibility = View.GONE
                binding.stopButton.visibility = View.VISIBLE
                binding.continueButton.visibility = View.GONE
                binding.resetButton.visibility = View.GONE
            }
            TimerState.STOPPED -> {
                binding.startButton.visibility = View.GONE
                binding.stopButton.visibility = View.GONE
                binding.continueButton.visibility = View.VISIBLE
                binding.resetButton.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
