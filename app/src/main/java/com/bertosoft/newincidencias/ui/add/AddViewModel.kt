package com.bertosoft.newincidencias.ui.add

import android.content.Context
import androidx.lifecycle.ViewModel
import com.bertosoft.newincidencias.domain.model.AddInfo
import com.bertosoft.newincidencias.domain.model.AddInfo.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor() : ViewModel() {

    private var _addDatos = MutableStateFlow<List<AddInfo>>(emptyList())
    val addDatos: StateFlow<List<AddInfo>> = _addDatos

    init {
        _addDatos.value = listOf(HED, HEN, Voladuras, HEF)
    }

    fun setPlusVoladuras(contexto: Context): String {
        TODO("Not yet implemented")
    }
}
