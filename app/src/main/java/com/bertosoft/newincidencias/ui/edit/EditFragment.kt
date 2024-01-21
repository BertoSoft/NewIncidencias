package com.bertosoft.newincidencias.ui.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bertosoft.incidencias.data.funciones.FuncAux
import com.bertosoft.newincidencias.R
import com.bertosoft.newincidencias.databinding.FragmentAddBinding
import com.bertosoft.newincidencias.databinding.FragmentEditBinding
import com.bertosoft.newincidencias.databinding.FragmentVerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class EditFragment : Fragment() {

    private val editViewModel by viewModels<EditViewModel>()
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initUi()
    }

    private fun initUi() {
        initRv()
        initColectores()
    }

    private fun initColectores() {
        initColectorFecha()
        initColectorDatos()

    }

    private fun initColectorDatos() {
    }

    private fun initColectorFecha() {
        editViewModel.fechaCaja.observe(viewLifecycleOwner, Observer {
            binding.tvFecha.text = FuncAux().strFechaCortaFromCalendar(it)
        })
    }

    private fun initRv() {

    }

    private fun initListeners() {
        binding.ibMas.setOnClickListener {
            editViewModel.sumaDia()
        }
        binding.ibMenos.setOnClickListener {
            editViewModel.restaDia()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}