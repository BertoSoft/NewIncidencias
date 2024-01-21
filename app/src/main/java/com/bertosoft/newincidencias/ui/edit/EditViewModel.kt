package com.bertosoft.newincidencias.ui.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bertosoft.incidencias.data.funciones.FuncAux
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(): ViewModel() {

    private var _fechaCaja = MutableLiveData<Calendar>(Calendar.getInstance())
    val fechaCaja: LiveData<Calendar> = _fechaCaja

    fun sumaDia() {
        _fechaCaja.value?.add(Calendar.DAY_OF_MONTH, 1)
    }

    fun restaDia() {
        _fechaCaja.value?.add(Calendar.DAY_OF_MONTH, -1)
    }



}