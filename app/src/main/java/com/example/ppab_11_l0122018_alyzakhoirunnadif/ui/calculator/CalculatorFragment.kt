package com.example.ppab_11_l0122018_alyzakhoirunnadif.ui.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ppab_11_l0122018_alyzakhoirunnadif.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CalculatorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CalculatorViewModel::class.java)

        binding.btnAdd.setOnClickListener { calculate('+') }
        binding.btnSubtract.setOnClickListener { calculate('-') }
        binding.btnMultiply.setOnClickListener { calculate('*') }
        binding.btnDivide.setOnClickListener { calculate('/') }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun calculate(operator: Char) {
        val firstNumber = binding.edtFirstNumber.text.toString().toDoubleOrNull() ?: return
        val secondNumber = binding.edtSecondNumber.text.toString().toDoubleOrNull() ?: return

        viewModel.calculate(firstNumber, secondNumber, operator)
        binding.tvResult.text = viewModel.result
    }
}