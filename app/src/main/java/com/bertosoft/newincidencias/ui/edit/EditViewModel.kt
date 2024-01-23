package com.bertosoft.newincidencias.ui.edit

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bertosoft.newincidencias.domain.model.IncidenciasModelDomain
import com.bertosoft.newincidencias.domain.usecase.GetIncidenciasUseCase
import com.bertosoft.newincidencias.domain.usecase.SetIncidenciasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val setIncidenciasUseCase: SetIncidenciasUseCase,
    private val getIncidenciasUseCase: GetIncidenciasUseCase
): ViewModel() {
    fun getIncidencias(contexto: Context, fecha: String): IncidenciasModelDomain {
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

    fun setIncidencias(incidencias: IncidenciasModelDomain){
        viewModelScope.launch {
            val res = setIncidenciasUseCase(incidencias)
        }
    }

}