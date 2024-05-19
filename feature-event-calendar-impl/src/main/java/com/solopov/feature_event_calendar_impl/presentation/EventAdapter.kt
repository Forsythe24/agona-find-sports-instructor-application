package com.solopov.feature_event_calendar_impl.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.solopov.feature_event_calendar_impl.databinding.ItemEventBinding
import com.solopov.feature_event_calendar_impl.presentation.model.EventItem

class EventAdapter : ListAdapter<EventItem, EventViewHolder>(EventDiffUtilItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            ItemEventBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
        )
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {

        holder.bindItem(getItem(position))
    }
}
