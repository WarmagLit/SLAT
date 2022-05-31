package com.tsu.slat.presentation.screens.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tsu.slat.databinding.ActivityMainBinding
import com.tsu.slat.presentation.screens.client_menu.ClientMenuActivity
import com.tsu.slat.presentation.screens.foodsearch.FoodSearchActivity
import com.tsu.slat.presentation.screens.sign_in.SignInActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            //val intent = Intent(this, SignInActivity::class.java)
            val intent = Intent(this, ClientMenuActivity::class.java)
            startActivity(intent)
        }
    }
}