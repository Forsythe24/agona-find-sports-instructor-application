package com.solopov.feature_instructor_impl.presentation

import androidx.recyclerview.widget.DiffUtil

class InstructorDiffUtilItemCallback : DiffUtil.ItemCallback<InstructorsAdapter.ListItem>() {
    override fun areItemsTheSame(
        oldItem: InstructorsAdapter.ListItem,
        newItem: InstructorsAdapter.ListItem,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: InstructorsAdapter.ListItem,
        newItem: InstructorsAdapter.ListItem,
    ): Boolean {
        return oldItem.name == newItem.name
            && oldItem.age == newItem.age
            && oldItem.gender == newItem.gender
            && oldItem.sport == newItem.sport
            && oldItem.photo == newItem.photo
            && oldItem.experience == newItem.experience
            && oldItem.description == newItem.description
            && oldItem.rating == newItem.rating
            && oldItem.hourlyRate == newItem.hourlyRate
    }
}
