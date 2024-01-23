package com.bertosoft.newincidencias.ui.edit

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bertosoft.incidencias.data.funciones.FuncAux
import com.bertosoft.newincidencias.databinding.FragmentEditBinding
import com.bertosoft.newincidencias.domain.model.IncidenciasModelDomain
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class EditFragment : Fragment() {

    private val editViewModel by viewModels<EditViewModel>()
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private var fecha = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initUi()
    }

    private fun initListeners() {
        val listenerDatePicker =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                guardaValores()

                fecha.set(Calendar.YEAR, year)
                fecha.set(Calendar.MONTH, monthOfYear)
                fecha.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                binding.tvFecha.text = FuncAux().strFechaCortaFromCalendar(fecha)
                rellenaCampos()
            }

        binding.tvFecha.setOnClickListener {
            DatePickerDialog(
                this.requireContext(), listenerDatePicker,
                fecha.get(Calendar.YEAR),
                fecha.get(Calendar.MONTH),
                fecha.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        binding.ibMas.setOnClickListener {
            guardaValores()
            fecha.add(Calendar.DAY_OF_MONTH, 1)
            binding.tvFecha.text = FuncAux().strFechaCortaFromCalendar(fecha)
            rellenaCampos()
        }
        binding.ibMenos.setOnClickListener {
            guardaValores()
            fecha.add(Calendar.DAY_OF_MONTH, -1)
            binding.tvFecha.text = FuncAux().strFechaCortaFromCalendar(fecha)
            rellenaCampos()
        }
    }

    private fun guardaValores() {
        var voladuras = ""
        if (binding.chkVoladuras.isChecked) voladuras = "0.5"
        val incidencias = IncidenciasModelDomain(
            -1,
            this.requireContext(),
            FuncAux().strFechaCortaFromCalendar(fecha),
            binding.etHed.text.toString(),
            binding.etHen.text.toString(),
            binding.etHef.text.toString(),
            voladuras
        )
        editViewModel.setIncidencias(incidencias)
    }

    private fun initUi() {
        binding.tvFecha.text = FuncAux().strFechaCortaFromCalendar(fecha)
        rellenaCampos()
    }

    private fun rellenaCampos() {
        val incidencias = editViewModel.getIncidencias(this.requireContext(), FuncAux().strFechaCortaFromCalendar(fecha))
        binding.etHed.setText(incidencias.hed)
        binding.etHen.setText(incidencias.hen)
        binding.etHef.setText(incidencias.hef)
        binding.chkVoladuras.isChecked = incidencias.voladuras == "0.5"
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
        guardaValores()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        rellenaCampos()
    }

}