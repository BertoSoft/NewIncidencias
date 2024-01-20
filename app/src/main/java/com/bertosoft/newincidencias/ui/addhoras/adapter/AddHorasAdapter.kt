package com.bertosoft.newincidencias.ui.addhoras.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bertosoft.newincidencias.R
import com.bertosoft.newincidencias.domain.model.AddInfo
import com.bertosoft.newincidencias.ui.add.adapter.AddViewHolder

class AddHorasAdapter(
    private var addHorasList: List<String> = emptyList(),
    private val onNumeroSeleccionado: (String) ->Unit
):
RecyclerView.Adapter<AddHorasViewHolder>(){

    fun refrescaLista(nuevaLista: List<String>) {
        addHorasList = nuevaLista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddHorasViewHolder {
        return AddHorasViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_add_horas, parent, false)
        )
    }

    override fun getItemCount(): Int = addHorasList.size

    override fun onBindViewHolder(holder: AddHorasViewHolder, position: Int) {
        holder.render(addHorasList[position], onNumeroSeleccionado)
    }
}