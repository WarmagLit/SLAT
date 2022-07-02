package com.tsu.slat.presentation.screens.train_parameters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tsu.slat.R
import com.tsu.slat.data.entity.Workout
import com.tsu.slat.data.entity.response.ChatResponse
import com.tsu.slat.databinding.ChatItemBinding
import com.tsu.slat.databinding.WorkoutItemBinding
import com.tsu.slat.presentation.screens.chat.ChatActivity

class TrainAdapter(val context: Context, private val listener: OnItemClickListener): RecyclerView.Adapter<TrainAdapter.WorkoutHolder>() {

    var workoutList = ArrayList<Workout>()

    inner class WorkoutHolder(item: View): RecyclerView.ViewHolder(item),
        View.OnClickListener {
        val binding = WorkoutItemBinding.bind(item)
        private lateinit var userId: String
        fun bind(workout: Workout) = with(binding){
            textName.text = workout.name
            txtDescription.text = workout.description
            txtAmount.text = workout.amount.toString()
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.workout_item, parent, false)
        return WorkoutHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutHolder, position: Int) {
        holder.bind(workoutList[position])

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
        return workoutList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addWorkout(workout: Workout) {
        workoutList.add(workout)
        notifyDataSetChanged()
    }

}