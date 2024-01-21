package com.bertosoft.newincidencias.domain.repositorio

import com.bertosoft.newincidencias.data.model.IncidenciasModelData

interface RepositorioDomain {

    suspend fun setPlusVoladuras(incidencias: IncidenciasModelData): String

    suspend fun setHoras(incidencias: IncidenciasModelData): String

}