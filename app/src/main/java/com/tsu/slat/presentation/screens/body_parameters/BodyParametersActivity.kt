package com.tsu.slat.presentation.screens.body_parameters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tsu.slat.R
import com.tsu.slat.databinding.ActivityBodyParametersBinding
import com.tsu.slat.presentation.screens.add_workout.AddWorkoutActivity
import com.tsu.slat.presentation.screens.body_params_info.BodyParamsInfoActivity

class BodyParametersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBodyParametersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBodyParametersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
    }

    private fun initListeners() {
        binding.btnWeight.setOnClickListener {
            val intent = Intent(this, BodyParamsInfoActivity::class.java)
            startActivity(intent)
        }
    }

}