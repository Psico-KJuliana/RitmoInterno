package com.ritmointerno.app.ui.rutinas

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ritmointerno.app.R
import com.ritmointerno.app.data.RutinaRepository
import com.ritmointerno.app.data.SessionManager
import com.ritmointerno.app.data.db.AppDatabase
import com.ritmointerno.app.databinding.ActivityRutinaFormBinding
import kotlinx.coroutines.launch
import java.util.Calendar

/**
 * Vista 4 (Formulario): se reutiliza para Crear y para Editar. Si llega
 * EXTRA_RUTINA_ID en el Intent, precarga los datos existentes y guarda
 * como actualización; si no llega, crea una rutina nueva. La validación
 * (título obligatorio, duración numérica > 0, fecha obligatoria) vive en
 * RutinaFormViewModel.
 */
class RutinaFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRutinaFormBinding
    private lateinit var viewModel: RutinaFormViewModel
    private lateinit var repository: RutinaRepository
    private var idExistente: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRutinaFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = RutinaRepository(AppDatabase.getInstance(this).rutinaDao())
        viewModel = ViewModelProvider(
            this, RutinaFormViewModelFactory(this)
        )[RutinaFormViewModel::class.java]

        configurarSelectorFecha()

        val id = intent.getLongExtra(EXTRA_RUTINA_ID, -1L)
        if (id != -1L) {
            idExistente = id
            title = getString(R.string.titulo_editar_rutina)
            cargarRutina(id)
        } else {
            title = getString(R.string.titulo_nueva_rutina)
        }

        binding.buttonGuardar.setOnClickListener { guardar() }

        viewModel.guardado.observe(this) { resultado ->
            when (resultado) {
                is GuardarResultado.Exito -> {
                    Toast.makeText(this, R.string.rutina_guardada, Toast.LENGTH_SHORT).show()
                    finish()
                }
                is GuardarResultado.Error -> {
                    Toast.makeText(this, resultado.mensaje, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun cargarRutina(id: Long) {
        lifecycleScope.launch {
            val rutina = repository.obtenerPorId(id) ?: return@launch
            binding.editTitulo.setText(rutina.titulo)
            binding.editDescripcion.setText(rutina.descripcion)
            binding.editDuracion.setText(rutina.duracionMinutos.toString())
            binding.editFecha.setText(rutina.fecha)
            binding.checkFavorito.isChecked = rutina.favorito
        }
    }

    private fun configurarSelectorFecha() {
        binding.editFecha.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    val fecha = String.format("%04d-%02d-%02d", year, month + 1, day)
                    binding.editFecha.setText(fecha)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun guardar() {
        val userId = SessionManager(this).obtenerUserId()
        viewModel.guardar(
            idExistente = idExistente,
            userId = userId,
            titulo = binding.editTitulo.text?.toString().orEmpty(),
            descripcion = binding.editDescripcion.text?.toString().orEmpty(),
            duracionTexto = binding.editDuracion.text?.toString().orEmpty(),
            fecha = binding.editFecha.text?.toString().orEmpty(),
            favorito = binding.checkFavorito.isChecked
        )
    }

    companion object {
        const val EXTRA_RUTINA_ID = "extra_rutina_id"
    }
}