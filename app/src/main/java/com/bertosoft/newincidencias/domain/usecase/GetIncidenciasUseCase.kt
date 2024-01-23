package com.bertosoft.newincidencias.domain.usecase

import android.content.Context
import com.bertosoft.newincidencias.data.repositorio.Repositorio
import com.bertosoft.newincidencias.domain.model.IncidenciasModelDomain
import javax.inject.Inject

class GetIncidenciasUseCase @Inject constructor(private val repositorio: Repositorio){
    suspend operator fun invoke(contexto: Context, fecha: String): IncidenciasModelDomain{
        return repositorio.getIncidencias(contexto, fecha)
    }
}