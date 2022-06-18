package com.tsu.slat.presentation.screens.calendar

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tsu.slat.databinding.CalendarEventItemLayoutBinding

class CalendarEventsAdapter(val onClick: (CalendarEvent) -> Unit) :
    RecyclerView.Adapter<CalendarEventsAdapter.Example3EventsViewHolder>() {

    val events = mutableListOf<CalendarEvent>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Example3EventsViewHolder {
        return Example3EventsViewHolder(
            CalendarEventItemLayoutBinding.inflate(parent.context.layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(viewHolder: Example3EventsViewHolder, position: Int) {
        viewHolder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    inner class Example3EventsViewHolder(private val binding: CalendarEventItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onClick(events[bindingAdapterPosition])
            }
        }

        fun bind(event: CalendarEvent) {
            binding.itemEventText.text = event.text
        }
    }
}