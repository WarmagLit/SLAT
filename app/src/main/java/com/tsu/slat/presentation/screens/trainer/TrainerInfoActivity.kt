package com.tsu.slat.presentation.screens.trainer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tsu.slat.databinding.ActivityTrainerInfoBinding

class TrainerInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTrainerInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrainerInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}