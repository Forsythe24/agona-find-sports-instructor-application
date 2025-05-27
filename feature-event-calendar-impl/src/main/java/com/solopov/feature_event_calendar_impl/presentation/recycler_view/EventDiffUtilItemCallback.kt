package com.solopov.feature_event_calendar_impl.presentation.recycler_view

import androidx.recyclerview.widget.DiffUtil
import com.solopov.feature_event_calendar_impl.presentation.model.EventItem

class EventDiffUtilItemCallback : DiffUtil.ItemCallback<EventItem>() {
    override fun areItemsTheSame(oldItem: EventItem, newItem: EventItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: EventItem, newItem: EventItem): Boolean {
        // able to compare like this because EventItem is a data class
        return oldItem == newItem
    }
}
