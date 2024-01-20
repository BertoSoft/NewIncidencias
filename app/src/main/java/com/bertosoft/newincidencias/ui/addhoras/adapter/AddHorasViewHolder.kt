package com.bertosoft.newincidencias.ui.addhoras.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bertosoft.newincidencias.databinding.ItemAddHorasBinding

class AddHorasViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemAddHorasBinding.bind(view)

    fun render(numero: String, onNumeroSeleccionado: (String) ->Unit){

        binding.tvNumero.text = numero

        binding.ivImagen.setOnClickListener {
            onNumeroSeleccionado(numero)
        }
    }
}