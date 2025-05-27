package com.solopov.feature_instructor_impl.presentation.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import com.solopov.instructors.databinding.ItemInstructorBinding

class InstructorsAdapter(
    private val showImage: (url: String, imageView: ImageView) -> Unit,
    private val onItemClicked: (ListItem) -> Unit,
) : ListAdapter<InstructorsAdapter.ListItem, InstructorViewHolder>(InstructorDiffUtilItemCallback()) {

    data class ListItem(
        val id: String,
        var name: String,
        var age: Int,
        var gender: String,
        var sport: String,
        var photo: String,
        var experience: String,
        var description: String,
        var rating: Float,
        var numberOfRatings: Int,
        var hourlyRate: Float,
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructorViewHolder {
        return InstructorViewHolder(
            ItemInstructorBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), showImage, onItemClicked
        )
    }


    override fun onBindViewHolder(holder: InstructorViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

}
