package com.bertosoft.newincidencias.ui.ver.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bertosoft.newincidencias.databinding.ItemVerBinding

class VerViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemVerBinding.bind(view)

    fun render(texto: String){
        binding.tvTexto.text = texto
    }
}