package com.bertosoft.newincidencias.domain.model

import android.content.Context
import com.bertosoft.incidencias.data.funciones.FuncAux
import com.bertosoft.newincidencias.data.model.IncidenciasModelData

data class IncidenciasModelDomain(
    val iId: Int,
    val contexto: Context,
    val fecha: String,
    val hed: String,
    val hen: String,
    val hef: String,
    val voladuras: String,
) {
    fun toData(): IncidenciasModelData {
        var fechaCod = ""
        var hedCod = ""
        var henCod = ""
        var hefCod = ""
        var voladurasCod = ""

        if (fecha != "") {fechaCod = FuncAux().cifrar(fecha)}
        if (hed != "") {hedCod = FuncAux().cifrar(hed)}
        if (hen != "") {henCod = FuncAux().cifrar(hen)}
        if (hef != "") {hefCod = FuncAux().cifrar(hef)}
        if (voladuras != "") {voladurasCod = FuncAux().cifrar(voladuras)}

        return IncidenciasModelData(
            iId = iId,
            contexto = contexto,
            fecha = fechaCod,
            hed = hedCod,
            hen = henCod,
            hef = hefCod,
            voladuras = voladurasCod
        )
    }
}