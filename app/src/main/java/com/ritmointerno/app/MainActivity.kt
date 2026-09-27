package com.ritmointerno.app

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.ritmointerno.app.data.SessionManager
import com.ritmointerno.app.databinding.ActivityMainBinding
import com.ritmointerno.app.ui.SectionPagerAdapter
import com.ritmointerno.app.ui.auth.LoginActivity

/**
 * Pantalla principal: toolbar con menú de 3 puntos (Acerca de / Ayuda /
 * Cerrar sesión / Salir) + las 6 pestañas (Mis Rutinas, Conceptos, Tips,
 * Videos, Recursos, Favoritos). "Mis Rutinas" es el CRUD principal de la
 * app; el resto es contenido de referencia.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val titulosTabs = listOf(
        R.string.tab_mis_rutinas,
        R.string.tab_conceptos,
        R.string.tab_tips,
        R.string.tab_videos,
        R.string.tab_recursos,
        R.string.tab_favoritos
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.viewPager.adapter = SectionPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = getString(titulosTabs[position])
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_acerca_de -> {
                mostrarDialogo(getString(R.string.menu_acerca_de), getString(R.string.acerca_de_texto))
                true
            }
            R.id.action_ayuda -> {
                mostrarDialogo(getString(R.string.menu_ayuda), getString(R.string.ayuda_texto))
                true
            }
            R.id.action_cerrar_sesion -> {
                cerrarSesion()
                true
            }
            R.id.action_salir -> {
                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun cerrarSesion() {
        SessionManager(this).cerrarSesion()
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }

    private fun mostrarDialogo(titulo: String, mensaje: String) {
        AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(mensaje)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }
}