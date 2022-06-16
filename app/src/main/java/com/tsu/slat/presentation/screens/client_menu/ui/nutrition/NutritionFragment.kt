package com.tsu.slat.presentation.screens.client_menu.ui.nutrition

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tsu.slat.databinding.FragmentNutritionBinding
import com.tsu.slat.presentation.screens.client_menu.ui.nutrition.day_meal.DayMealAdapter
import com.tsu.slat.presentation.screens.nutrition.NutritionActivity

class NutritionFragment : Fragment() {

    private var _binding: FragmentNutritionBinding? = null

    private val binding get() = _binding!!

    private lateinit var adapter: DayMealAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(NutritionViewModel::class.java)

        _binding = FragmentNutritionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = DayMealAdapter(childFragmentManager);
        binding.viewPager.adapter = adapter


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}