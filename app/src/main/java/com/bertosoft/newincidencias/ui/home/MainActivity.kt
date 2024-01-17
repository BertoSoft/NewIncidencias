package com.bertosoft.newincidencias.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bertosoft.newincidencias.R
import com.bertosoft.newincidencias.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navControlador: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
    }

    private fun initUi() {
        initNavegacion()
    }

    private fun initNavegacion() {
        //
        // Codigo necesario para que la navegacion sea por main_graph
        //
        val navHost = supportFragmentManager.findFragmentById(R.id.contenedorFragmentsView) as NavHostFragment
        navControlador = navHost.navController
        binding.botonNavegacion.setupWithNavController(navControlador)
    }
}