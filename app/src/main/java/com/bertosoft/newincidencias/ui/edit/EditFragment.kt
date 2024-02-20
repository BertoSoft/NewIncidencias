package com.bertosoft.newincidencias.ui.edit

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.KeyEvent
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

        binding.etHed.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                binding.etHed.postDelayed(
                    {
                        binding.etHed.selectAll()
                    }, 50
                )
            }
        }
        binding.etHen.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                binding.etHen.postDelayed(
                    {
                        binding.etHen.selectAll()
                    }, 50
                )
            }
        }
        binding.etHef.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                binding.etHef.postDelayed(
                    {
                        binding.etHef.selectAll()
                    }, 50
                )
            }
        }

        binding.etHef.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                binding.etHef.postDelayed(
                    {
                        FuncAux().ocultarTeclado(binding.etHef.context, binding.etHef)
                    }, 50
                )
                return@OnKeyListener true
            }
            false
        })

    }

    private fun guardaValores() {
        val hed = editViewModel.toTextView(binding.etHed.text.toString())
        val hen = editViewModel.toTextView(binding.etHen.text.toString())
        val hef = editViewModel.toTextView(binding.etHef.text.toString())
        var voladuras = ""
        if (binding.chkVoladuras.isChecked) voladuras = "0.5"
        val incidencias = IncidenciasModelDomain(
            -1,
            this.requireContext(),
            FuncAux().strFechaCortaFromCalendar(fecha),
            hed,
            hen,
            hef,
            voladuras
        )

        //
        // Guardo valores solo si uno de ellos es distinto de ""
        //
        if (incidencias.hed != "" ||
            incidencias.hen != "" ||
            incidencias.hef != "" ||
            incidencias.voladuras != ""
        ) {
            editViewModel.setIncidencias(incidencias)
        }
    }

    private fun initUi() {
        binding.tvFecha.text = FuncAux().strFechaCortaFromCalendar(fecha)
        rellenaCampos()
    }

    private fun rellenaCampos() {
        val incidencias = editViewModel.getIncidencias(
            this.requireContext(),
            FuncAux().strFechaCortaFromCalendar(fecha)
        )
        binding.etHed.setText(editViewModel.toTextView(incidencias.hed))
        binding.etHen.setText(editViewModel.toTextView(incidencias.hen))
        binding.etHef.setText(editViewModel.toTextView(incidencias.hef))
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