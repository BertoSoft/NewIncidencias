package com.bertosoft.newincidencias.domain.usecase

import com.bertosoft.newincidencias.data.repositorio.Repositorio
import com.bertosoft.newincidencias.domain.model.IncidenciasModelDomain
import javax.inject.Inject

class SetHorasUseCase @Inject constructor(private val repositorio: Repositorio) {

    suspend operator fun invoke(incidencias: IncidenciasModelDomain): String{
        return repositorio.setHoras(incidencias.toData())
    }
}