package com.ritmointerno.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ritmointerno.app.MainActivity
import com.ritmointerno.app.R
import com.ritmointerno.app.data.AuthResultado
import com.ritmointerno.app.data.SessionManager
import com.ritmointerno.app.databinding.ActivityLoginBinding

/**
 * Vista de Autenticación (login): valida contra los usuarios guardados en
 * Room a través de AuthViewModel y, si son correctos, abre sesión local
 * con SessionManager antes de pasar a MainActivity.
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        viewModel = ViewModelProvider(this, AuthViewModelFactory(this))[AuthViewModel::class.java]

        binding.buttonLogin.setOnClickListener { intentarLogin() }
        binding.textIrRegistro.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        viewModel.resultado.observe(this) { resultado -> manejarResultado(resultado) }
    }

    private fun intentarLogin() {
        val email = binding.editEmail.text?.toString().orEmpty()
        val password = binding.editPassword.text?.toString().orEmpty()
        mostrarCargando(true)
        viewModel.iniciarSesion(email, password)
    }

    private fun manejarResultado(resultado: AuthResultado) {
        mostrarCargando(false)
        when (resultado) {
            is AuthResultado.Exito -> {
                sessionManager.iniciarSesion(resultado.usuario.id, resultado.usuario.nombre)
                Toast.makeText(
                    this, getString(R.string.bienvenida, resultado.usuario.nombre), Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            is AuthResultado.Error -> {
                Toast.makeText(this, resultado.mensaje, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mostrarCargando(cargando: Boolean) {
        binding.progressLogin.visibility = if (cargando) View.VISIBLE else View.GONE
        binding.buttonLogin.isEnabled = !cargando
    }
}