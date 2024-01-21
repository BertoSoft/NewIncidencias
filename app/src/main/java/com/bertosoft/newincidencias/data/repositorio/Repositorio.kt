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
        // comprobamos si hya algun plus de voladura en esa fecha
        //
        val idRegistro = funcAux.existeRegistroFecha(incidencias.contexto, incidencias.fecha)
        var respuesta = ""
        val adminDbHlper = AdminDbHelper(incidencias.contexto, null)
        val sqlWriteDb = adminDbHlper.writableDatabase
        if(idRegistro < 0){
            strSql = "INSERT INTO Incidencias (Fecha, " +
                    "Hed, " +
                    "Hen, " +
                    "Hef, " +
                    "Voladuras) VALUES ('${incidencias.fecha}'," +
                    "'${incidencias.hed}'," +
                    "'${incidencias.hen}'," +
                    "'${incidencias.hef}'," +
                    "'${incidencias.voladuras}');"
            respuesta = incidencias.contexto.getString(R.string.add_voladuras)
        }
        else{
            if(funcAux.leerVoladurasFromId(incidencias.contexto, idRegistro) == ""){
                strSql = "UPDATE Incidencias SET Fecha = '" +
                        "${incidencias.fecha}', Hed = '" +
                        "${incidencias.hed}', Hen = '" +
                        "${incidencias.hen}', Hef = '" +
                        "${incidencias.hef}', Voladuras = '" +
                        "${incidencias.voladuras}' WHERE _id = $idRegistro;"
                respuesta = incidencias.contexto.getString(R.string.update_horas_add_voladuras)

            }
            else{
                respuesta =  incidencias.contexto.getString(R.string.update_voladuras)
            }
        }
        sqlWriteDb.execSQL(strSql)
        sqlWriteDb.close()
        adminDbHlper.close()
        return respuesta
    }

    override suspend fun setHoras(incidencias: IncidenciasModelData): String {
        var respuesta = ""
        val idRegistroFecha = funcAux.existeRegistroFecha(incidencias.contexto, incidencias.fecha)
        if(idRegistroFecha > 0){
            incidencias.voladuras  = funcAux.leerVoladurasFromId(incidencias.contexto, idRegistroFecha)
        }
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
            if(incidencias.voladuras == ""){
                respuesta = incidencias.contexto.getString(R.string.update_horas)
            }
            else{
                if(incidencias.hed == "" &&
                    incidencias.hen == "" &&
                    incidencias.hef == ""){
                    respuesta = incidencias.contexto.getString(R.string.update_voladuras_add_horas)
                }
                else{
                    respuesta = incidencias.contexto.getString(R.string.update_voladuras_update_horas)
                }
            }
        }
        sqlWriteDb.execSQL(strSql)
        sqlWriteDb.close()
        adminDbHlper.close()
        return respuesta
    }

}