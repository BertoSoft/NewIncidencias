package com.bertosoft.newincidencias.ui.addhoras.adapter

import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bertosoft.newincidencias.databinding.ItemAddHorasBinding

class AddHorasViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemAddHorasBinding.bind(view)

    fun render(numero: String, onNumeroSeleccionado: (String) ->Unit){

        binding.tvNumero.text = numero

        binding.ivImagen.setOnClickListener {
            animacionRotatoria(binding.tvNumero, nuevaLambda = {onNumeroSeleccionado(numero)})
        }
    }

    private fun animacionRotatoria(tvNumero: TextView, nuevaLambda: () -> Unit) {
        tvNumero.animate().apply {
            duration = 500
            interpolator = LinearInterpolator()
            rotationBy(360f)
            withEndAction(nuevaLambda)
            start()
        }
    }
}