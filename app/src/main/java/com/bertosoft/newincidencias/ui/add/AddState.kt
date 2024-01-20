package com.bertosoft.newincidencias.ui.add

sealed class AddState{
    data object EnEspera: AddState()
    data class Error(val error: String): AddState()
    data class TodoOk(val respuesta: String): AddState()
}