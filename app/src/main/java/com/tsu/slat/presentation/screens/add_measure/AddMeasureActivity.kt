package com.tsu.slat.presentation.screens.add_measure

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.tsu.slat.data.entity.BodyMeasure
import com.tsu.slat.data.entity.Workout
import com.tsu.slat.databinding.ActivityAddMeasureBinding
import com.tsu.slat.databinding.ActivityAddWorkoutBinding
import com.tsu.slat.presentation.screens.add_workout.AddWorkoutViewModel
import java.time.LocalDate
import java.util.*

class AddMeasureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddMeasureBinding

    private val viewModel by viewModels<AddMeasureViewModel>()

    private var selectedDate = LocalDate.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMeasureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
    }

    private fun initListeners() {
        binding.btnAddMeasure.setOnClickListener {
            //val da = binding.editName.text.toString()
            if (binding.editAmount.text.toString() == "")
                return@setOnClickListener
            val amount = binding.editAmount.text.toString().toDouble()
            val timestamp = System.currentTimeMillis().toInt()
            val measure = BodyMeasure(UUID.randomUUID().toString(), selectedDate.toString(), timestamp, amount)
            viewModel.addMeasure(measure)
            finish()
        }

        binding.editDate.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->

                // Display Selected date in textbox
                selectedDate = LocalDate.of(year, monthOfYear+1, dayOfMonth)
                binding.editDate.setText("" + dayOfMonth + " " + (monthOfYear+1).toString() + ", " + year)

            }, year, month, day)

            dpd.show()
        }
    }
}