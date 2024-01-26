package com.bertosoft.newincidencias.ui.ver.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bertosoft.newincidencias.R
import com.bertosoft.newincidencias.ui.addhoras.adapter.AddHorasViewHolder

class VerAdapter(private var lista: List<String> = emptyList()): RecyclerView.Adapter<VerViewHolder>() {

    fun refrescaLista(nuevaLista: List<String>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerViewHolder {
        return VerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_ver, parent, false)
        )
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(holder: VerViewHolder, position: Int) {
        holder.render(lista[position])
    }
}