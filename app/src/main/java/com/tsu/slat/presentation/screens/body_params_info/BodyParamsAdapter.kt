package com.tsu.slat.presentation.screens.body_params_info

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tsu.slat.R
import com.tsu.slat.data.entity.BodyMeasure
import com.tsu.slat.data.entity.Workout
import com.tsu.slat.databinding.MeasureItemBinding
import com.tsu.slat.databinding.WorkoutItemBinding

class BodyParamsAdapter(val context: Context, private val listener: OnItemClickListener): RecyclerView.Adapter<BodyParamsAdapter.MeasureHolder>() {

    var measureList = ArrayList<BodyMeasure>()

    inner class MeasureHolder(item: View): RecyclerView.ViewHolder(item),
        View.OnClickListener {
        val binding = MeasureItemBinding.bind(item)
        private lateinit var userId: String
        fun bind(measure: BodyMeasure) = with(binding){
            textName.text = measure.date
            txtAmount.text = measure.value.toString()
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onItemClick(userId)
        }

    }

    interface OnItemClickListener {
        fun onItemClick(userId: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeasureHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.measure_item, parent, false)
        return MeasureHolder(view)
    }

    override fun onBindViewHolder(holder: MeasureHolder, position: Int) {
        holder.bind(measureList[position])

        holder.itemView.setOnClickListener {
            TODO()
            /*
            val chat = workoutList[position].chat
            if (chat != null) {
                val intent = Intent(context, ChatActivity::class.java)
                intent.putExtra("current_chat", chat)
                context.startActivity(intent)
            }*/
        }

    }

    override fun getItemCount(): Int {
        return measureList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addMeasure(measure: BodyMeasure) {
        measureList.add(measure)
        notifyDataSetChanged()
    }

}