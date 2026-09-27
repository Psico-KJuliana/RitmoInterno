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
import com.ritmointerno.app.data.ContentType
import com.ritmointerno.app.data.FavoritesManager
import com.ritmointerno.app.databinding.FragmentContentListBinding

class ContentListFragment : Fragment() {

    private var _binding: FragmentContentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoritesManager: FavoritesManager

    private val tipo: ContentType by lazy {
        ContentType.valueOf(requireArguments().getString(ARG_TIPO)!!)
    }

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
        val adapter = ContentAdapter(
            items = ContentRepository.listaPara(tipo),
            favoritesManager = favoritesManager,
            onAbrirVideoOrLink = { item -> abrirEnlace(item.videoUrl ?: item.linkUrl) },
            onFavoritoCambiado = { /* nada que refrescar aquí, solo cambia el ícono */ }
        )
        binding.recyclerView.adapter = adapter
    }

    private fun abrirEnlace(url: String?) {
        if (url.isNullOrBlank()) return
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_TIPO = "arg_tipo"

        fun newInstance(tipo: ContentType): ContentListFragment {
            val fragment = ContentListFragment()
            fragment.arguments = Bundle().apply {
                putString(ARG_TIPO, tipo.name)
            }
            return fragment
        }
    }
}