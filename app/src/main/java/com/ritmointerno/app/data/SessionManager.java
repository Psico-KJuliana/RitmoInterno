package com.ritmointerno.app.data

import android.content.Context

/**
 * Guarda quién es el usuario con sesión activa (id y nombre) en
 * SharedPreferences. No guarda contraseñas ni datos sensibles: solo lo
 * necesario para saber si hay sesión y de quién es.
 */
class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun iniciarSesion(userId: Long, nombre: String) {
        prefs.edit()
                .putLong(KEY_USER_ID, userId)
                .putString(KEY_NOMBRE, nombre)
                .apply()
    }

    fun cerrarSesion() {
        prefs.edit().clear().apply()
    }

    fun haySesionActiva(): Boolean = obtenerUserId() != -1L

    fun obtenerUserId(): Long = prefs.getLong(KEY_USER_ID, -1L)

    fun obtenerNombre(): String = prefs.getString(KEY_NOMBRE, "") ?: ""

    companion object {
        private const val PREFS_NAME = "ritmo_interno_sesion"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_NOMBRE = "nombre"
    }
}