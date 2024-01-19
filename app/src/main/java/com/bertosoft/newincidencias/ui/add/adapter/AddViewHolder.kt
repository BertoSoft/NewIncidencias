package com.bertosoft.newincidencias.ui.add.adapter

import android.view.View
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.bertosoft.newincidencias.databinding.ItemAddBinding
import com.bertosoft.newincidencias.domain.model.AddInfo

class AddViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemAddBinding.bind(view)

    fun render(addInfo: AddInfo, onItemSeleccionado: (AddInfo) -> Unit) {
        val context = binding.tvAdd.context

        binding.ivAdd.setImageResource(addInfo.imagen)
        binding.tvAdd.text = context.getString(addInfo.texto)

        binding.parenRv.setOnClickListener {
            onItemSeleccionado(addInfo)
        }
    }
}