package com.bertosoft.newincidencias.ui.ver

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bertosoft.incidencias.data.funciones.FuncAux
import com.bertosoft.newincidencias.domain.model.IncidenciasModelDomain
import com.bertosoft.newincidencias.domain.usecase.GetIncidenciasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class VerViewModel @Inject constructor(private val getIncidenciasUseCase: GetIncidenciasUseCase) :
    ViewModel() {

    val liveDataCargando = MutableLiveData<Boolean>()

    fun getListadoIncidencias(
        contexto: Context,
        mes: String,
        ano: String
    ): MutableList<IncidenciasModelDomain> {
        val listaIncidencias = mutableListOf<IncidenciasModelDomain>()
        val iMes = FuncAux().mesStrToInt(mes)
        val iAno = ano.toInt()
        val fecha = Calendar.getInstance()

        //
        // Obtenemos la fecha final
        //
        val fechaFinal = Calendar.getInstance()
        fechaFinal.set(Calendar.DAY_OF_MONTH, 20)
        fechaFinal.set(Calendar.MONTH, iMes)
        fechaFinal.set(Calendar.YEAR, iAno)

        fecha.set(Calendar.DAY_OF_MONTH, fechaFinal.get(Calendar.DAY_OF_MONTH))
        fecha.set(Calendar.MONTH, fechaFinal.get(Calendar.MONTH))
        fecha.set(Calendar.YEAR, fechaFinal.get(Calendar.YEAR))

        //
        // Obtenemos la fecha Inicial
        //
        val fechaInicial = Calendar.getInstance()
        fecha.add(Calendar.MONTH, -1)
        fechaInicial.set(Calendar.DAY_OF_MONTH, 21)
        fechaInicial.set(Calendar.MONTH, fecha.get(Calendar.MONTH))
        fechaInicial.set(Calendar.YEAR, fecha.get(Calendar.YEAR))

        //
        // Recorremos todos los dias
        //
        fecha.set(Calendar.DAY_OF_MONTH, fechaInicial.get(Calendar.DAY_OF_MONTH))
        fecha.set(Calendar.MONTH, fechaInicial.get(Calendar.MONTH))
        fecha.set(Calendar.YEAR, fechaInicial.get(Calendar.YEAR))

        viewModelScope.launch() {
            liveDataCargando.postValue(true)
            val result = with(Dispatchers.IO){
                while (fecha.before(fechaFinal)) {
                    val incidencias =
                        getIncidenciasUseCase(contexto, FuncAux().strFechaCortaFromCalendar(fecha))
                    if (incidencias.hed != "" ||
                        incidencias.hen != "" ||
                        incidencias.hef != "" ||
                        incidencias.voladuras != ""
                    ) {
                        listaIncidencias.add(incidencias)
                    }
                    fecha.add(Calendar.DAY_OF_MONTH, 1)
                }
            }
            liveDataCargando.postValue(false)
        }
        return listaIncidencias
    }
}