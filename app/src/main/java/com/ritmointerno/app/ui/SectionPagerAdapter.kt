package com.ritmointerno.app.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ritmointerno.app.data.ContentType
import com.ritmointerno.app.ui.rutinas.RutinaListFragment

/**
 * Arma las 6 pestañas del ViewPager2 en orden:
 * Mis Rutinas (CRUD principal), Conceptos, Tips, Videos, Recursos, Favoritos.
 */
class SectionPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> RutinaListFragment()
        1 -> ContentListFragment.newInstance(ContentType.CONCEPTO)
        2 -> ContentListFragment.newInstance(ContentType.TIP)
        3 -> ContentListFragment.newInstance(ContentType.VIDEO)
        4 -> ContentListFragment.newInstance(ContentType.RECURSO)
        else -> FavoritosFragment()
    }
}