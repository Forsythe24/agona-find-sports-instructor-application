package com.solopov.feature_event_calendar_impl.presentation

import androidx.recyclerview.widget.RecyclerView
import com.solopov.feature_event_calendar_impl.databinding.ItemEventBinding
import com.solopov.feature_event_calendar_impl.presentation.model.EventItem

class EventViewHolder(private val viewBinding: ItemEventBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bindItem(item: EventItem) {
    }
}
