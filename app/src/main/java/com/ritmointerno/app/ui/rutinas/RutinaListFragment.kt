package com.ritmointerno.app.ui.rutinas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ritmointerno.app.data.SessionManager
import com.ritmointerno.app.data.db.RutinaEntity
import com.ritmointerno.app.databinding.FragmentRutinaListBinding

/**
 * Vista 2 (Listado): pestaña "Mis Rutinas" dentro de MainActivity. Es el
 * corazón del CRUD -- muestra las rutinas del usuario con sesión activa,
 * permite crear (FAB), abrir el detalle y marcar favorito directamente
 * desde la tarjeta. La lista se actualiza sola (LiveData) al crear,
 * editar o eliminar, sin reiniciar la app.
 */
class RutinaListFragment : Fragment() {

    private var _binding: FragmentRutinaListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RutinaListViewModel
    private lateinit var adapter: RutinaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRutinaListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = SessionManager(requireContext()).obtenerUserId()
        viewModel = ViewModelProvider(
            this, RutinaListViewModelFactory(requireContext(), userId)
        )[RutinaListViewModel::class.java]

        adapter = RutinaAdapter(
            items = emptyList(),
            onClick = { rutina -> abrirDetalle(rutina) },
            onFavoritoClick = { rutina -> viewModel.alternarFavorito(rutina) }
        )
        binding.recyclerViewRutinas.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewRutinas.adapter = adapter

        binding.fabNuevaRutina.setOnClickListener {
            startActivity(Intent(requireContext(), RutinaFormActivity::class.java))
        }

        viewModel.rutinas.observe(viewLifecycleOwner) { lista ->
            adapter.actualizarLista(lista)
            binding.textRutinasVacio.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun abrirDetalle(rutina: RutinaEntity) {
        val intent = Intent(requireContext(), RutinaDetailActivity::class.java)
        intent.putExtra(RutinaDetailActivity.EXTRA_RUTINA_ID, rutina.id)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}