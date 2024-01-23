package com.bertosoft.newincidencias.ui.add

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bertosoft.newincidencias.domain.model.AddInfo
import com.bertosoft.newincidencias.domain.model.AddInfo.*
import com.bertosoft.newincidencias.domain.model.IncidenciasModelDomain
import com.bertosoft.newincidencias.domain.usecase.GetIncidenciasUseCase
import com.bertosoft.newincidencias.domain.usecase.SetIncidenciasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val setIncidenciasUseCase: SetIncidenciasUseCase,
    private val getIncidenciasUseCase: GetIncidenciasUseCase
) : ViewModel() {

    private var _addDatos = MutableStateFlow<List<AddInfo>>(emptyList())
    val addDatos: StateFlow<List<AddInfo>> = _addDatos

    init {
        _addDatos.value = listOf(HED, HEN, HEF, Voladuras)
    }

    fun getIncidencias(contexto: Context, fecha: String): IncidenciasModelDomain{
        var incidencias = IncidenciasModelDomain(
            -1,
            contexto,
            fecha,
            "",
            "",
            "",
            ""
        )
        viewModelScope.launch {
            incidencias = getIncidenciasUseCase(contexto, fecha)
        }
        return incidencias
    }

    fun setPlusVoladuras(contexto: Context, fecha: String): String{
        var respuesta = ""

        val incidencias = getIncidencias(contexto, fecha)
        incidencias.voladuras = "0.5"

        viewModelScope.launch {
            respuesta = setIncidenciasUseCase(incidencias)
        }

        return respuesta
    }
}
