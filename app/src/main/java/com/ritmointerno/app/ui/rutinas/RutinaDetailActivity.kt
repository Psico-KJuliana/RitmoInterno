package com.ritmointerno.app.ui.rutinas

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ritmointerno.app.R
import com.ritmointerno.app.databinding.ActivityRutinaDetalleBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Vista 3 (Detalle): muestra una rutina y ofrece sus acciones contextuales
 * -- editar, eliminar (con confirmación) y marcar/desmarcar favorita.
 */
class RutinaDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRutinaDetalleBinding
    private lateinit var viewModel: RutinaDetailViewModel
    private var rutinaId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRutinaDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rutinaId = intent.getLongExtra(EXTRA_RUTINA_ID, -1L)
        viewModel = ViewModelProvider(
            this, RutinaDetailViewModelFactory(this)
        )[RutinaDetailViewModel::class.java]

        viewModel.rutina.observe(this) { rutina ->
            if (rutina == null) return@observe
            binding.textTitulo.text = rutina.titulo
            binding.textDescripcion.text = rutina.descripcion
            binding.textDuracion.text = getString(R.string.rutina_duracion_formato, rutina.duracionMinutos)
            binding.textFecha.text = rutina.fecha
            binding.buttonFavorito.setImageResource(
                if (rutina.favorito) R.drawable.ic_heart_filled else R.drawable.ic_heart_border
            )
        }

        viewModel.eliminado.observe(this) { eliminado ->
            if (eliminado) {
                Toast.makeText(this, R.string.rutina_eliminada, Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        binding.buttonFavorito.setOnClickListener { viewModel.alternarFavorito() }
        binding.buttonEditar.setOnClickListener {
            val intent = Intent(this, RutinaFormActivity::class.java)
            intent.putExtra(RutinaFormActivity.EXTRA_RUTINA_ID, rutinaId)
            startActivity(intent)
        }
        binding.buttonEliminar.setOnClickListener { confirmarEliminar() }
    }

    // Se vuelve a cargar cada vez que la pantalla vuelve a primer plano,
    // por si se editó la rutina desde RutinaFormActivity.
    override fun onResume() {
        super.onResume()
        viewModel.cargar(rutinaId)
    }

    private fun confirmarEliminar() {
        MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_RitmoInterno_Dialog)
            .setTitle(R.string.confirmar_eliminar_titulo)
            .setMessage(R.string.confirmar_eliminar_mensaje)
            .setPositiveButton(R.string.eliminar) { _, _ -> viewModel.eliminar() }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    companion object {
        const val EXTRA_RUTINA_ID = "extra_rutina_id"
    }
}