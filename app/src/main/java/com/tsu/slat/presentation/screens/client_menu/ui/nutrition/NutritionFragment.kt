package com.tsu.slat.presentation.screens.client_menu.ui.nutrition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.tsu.slat.databinding.FragmentNutritionBinding
import com.tsu.slat.presentation.screens.client_menu.ui.nutrition.day_meal.DayMealAdapter
import java.time.LocalDate

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
        binding.viewPager.currentItem = 1

        initListeners()

        return binding.root
    }

    private fun initListeners() {
        binding.btnGoBack.setOnClickListener {
            if (binding.viewPager.currentItem != 0) {
                binding.viewPager.currentItem = binding.viewPager.currentItem - 1
            }
        }

        binding.btnGoForward.setOnClickListener {
            if (binding.viewPager.currentItem != 2) {
                binding.viewPager.currentItem = binding.viewPager.currentItem + 1
            }
        }

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                var date = "Сегодня (" + LocalDate.now().toString() + ")"
                if (position == 0) {
                    date = "Вчера (" + LocalDate.now().minusDays(1).toString() + ")"
                }
                if (position == 2) {
                    date = "Завтра (" + LocalDate.now().plusDays(1).toString() + ")"
                }
                binding.txtDay.text = date
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}