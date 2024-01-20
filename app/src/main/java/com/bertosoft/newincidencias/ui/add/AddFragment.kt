package com.bertosoft.newincidencias.ui.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bertosoft.newincidencias.databinding.FragmentAddBinding
import com.bertosoft.newincidencias.domain.model.AddEnumModel
import com.bertosoft.newincidencias.domain.model.AddInfo
import com.bertosoft.newincidencias.ui.add.adapter.AddAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddFragment : Fragment() {

    private val addViewModel by viewModels<AddViewModel>()
    private lateinit var addAdapter: AddAdapter
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi() {
        initRv()
        initColectorDatos()
    }

    private fun initRv() {
        addAdapter = AddAdapter(onItemSeleccionado = {
            val seleccion = when(it){
                AddInfo.HED -> AddEnumModel.HED
                AddInfo.HEF -> AddEnumModel.HEF
                AddInfo.HEN -> AddEnumModel.HEN
                AddInfo.Voladuras -> AddEnumModel.Voladuras
            }

            //
            // Si pulsamos voladuras guardamos directamente, si no llamamos a detalle
            //
            if(seleccion == AddEnumModel.Voladuras){
                guardarPlusVoladura()
            }
            else{
                findNavController().navigate(
                    AddFragmentDirections.actionAddFragmentToAddHorasActivity(seleccion)
                )
            }
        })
        binding.rvAdd.apply {
            layoutManager = GridLayoutManager(context, 1)
            adapter = addAdapter
        }
    }

    private fun guardarPlusVoladura() {

        val respuesta = addViewModel.setPlusVoladuras(this.requireContext())
        Toast.makeText(this.requireContext(), respuesta, Toast.LENGTH_SHORT).show()
    }

    private fun initColectorDatos() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                addViewModel.addDatos.collect {
                    //
                    // Ha habido cambios en addDatos
                    //
                    addAdapter.refrescaLista(it)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}