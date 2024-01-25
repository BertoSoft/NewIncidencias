package com.bertosoft.newincidencias.ui.ver

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bertosoft.newincidencias.R
import com.bertosoft.newincidencias.databinding.FragmentVerBinding
import java.util.Calendar

class VerFragment : Fragment() {

    private var _binding: FragmentVerBinding? = null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSp()
    }

    private fun initSp() {
        initSpAnos()
        initSpMeses()
    }

    private fun initSpMeses() {
        var meses = arrayOf<String>()

        meses += "enero"
        meses += "febrero"
        meses += "marzo"
        meses += "abril"
        meses += "mayo"
        meses += "enero"
        meses += "enero"
        meses += "enero"
        meses += "enero"
        meses += "enero"
        meses += "enero"
        meses += "enero"


        val aaAdaptadorSpinner = ArrayAdapter(
            this.requireContext(),
            R.layout.item_sp,
            meses
        )
        aaAdaptadorSpinner.setDropDownViewResource(R.layout.item_sp)
        binding.spMeses.adapter = aaAdaptadorSpinner

    }

    private fun initSpAnos() {
        var anos = arrayOf<String>()

        anos += "2021"
        anos += "2022"
        anos += "2023"
        anos += "2024"

        val ano = Calendar.getInstance()

        val aaAdaptadorSpinner = ArrayAdapter(
            this.requireContext(),
            R.layout.item_sp,
            anos
        )
        aaAdaptadorSpinner.setDropDownViewResource(R.layout.item_sp)
        binding.spAnos.adapter = aaAdaptadorSpinner
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}