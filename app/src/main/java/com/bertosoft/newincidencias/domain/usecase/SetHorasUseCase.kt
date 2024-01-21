package com.bertosoft.newincidencias.domain.usecase

import com.bertosoft.newincidencias.domain.model.IncidenciasModelDomain
import javax.inject.Inject

class SetHorasUseCase @Inject constructor() {
    suspend operator fun invoke(incidencias: IncidenciasModelDomain): String{

        return ""
    }
}