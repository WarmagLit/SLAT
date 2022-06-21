package com.tsu.slat.presentation.screens.client_menu

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.tsu.slat.R
import com.tsu.slat.databinding.ActivityClientMenuBinding
import com.tsu.slat.presentation.screens.client_menu.ui.nutrition.NutritionFragment

class ClientMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientMenuBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_client_menu)

        navView.setupWithNavController(navController)

        val setFragment = intent.getStringExtra("fragment")

        when(setFragment) {
            "news" -> loadFragment(R.id.navigation_news)
            "nutrition" -> loadFragment(R.id.navigation_nutrition)
            "chat" -> loadFragment(R.id.navigation_chats)
            "profile" -> loadFragment(R.id.navigation_profile)
        }

    }

    private fun loadFragment(id: Int) {
        navController.navigate(id)
    }


}