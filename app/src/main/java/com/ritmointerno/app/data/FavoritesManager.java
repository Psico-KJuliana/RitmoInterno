package com.ritmointerno.app.data

import android.content.Context

/**
 * Guarda qué ítems marcó el usuario como favorito usando SharedPreferences.
 * Solo se guarda un conjunto de ids (ej. "concepto_1", "video_1"); el
 * contenido en sí siempre sale de ContentRepository. No requiere registro
 * ni conexión, tal como se definió en el documento de diseño.
 */
class FavoritesManager(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun esFavorito(id: String): Boolean =
    obtenerIds().contains(id)

    fun alternar(id: String) {
        val ids = obtenerIds().toMutableSet()
        if (ids.contains(id)) ids.remove(id) else ids.add(id)
        prefs.edit().putStringSet(KEY_FAVORITOS, ids).apply()
    }

    fun obtenerIds(): Set<String> =
            prefs.getStringSet(KEY_FAVORITOS, emptySet()) ?: emptySet()

    companion object {
        private const val PREFS_NAME = "ritmo_interno_favoritos"
        private const val KEY_FAVORITOS = "ids_favoritos"
    }
}