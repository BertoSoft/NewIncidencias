package com.bertosoft.newincidencias.ui.addhoras

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bertosoft.incidencias.data.funciones.FuncAux
import com.bertosoft.newincidencias.databinding.ActivityAddHorasBinding
import com.bertosoft.newincidencias.domain.model.AddEnumModel
import com.bertosoft.newincidencias.ui.addhoras.adapter.AddHorasAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class AddHorasActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddHorasBinding
    private val addHorasViewModel: AddHorasViewModel by viewModels()
    private val args: AddHorasActivityArgs by navArgs()
    private lateinit var addHorasAdapter: AddHorasAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddHorasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
        initUi()
    }

    private fun initUi() {
        initRv()
        initTexto()
        initColectorDatos()
    }

    private fun initRv() {
        var cantidad = ""

        addHorasAdapter = AddHorasAdapter(onNumeroSeleccionado = {
            cantidad = it
        })

        val tipo = when(args.seleccion){
            AddEnumModel.HED -> "hed"
            AddEnumModel.HEN -> "hen"
            AddEnumModel.HEF -> "hef"
            AddEnumModel.Voladuras -> ""
        }
        val fecha = FuncAux().strFechaCortaFromCalendar(Calendar.getInstance())
        val respuesta = addHorasViewModel.setHoras(fecha, cantidad, tipo)
        binding.rvNumerico.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = addHorasAdapter
        }
    }

    private fun initColectorDatos() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                addHorasViewModel.addDatos.collect {
                    //
                    // Ha habido cambios en addDatos
                    //
                    addHorasAdapter.refrescaLista(it)
                }
            }
        }
    }

    private fun initTexto() {
        val fecha = FuncAux().strFechaCortaFromCalendar(Calendar.getInstance())
        val str = "Añadir ${args.seleccion}, con fecha $fecha"
        binding.tvTexto.text = str
    }

    private fun initListeners() {
        binding.ivAtras.setOnClickListener {
            finish()
        }
    }
}