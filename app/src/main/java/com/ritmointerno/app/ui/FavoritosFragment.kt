package com.ritmointerno.app.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ritmointerno.app.data.ContentRepository
import com.ritmointerno.app.data.FavoritesManager
import com.ritmointerno.app.databinding.FragmentContentListBinding

class FavoritosFragment : Fragment() {

    private var _binding: FragmentContentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoritesManager: FavoritesManager
    private lateinit var adapter: ContentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoritesManager = FavoritesManager(requireContext())

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ContentAdapter(
            items = favoritosActuales(),
            favoritesManager = favoritesManager,
            onAbrirVideoOrLink = { item -> abrirEnlace(item.videoUrl ?: item.linkUrl) },
            onFavoritoCambiado = { refrescar() }
        )
        binding.recyclerView.adapter = adapter
        refrescar()
    }

    // Se llama cada vez que el usuario vuelve a esta pestaña, por si marcó
    // o desmarcó algo desde Conceptos, Tips, Videos o Recursos.
    override fun onResume() {
        super.onResume()
        if (_binding != null) refrescar()
    }

    private fun favoritosActuales() =
        ContentRepository.todos().filter { favoritesManager.esFavorito(it.id) }

    private fun refrescar() {
        val lista = favoritosActuales()
        adapter.actualizarLista(lista)
        binding.textViewVacio.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun abrirEnlace(url: String?) {
        if (url.isNullOrBlank()) return
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}