package com.ritmointerno.app.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ritmointerno.app.R
import com.ritmointerno.app.data.AuthResultado
import com.ritmointerno.app.databinding.ActivityRegisterBinding

/**
 * Vista de Autenticación (registro): crea un usuario nuevo en Room a
 * través de AuthViewModel. Al terminar vuelve a Login para que inicie
 * sesión con la cuenta recién creada.
 */
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, AuthViewModelFactory(this))[AuthViewModel::class.java]

        binding.buttonRegistrar.setOnClickListener { intentarRegistro() }
        binding.textIrLogin.setOnClickListener { finish() }

        viewModel.resultado.observe(this) { resultado -> manejarResultado(resultado) }
    }

    private fun intentarRegistro() {
        val nombre = binding.editNombre.text?.toString().orEmpty()
        val email = binding.editEmail.text?.toString().orEmpty()
        val password = binding.editPassword.text?.toString().orEmpty()
        val confirmar = binding.editConfirmarPassword.text?.toString().orEmpty()

        if (password != confirmar) {
            Toast.makeText(this, R.string.error_passwords_no_coinciden, Toast.LENGTH_SHORT).show()
            return
        }

        mostrarCargando(true)
        viewModel.registrar(nombre, email, password)
    }

    private fun manejarResultado(resultado: AuthResultado) {
        mostrarCargando(false)
        when (resultado) {
            is AuthResultado.Exito -> {
                Toast.makeText(this, R.string.cuenta_creada, Toast.LENGTH_SHORT).show()
                finish()
            }
            is AuthResultado.Error -> {
                Toast.makeText(this, resultado.mensaje, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mostrarCargando(cargando: Boolean) {
        binding.progressRegistro.visibility = if (cargando) View.VISIBLE else View.GONE
        binding.buttonRegistrar.isEnabled = !cargando
    }
}