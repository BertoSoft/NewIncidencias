package com.bertosoft.newincidencias.ui.ver

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bertosoft.incidencias.data.funciones.FuncAux
import com.bertosoft.newincidencias.R
import com.bertosoft.newincidencias.databinding.FragmentVerBinding
import com.bertosoft.newincidencias.ui.ver.adapter.VerAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class VerFragment : Fragment() {

    private var _binding: FragmentVerBinding? = null
    private val binding get() = _binding!!
    private val verViewModel: VerViewModel by viewModels()
    private lateinit var verAdapter: VerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initObservers()
        initUi()
    }

    private fun initObservers() {
        //
        // Si hay cambios en el LiveData Cargando, ejecutamos
        //
        verViewModel.liveDataCargando.observe(viewLifecycleOwner) {
            binding.pbCargando.isVisible = it
        }
    }

    private fun initListeners() {

        binding.spMeses.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                refrescaTvTitulo()
                refrescaListaIncidencias()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        binding.spAnos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                refrescaTvTitulo()
                refrescaListaIncidencias()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    private fun initUi() {
        initRv()
        initSp()
        refrescaTvTitulo()
    }

    private fun initRv() {
        verAdapter = VerAdapter()
        binding.rvIncidencias.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = verAdapter
        }
    }

    private fun refrescaListaIncidencias() {
        verAdapter.refrescaLista(
            verViewModel.getListadoIncidencias(
                this.requireContext(),
                binding.spMeses.selectedItem.toString(),
                binding.spAnos.selectedItem.toString()
            )
        )
        refrescaContadores()
    }

    private fun refrescaContadores() {
        val strHed =  "${verViewModel.hed}  Horas"
        val strHen =  "${verViewModel.hen}  Horas"
        val strHef =  "${verViewModel.hef}  Horas"
        val strVoladuras = "${verViewModel.voladuras} Unid. "

        binding.tvHed.text = strHed
        binding.tvHen.text = strHen
        binding.tvHef.text = strHef
        binding.tvVoladuras.text = strVoladuras
    }

    private fun refrescaTvTitulo() {
        val iMes = FuncAux().mesStrToInt(binding.spMeses.selectedItem.toString())
        val strAno = binding.spAnos.selectedItem.toString()
        val iAno = strAno.toInt()
        val fecha = Calendar.getInstance()

        fecha.set(Calendar.DAY_OF_MONTH, 20)
        fecha.set(Calendar.MONTH, iMes)
        fecha.set(Calendar.YEAR, iAno)

        val strFechaFinal = FuncAux().strFechaCortaFromCalendar(fecha)
        fecha.add(Calendar.MONTH, -1)
        fecha.set(Calendar.DAY_OF_MONTH, 21)
        val strFechaInicial = FuncAux().strFechaCortaFromCalendar(fecha)

        val str = "Listado del $strFechaInicial al $strFechaFinal"
        binding.tvTitulo.text = str
    }

    private fun initSp() {
        initSpAnos()
        initSpMeses()
        binding.spAnos.setSelection(1)
        binding.spMeses.setSelection(Calendar.getInstance().get(Calendar.MONTH))
    }

    private fun initSpMeses() {
        var meses = arrayOf<String>()

        meses += "Enero"
        meses += "Febrero"
        meses += "Marzo"
        meses += "Abril"
        meses += "Mayo"
        meses += "Junio"
        meses += "Julio"
        meses += "Agosto"
        meses += "Septiembre"
        meses += "Octubre"
        meses += "Noviembre"
        meses += "Diciembre"


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

        var iAnoActual = Calendar.getInstance().get(Calendar.YEAR)
        iAnoActual++
        val iAnoFinal = iAnoActual - 10
        while (iAnoActual > iAnoFinal) {
            anos += iAnoActual.toString()
            iAnoActual--
        }
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