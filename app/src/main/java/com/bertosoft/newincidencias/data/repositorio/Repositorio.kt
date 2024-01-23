package com.bertosoft.newincidencias.data.repositorio

import android.content.Context
import com.bertosoft.incidencias.data.database.AdminDbHelper
import com.bertosoft.incidencias.data.funciones.FuncAux
import com.bertosoft.newincidencias.R
import com.bertosoft.newincidencias.data.model.IncidenciasModelData
import com.bertosoft.newincidencias.domain.model.IncidenciasModelDomain
import com.bertosoft.newincidencias.domain.repositorio.RepositorioDomain
import javax.inject.Inject

class Repositorio @Inject constructor(private val funcAux: FuncAux): RepositorioDomain {

    private var strSql: String? = null

    override suspend fun setIncidencias(incidencias: IncidenciasModelData): String {
        var respuesta = ""
        val idRegistroFecha = funcAux.existeRegistroFecha(incidencias.contexto, incidencias.fecha)
        val adminDbHlper = AdminDbHelper(incidencias.contexto, null)
        val sqlWriteDb = adminDbHlper.writableDatabase
        if(idRegistroFecha < 0){
            strSql = "INSERT INTO Incidencias (Fecha, " +
                    "Hed, " +
                    "Hen, " +
                    "Hef, " +
                    "Voladuras) VALUES ('${incidencias.fecha}'," +
                    "'${incidencias.hed}'," +
                    "'${incidencias.hen}'," +
                    "'${incidencias.hef}'," +
                    "'${incidencias.voladuras}');"
            //
            // Comprobamos el campo añadido para devolver la respuesta
            //
            if(incidencias.hed != ""){respuesta = incidencias.contexto.getString(R.string.add_hed)}
            if(incidencias.hen != ""){respuesta = incidencias.contexto.getString(R.string.add_hen)}
            if(incidencias.hef != ""){respuesta = incidencias.contexto.getString(R.string.add_hef)}
            if(incidencias.voladuras != ""){respuesta = incidencias.contexto.getString(R.string.add_voladuras)}
        }
        else{
            strSql = "UPDATE Incidencias SET Fecha = '" +
                    "${incidencias.fecha}', Hed = '" +
                    "${incidencias.hed}', Hen = '" +
                    "${incidencias.hen}', Hef = '" +
                    "${incidencias.hef}', Voladuras = '" +
                    "${incidencias.voladuras}' WHERE _id = $idRegistroFecha;"
            //
            // Ahora devolvemos la respuesta
            //
            respuesta = incidencias.contexto.getString(R.string.update_incidencias)
        }
        sqlWriteDb.execSQL(strSql)
        sqlWriteDb.close()
        adminDbHlper.close()
        return respuesta
    }

    override suspend fun getIncidencias(contexto: Context, fecha: String): IncidenciasModelDomain {
        val fechaCod = funcAux.cifrar(fecha)
        val incidencias =IncidenciasModelData(
            -1,
            contexto,
            fechaCod,
            "",
            "",
            "",
            ""
        )
        val adminDbHlper = AdminDbHelper(contexto, null)
        val sqlReadDb = adminDbHlper.readableDatabase
        val cursor = sqlReadDb.rawQuery("SELECT *FROM Incidencias", null)

        val iColFecha = cursor.getColumnIndex("Fecha")
        val iColHed = cursor.getColumnIndex("Hed")
        val iColHen = cursor.getColumnIndex("Hen")
        val iColHef = cursor.getColumnIndex("Hef")
        val iColVoladuras = cursor.getColumnIndex("Voladuras")

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val fechaCod = cursor.getString(iColFecha)
                val fechaDb = funcAux.descifrar(fechaCod)
                if(fecha == fechaDb){
                    incidencias.hed = cursor.getString(iColHed)
                    incidencias.hen = cursor.getString(iColHen)
                    incidencias.hef = cursor.getString(iColHef)
                    incidencias.voladuras = cursor.getString(iColVoladuras)

                    cursor.moveToLast()
                }
                cursor.moveToNext()
            }
        }

        cursor.close()
        sqlReadDb.close()
        adminDbHlper.close()

        return incidencias.toDomain()
    }

}