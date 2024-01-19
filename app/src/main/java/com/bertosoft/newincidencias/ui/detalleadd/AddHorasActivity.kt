package com.bertosoft.newincidencias.ui.detalleadd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bertosoft.newincidencias.R
import com.bertosoft.newincidencias.databinding.ActivityAddHorasBinding

class AddHorasActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAddHorasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddHorasBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}