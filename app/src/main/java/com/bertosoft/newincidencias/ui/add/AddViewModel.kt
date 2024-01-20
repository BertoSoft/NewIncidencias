package com.bertosoft.newincidencias.ui.add

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bertosoft.incidencias.data.funciones.FuncAux
import com.bertosoft.newincidencias.domain.model.AddInfo
import com.bertosoft.newincidencias.domain.model.AddInfo.*
import com.bertosoft.newincidencias.domain.model.IncidenciasModelDomain
import com.bertosoft.newincidencias.domain.usecase.SetPlusVoladurasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val funcAux: FuncAux,
    private val setPlusVoladurasUseCase: SetPlusVoladurasUseCase
) : ViewModel() {

    private var _addDatos = MutableStateFlow<List<AddInfo>>(emptyList())
    val addDatos: StateFlow<List<AddInfo>> = _addDatos

    init {
        _addDatos.value = listOf(HED, HEN, HEF, Voladuras)
    }

    fun setPlusVoladuras(contexto: Context): String{
        val fecha = funcAux.strFechaCortaFromCalendar(Calendar.getInstance())
        var respuesta = ""
        val incidencias = IncidenciasModelDomain(
            -1,
            contexto,
            fecha,
            "",
            "",
            "",
            "0.5"
        )
        viewModelScope.launch {
            respuesta = setPlusVoladurasUseCase(incidencias)
        }
        return respuesta
    }
}
