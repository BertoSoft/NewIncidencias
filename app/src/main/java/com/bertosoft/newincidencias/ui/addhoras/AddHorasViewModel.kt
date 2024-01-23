package com.bertosoft.newincidencias.ui.addhoras

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bertosoft.newincidencias.domain.model.AddEnumModel
import com.bertosoft.newincidencias.domain.model.IncidenciasModelDomain
import com.bertosoft.newincidencias.domain.usecase.GetIncidenciasUseCase
import com.bertosoft.newincidencias.domain.usecase.SetIncidenciasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddHorasViewModel @Inject constructor(
    private val setIncidenciasUseCase: SetIncidenciasUseCase,
    private val getIncidenciasUseCase: GetIncidenciasUseCase
): ViewModel(){

    private var _addDatos = MutableStateFlow<List<String>>(emptyList())
    val addDatos: StateFlow<List<String>> = _addDatos

    init {
        _addDatos.value = listOf(
            "1",
            "1.5",
            "2",
            "2.5",
            "3",
            "3.5",
            "4",
            "4.5",
            "5",
            "5.5",
            "6",
            "6.5"
        )
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

    fun setHoras(contexto: Context, fecha: String, cantidad: String, tipo: AddEnumModel): String {
        val incidenciasDb = getIncidencias(contexto, fecha)

        var respuesta = ""
        var hed = ""
        var hen = ""
        var hef = ""

        when(tipo){
            AddEnumModel.HED -> hed = cantidad
            AddEnumModel.HEN -> hen = cantidad
            AddEnumModel.HEF -> hef = cantidad
            else -> {}
        }
        val incidencias = IncidenciasModelDomain(
            -1,
            contexto,
            fecha,
            hed,
            hen,
            hef,
            incidenciasDb.voladuras
        )
        viewModelScope.launch {
            respuesta = setIncidenciasUseCase(incidencias)
        }
        return respuesta
    }
}