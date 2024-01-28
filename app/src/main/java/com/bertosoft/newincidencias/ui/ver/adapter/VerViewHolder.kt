package com.bertosoft.newincidencias.ui.ver.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bertosoft.newincidencias.databinding.ItemVerBinding
import com.bertosoft.newincidencias.domain.model.IncidenciasModelDomain

class VerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemVerBinding.bind(view)

    fun render(incidencias: IncidenciasModelDomain) {

        if (incidencias.hed != "" ||
            incidencias.hen != "" ||
            incidencias.hef != "" ||
            incidencias.voladuras != ""
        ) {
            binding.tvTexto.text = incidencias.fecha
            binding.tvHed.text = incidencias.hed
            binding.tvHen.text = incidencias.hen
            binding.tvHef.text = incidencias.hef
            binding.tvVoladuras.text = incidencias.voladuras
        }


    }
}