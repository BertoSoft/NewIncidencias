package com.bertosoft.newincidencias.domain.repositorio

import android.content.Context
import com.bertosoft.newincidencias.data.model.IncidenciasModelData
import com.bertosoft.newincidencias.domain.model.IncidenciasModelDomain

interface RepositorioDomain {

    suspend fun setIncidencias(incidencias: IncidenciasModelData): String

    suspend fun getIncidencias(contexto: Context, fecha: String): IncidenciasModelDomain

}