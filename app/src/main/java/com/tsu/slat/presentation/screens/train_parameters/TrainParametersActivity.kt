package com.tsu.slat.presentation.screens.train_parameters

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.tsu.slat.data.entity.Workout
import com.tsu.slat.databinding.ActivityTrainParametersBinding
import com.tsu.slat.presentation.screens.add_workout.AddWorkoutActivity

class TrainParametersActivity : AppCompatActivity(), TrainAdapter.OnItemClickListener {

    private lateinit var binding: ActivityTrainParametersBinding

    private lateinit var adapter: TrainAdapter

    private val viewModel by viewModels<TrainParametersViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrainParametersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObservers()
        initAdapter()
        initListeners()
    }


    private fun addChartData(workouts: List<Workout>) {
        val entries = mutableListOf<Entry>()
        for ((count, workout) in workouts.withIndex()) {
            if (workout.amount != null)
                entries.add(Entry(count.toFloat(), workout.amount.toFloat()))
        }
        val dataset = LineDataSet(entries, "Results")
        val lineData = LineData(dataset)
        binding.chart.data = lineData
        binding.chart.invalidate()
    }

    private fun initObservers() {
        viewModel.workouts.observe(this) {
            adapter.workoutList.clear()
            for (workout in it) {
                adapter.addWorkout(workout)
            }
            addChartData(it)
        }
    }

    private fun initListeners() {
        binding.btnAddWorkout.setOnClickListener {
            val intent = Intent(this, AddWorkoutActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TrainAdapter(this, this)
        binding.recyclerView.adapter = adapter
    }

    override fun onItemClick(userId: String) {
        TODO("Not yet implemented")
    }

}