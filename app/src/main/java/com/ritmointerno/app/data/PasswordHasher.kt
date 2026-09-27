package com.ritmointerno.app.data

import java.security.MessageDigest

/**
 * Hash educativo de contraseñas (SHA-256) para no guardarlas en texto
 * plano en la base de datos local, tal como sugiere el documento del
 * profesor. No sustituye un esquema de producción (faltaría salt/BCrypt),
 * pero es suficiente para los fines de este proyecto académico.
 */
object PasswordHasher {

    fun hash(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString(separator = "") { "%02x".format(it) }
    }
}