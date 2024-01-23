package com.bertosoft.newincidencias.data.model

import android.content.Context
import com.bertosoft.incidencias.data.funciones.FuncAux
import com.bertosoft.newincidencias.domain.model.IncidenciasModelDomain

data class IncidenciasModelData (
    val iId: Int,
    val contexto: Context,
    val fecha: String,
    var hed: String,
    var hen: String,
    var hef: String,
    var voladuras: String,
    ){
    fun toDomain(): IncidenciasModelDomain {
        var fechaDes = ""
        var hedDes = ""
        var henDes = ""
        var hefDes = ""
        var voladurasDes = ""

        if (fecha != "") {fechaDes = FuncAux().descifrar(fecha)}
        if (hed != "") {hedDes = FuncAux().descifrar(hed)}
        if (hen != "") {henDes = FuncAux().descifrar(hen)}
        if (hef != "") {hefDes = FuncAux().descifrar(hef)}
        if (voladuras != "") {voladurasDes = FuncAux().descifrar(voladuras)}

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