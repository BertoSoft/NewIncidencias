package com.bertosoft.newincidencias.ui.ver

import android.content.Context
import androidx.lifecycle.ViewModel
import com.bertosoft.incidencias.data.funciones.FuncAux
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class VerViewModel @Inject constructor() : ViewModel() {

    fun getListadoIncidencias(
        contexto: Context,
        mes: String,
        ano: String
    ): List<String> {
        val iMes = FuncAux().mesStrToInt(mes)
        val iAno = ano.toInt()
        var fecha = Calendar.getInstance()

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

        while (fecha.before(fechaFinal)){
            val str = FuncAux().strFechaCortaFromCalendar(fecha)
            fecha.add(Calendar.DAY_OF_MONTH, 1)
        }

        val lista = mutableListOf<String>(
            "hhkhdksjf",
            "skjdfjahnjsdfkj",
            "kjkjasjfld",
            "hjgsagfashd"
        )

        return lista
    }
}