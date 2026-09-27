package com.ritmointerno.app.ui.rutinas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ritmointerno.app.R
import com.ritmointerno.app.data.db.RutinaEntity
import com.ritmointerno.app.databinding.ItemRutinaBinding

/** Adapter del RecyclerView de la Vista 2 (listado de rutinas). */
class RutinaAdapter(
    private var items: List<RutinaEntity>,
    private val onClick: (RutinaEntity) -> Unit,
    private val onFavoritoClick: (RutinaEntity) -> Unit
) : RecyclerView.Adapter<RutinaAdapter.RutinaViewHolder>() {

    inner class RutinaViewHolder(val binding: ItemRutinaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RutinaViewHolder {
        val binding = ItemRutinaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RutinaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RutinaViewHolder, position: Int) {
        val rutina = items[position]
        val b = holder.binding

        b.textTitulo.text = rutina.titulo
        b.textDetalle.text = b.root.context.getString(
            R.string.rutina_detalle_corto, rutina.duracionMinutos, rutina.fecha
        )
        b.buttonFavorito.setImageResource(
            if (rutina.favorito) R.drawable.ic_heart_filled else R.drawable.ic_heart_border
        )
        b.buttonFavorito.setOnClickListener { onFavoritoClick(rutina) }
        b.root.setOnClickListener { onClick(rutina) }
    }

    override fun getItemCount(): Int = items.size

    fun actualizarLista(nuevaLista: List<RutinaEntity>) {
        items = nuevaLista
        notifyDataSetChanged()
    }
}