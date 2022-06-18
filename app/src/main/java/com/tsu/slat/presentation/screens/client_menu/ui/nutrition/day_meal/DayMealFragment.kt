package com.tsu.slat.presentation.screens.client_menu.ui.nutrition.day_meal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tsu.slat.R
import com.tsu.slat.databinding.FragmentDayMealBinding
import com.tsu.slat.databinding.FragmentNewsBinding


class DayMealFragment : Fragment() {

    private var _binding: FragmentDayMealBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDayMealBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}