package com.bertosoft.newincidencias.data.model

import android.content.Context
import com.bertosoft.incidencias.data.funciones.FuncAux
import com.bertosoft.newincidencias.domain.model.IncidenciasModelDomain

data class IncidenciasModelData (
    val iId: Int,
    val contexto: Context,
    val fecha: String,
    val hed: String,
    val hen: String,
    val hef: String,
    var voladuras: String,
    ){
    fun toDomain(): IncidenciasModelDomain {
        var fechaDes = ""
        var hedDes = ""
        var henDes = ""
        var hefDes = ""
        var voladurasDes = ""

        if (fecha != "") {fechaDes = FuncAux().cifrar(fecha)}
        if (hed != "") {hedDes = FuncAux().cifrar(hed)}
        if (hen != "") {henDes = FuncAux().cifrar(hen)}
        if (hef != "") {hefDes = FuncAux().cifrar(hef)}
        if (voladuras != "") {voladurasDes = FuncAux().cifrar(voladuras)}

        return IncidenciasModelDomain(
            iId = iId,
            contexto = contexto,
            fecha = fechaDes,
            hed = hedDes,
            hen = henDes,
            hef = hefDes,
            voladuras = voladurasDes
        )
    }
}