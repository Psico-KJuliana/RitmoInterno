package com.ritmointerno.app.data

import com.ritmointerno.app.data.db.UserDao
import com.ritmointerno.app.data.db.UserEntity

/** Resultado de un intento de registro o login, para que la UI reaccione. */
sealed class AuthResultado {
    data class Exito(val usuario: UserEntity) : AuthResultado()
    data class Error(val mensaje: String) : AuthResultado()
}

/**
 * Repositorio de autenticación: valida y guarda usuarios en Room. Es la
 * única clase que conoce el DAO; el ViewModel no sabe que existe SQLite.
 */
class AuthRepository(private val userDao: UserDao) {

    suspend fun registrar(nombre: String, email: String, password: String): AuthResultado {
        if (nombre.isBlank()) {
            return AuthResultado.Error("El nombre no puede estar vacío.")
        }
        if (email.isBlank() || !email.contains("@")) {
            return AuthResultado.Error("Ingresa un correo válido.")
        }
        if (password.length < 6) {
            return AuthResultado.Error("La contraseña debe tener al menos 6 caracteres.")
        }

        val emailNormalizado = email.trim().lowercase()
        if (userDao.existeEmail(emailNormalizado) > 0) {
            return AuthResultado.Error("Ya existe una cuenta con ese correo.")
        }

        val usuario = UserEntity(
            nombre = nombre.trim(),
            email = emailNormalizado,
            passwordHash = PasswordHasher.hash(password)
        )
        val id = userDao.insertar(usuario)
        return AuthResultado.Exito(usuario.copy(id = id))
    }

    suspend fun iniciarSesion(email: String, password: String): AuthResultado {
        if (email.isBlank() || password.isBlank()) {
            return AuthResultado.Error("Completa correo y contraseña.")
        }

        val usuario = userDao.buscarPorEmail(email.trim().lowercase())
            ?: return AuthResultado.Error("No existe una cuenta con ese correo.")

        return if (usuario.passwordHash == PasswordHasher.hash(password)) {
            AuthResultado.Exito(usuario)
        } else {
            AuthResultado.Error("Contraseña incorrecta.")
        }
    }
}