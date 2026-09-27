package com.ritmointerno.app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ritmointerno.app.R
import com.ritmointerno.app.data.ContentItem
import com.ritmointerno.app.data.ContentType
import com.ritmointerno.app.data.FavoritesManager
import com.ritmointerno.app.data.SubConcepto
import com.ritmointerno.app.databinding.ItemContentBinding

/**
 * Adapter genérico reutilizado por las 5 pestañas (Conceptos, Tips, Videos,
 * Recursos y Favoritos): todas muestran el mismo tipo de tarjeta, solo
 * cambian los datos que reciben.
 *
 * onFavoritoCambiado se usa para que, si estamos en Favoritos, la tarjeta
 * desaparezca de inmediato al desmarcarla.
 */
class ContentAdapter(
        private var items: List<ContentItem>,
        private val favoritesManager: FavoritesManager,
        private val onAbrirVideoOrLink: (ContentItem) -> Unit,
private val onFavoritoCambiado: () -> Unit
) : RecyclerView.Adapter<ContentAdapter.ContentViewHolder>() {

inner class ContentViewHolder(val binding: ItemContentBinding) :
        RecyclerView.ViewHolder(binding.root)

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
    val binding = ItemContentBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
    )
    return ContentViewHolder(binding)
}

override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
    val item = items[position]
    val b = holder.binding

    b.textTitulo.text = item.titulo
    b.textDescripcion.text = item.descripcion

    // Autor: solo aparece si el ítem trae uno (ej. videos)
    if (item.autor.isNullOrBlank()) {
        b.textAutor.visibility = View.GONE
    } else {
        b.textAutor.visibility = View.VISIBLE
        b.textAutor.text = "Por: ${item.autor}"
    }

    // El ícono de "play" solo se muestra para videos
    b.imagePlay.visibility =
    if (item.type == ContentType.VIDEO) View.VISIBLE else View.GONE

    pintarCorazon(b, favoritesManager.esFavorito(item.id))
    pintarSubconceptos(b, item.subconceptos)

    b.buttonFavorito.setOnClickListener {
        favoritesManager.alternar(item.id)
        pintarCorazon(b, favoritesManager.esFavorito(item.id))
        onFavoritoCambiado()
    }

    // Videos y recursos abren un enlace externo; conceptos y tips solo
    // muestran el texto (no necesitan click)
    if (item.type == ContentType.VIDEO || item.type == ContentType.RECURSO) {
        b.root.setOnClickListener { onAbrirVideoOrLink(item) }
    } else {
        b.root.setOnClickListener(null)
    }
}

/**
 * Pinta la lista de subconceptos (si el ítem trae alguno) debajo de la
 * fila principal de la tarjeta. Si viene null o vacía, el contenedor
 * queda oculto y la tarjeta se ve exactamente igual que un ítem sin
 * subconceptos.
 */
private fun pintarSubconceptos(b: ItemContentBinding, subconceptos: List<SubConcepto>?) {
    b.subconceptosContainer.removeAllViews()

    if (subconceptos.isNullOrEmpty()) {
        b.subconceptosContainer.visibility = View.GONE
        return
    }

    b.subconceptosContainer.visibility = View.VISIBLE
    val contexto = b.subconceptosContainer.context

    subconceptos.forEach { sub ->
            val titulo = TextView(contexto).apply {
        text = sub.titulo
        setTextColor(contexto.getColor(R.color.blanco_crema))
        textSize = 13f
        setTypeface(typeface, android.graphics.Typeface.BOLD)
    }
        val texto = TextView(contexto).apply {
            text = sub.texto
            setTextColor(contexto.getColor(R.color.greige))
            textSize = 13f
        }

        val fila = LinearLayout(contexto).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 8, 0, 0)
            addView(titulo)
            addView(texto)
        }
        b.subconceptosContainer.addView(fila)
    }
}

private fun pintarCorazon(b: ItemContentBinding, activo: Boolean) {
    b.buttonFavorito.setImageResource(
    if (activo) R.drawable.ic_heart_filled
    else R.drawable.ic_heart_border
        )
}

override fun getItemCount(): Int = items.size

fun actualizarLista(nuevaLista: List<ContentItem>) {
    items = nuevaLista
    notifyDataSetChanged()
}
}
