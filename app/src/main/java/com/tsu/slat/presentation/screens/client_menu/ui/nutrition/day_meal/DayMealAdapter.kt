package com.tsu.slat.presentation.screens.client_menu.ui.nutrition.day_meal

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class DayMealAdapter(fragmentManager: FragmentManager?) : FragmentPagerAdapter(fragmentManager!!) {
    // Returns total number of pages
    override fun getCount(): Int {
        return NUM_ITEMS
    }

    // Returns the fragment to display for that page
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> DayMealFragment()
            1 -> DayMealFragment()
            2 -> DayMealFragment()
            else -> DayMealFragment()
        }
    }

    // Returns the page title for the top indicator
    override fun getPageTitle(position: Int): CharSequence? {
        return "Page $position"
    }

    companion object {
        private const val NUM_ITEMS = 3
    }
}