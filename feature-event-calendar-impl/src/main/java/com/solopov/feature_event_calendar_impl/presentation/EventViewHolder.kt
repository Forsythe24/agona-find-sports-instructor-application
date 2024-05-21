package com.solopov.feature_event_calendar_impl.presentation

import androidx.recyclerview.widget.RecyclerView
import com.solopov.feature_event_calendar_impl.R
import com.solopov.feature_event_calendar_impl.databinding.ItemEventBinding
import com.solopov.feature_event_calendar_impl.presentation.model.EventItem

class EventViewHolder(
    private val viewBinding: ItemEventBinding,
    private val onItemClicked: (EventItem) -> Unit
) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bindItem(item: EventItem) {
        with(viewBinding) {
            with(item) {
                viewBinding.nameTv.text = name
                personNameTv.text = personName
                placeTv.text = place
                timeRangeTv.text = root.context.getString(R.string.time_range_template)
                    .format(convertMinutesToHHmm(startTime), convertMinutesToHHmm(endTime))
            }

            root.setOnClickListener {
                onItemClicked(item)
            }
        }
    }

    private fun convertMinutesToHHmm(minutesTotalNumber: Int): String {
        val hours = minutesTotalNumber / 60
        val minutes = minutesTotalNumber % 60
        return String.format(
            viewBinding.root.context.getString(R.string.time_template),
            hours,
            minutes
        )
    }
}
