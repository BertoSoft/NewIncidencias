package com.bertosoft.newincidencias.data.repositorio

import com.bertosoft.incidencias.data.database.AdminDbHelper
import com.bertosoft.incidencias.data.funciones.FuncAux
import com.bertosoft.newincidencias.R
import com.bertosoft.newincidencias.data.model.IncidenciasModelData
import com.bertosoft.newincidencias.domain.repositorio.RepositorioDomain
import javax.inject.Inject

class Repositorio @Inject constructor(private val funcAux: FuncAux): RepositorioDomain {

    private var strSql: String? = null

    override suspend fun setPlusVoladuras(incidencias: IncidenciasModelData): String {
        //
        // Recibimos lo datos ya codificados y los grabamos
        //
        val idRegistro = funcAux.existeRegistroFecha(incidencias.contexto, incidencias.fecha)

        if(idRegistro < 0){
            val adminDbHlper = AdminDbHelper(incidencias.contexto, null)
            val sqlWriteDb = adminDbHlper.writableDatabase
            strSql = "INSERT INTO Incidencias (Fecha, " +
                    "Hed, " +
                    "Hen, " +
                    "Hef, " +
                    "Voladuras) VALUES ('${incidencias.fecha}'," +
                    "'${incidencias.hed}'," +
                    "'${incidencias.hen}'," +
                    "'${incidencias.hef}'," +
                    "'${incidencias.voladuras}');"
            sqlWriteDb.execSQL(strSql)
            sqlWriteDb.close()
            adminDbHlper.close()
        }
        else{
            return  incidencias.contexto.getString(R.string.update_voladuras)
        }
        return incidencias.contexto.getString(R.string.add_voladuras)
    }
}