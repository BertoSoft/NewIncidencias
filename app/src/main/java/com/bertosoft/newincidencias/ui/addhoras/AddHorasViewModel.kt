package com.bertosoft.newincidencias.ui.addhoras

import androidx.lifecycle.ViewModel
import com.bertosoft.newincidencias.domain.model.AddEnumModel
import com.bertosoft.newincidencias.domain.model.AddInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AddHorasViewModel @Inject constructor(): ViewModel(){

    private var _addDatos = MutableStateFlow<List<String>>(emptyList())
    val addDatos: StateFlow<List<String>> = _addDatos

    init {
        _addDatos.value = listOf("1", "1.5", "2", "2.5", "3")
    }

    fun setHoras(fecha: String, cantidad: String, tipo: String): String {


        return ""
    }
}