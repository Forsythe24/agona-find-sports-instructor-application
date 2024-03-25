package com.solopov.feature_instructor_impl.presentation.list

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.solopov.instructors.databinding.ItemInstructorBinding

class InstructorViewHolder (private val viewBinding: ItemInstructorBinding) : ViewHolder(viewBinding.root) {

    fun bindItem(instructor: InstructorsAdapter.ListItem) {
        with (viewBinding) {
            with(instructor) {
                nameTv.text = name
                hourlyRateTv.text = hourlyRate.toString()
                ratingTv.text = rating.toString()
                descriptionTv.text = description
                instructorInfoTv.text = instructorInfo.format(experience, gender, age)
            }
        }
    }

    companion object {
        private const val instructorInfo = "%s, %s, %d"
    }

}
