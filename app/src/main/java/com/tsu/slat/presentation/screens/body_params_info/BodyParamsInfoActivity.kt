package com.tsu.slat.presentation.screens.body_params_info

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.tsu.slat.data.entity.BodyMeasure
import com.tsu.slat.data.entity.Workout
import com.tsu.slat.databinding.ActivityBodyParametersBinding
import com.tsu.slat.databinding.ActivityBodyParamsInfoBinding
import com.tsu.slat.presentation.screens.add_measure.AddMeasureActivity
import com.tsu.slat.presentation.screens.add_workout.AddWorkoutActivity
import com.tsu.slat.presentation.screens.train_parameters.TrainAdapter
import com.tsu.slat.presentation.screens.train_parameters.TrainParametersViewModel

class BodyParamsInfoActivity : AppCompatActivity() , BodyParamsAdapter.OnItemClickListener{
    private lateinit var binding: ActivityBodyParamsInfoBinding

    private val viewModel by viewModels<BodyParamsInfoViewModel>()

    private lateinit var adapter: BodyParamsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBodyParamsInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObservers()
        initListeners()
        initAdapter()
    }

    private fun initAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = BodyParamsAdapter(this, this)
        binding.recyclerView.adapter = adapter
    }

    private fun initObservers() {
        viewModel.measures.observe(this) {
            adapter.measureList.clear()
            for (measure in it) {
                adapter.addMeasure(measure)
            }
            addChartData(it)
        }
    }

    private fun initListeners() {
        binding.btnAddMeasure.setOnClickListener {
            val intent = Intent(this, AddMeasureActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addChartData(measures: List<BodyMeasure>) {
        val entries = mutableListOf<Entry>()
        for ((count, workout) in measures.withIndex()) {
            entries.add(Entry(count.toFloat(), workout.value.toFloat()))
        }
        val dataset = LineDataSet(entries, "Results")
        dataset.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataset.color = Color.BLUE
        val lineData = LineData(dataset)

        binding.chart.data = lineData
        binding.chart.invalidate()
    }

    override fun onItemClick(userId: String) {
        // Edit or delete
    }
}