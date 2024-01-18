package com.bertosoft.newincidencias.domain.model

import com.bertosoft.newincidencias.R


sealed class AddInfo(val imagen: Int, val texto: Int) {
    data object HED : AddInfo(R.drawable.hed_r, R.string.HED)
    data object HEN : AddInfo(R.drawable.hen_r, R.string.HEN)
    data object HEF : AddInfo(R.drawable.hef_r, R.string.HEF)
    data object Voladuras : AddInfo(R.drawable.voladuras_r, R.string.Voladuras)
}