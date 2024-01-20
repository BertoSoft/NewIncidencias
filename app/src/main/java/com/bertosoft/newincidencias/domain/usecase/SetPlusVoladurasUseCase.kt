package com.bertosoft.newincidencias.domain.usecase

import com.bertosoft.newincidencias.data.repositorio.Repositorio
import com.bertosoft.newincidencias.domain.model.IncidenciasModelDomain
import javax.inject.Inject

class SetPlusVoladurasUseCase @Inject constructor(private val repositorio: Repositorio) {
    suspend operator fun invoke(incidencias: IncidenciasModelDomain): String {
        //
        // Aqui el cambio de modelo de datos
        //
        return repositorio.setPlusVoladuras (incidencias.toData())
    }
}