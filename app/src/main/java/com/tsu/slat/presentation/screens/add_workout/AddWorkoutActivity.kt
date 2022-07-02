package com.tsu.slat.presentation.screens.add_workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.tsu.slat.data.entity.Workout
import com.tsu.slat.databinding.ActivityAddWorkoutBinding
import java.time.LocalDate
import java.util.*

class AddWorkoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddWorkoutBinding

    private val viewModel by viewModels<AddWorkoutViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
    }

    private fun initListeners() {
        binding.btnAddWorkout.setOnClickListener {
            val name = binding.editName.text.toString()
            val amount = binding.editAmount.text.toString().toDouble()
            val description = binding.editDescription.text.toString()
            val date = LocalDate.now().toString()
            val timestamp = System.currentTimeMillis().toInt()
            val workout = Workout(UUID.randomUUID().toString(), name, date, timestamp, amount, description)
            viewModel.addWorkout(workout)
            finish()
        }
    }
}