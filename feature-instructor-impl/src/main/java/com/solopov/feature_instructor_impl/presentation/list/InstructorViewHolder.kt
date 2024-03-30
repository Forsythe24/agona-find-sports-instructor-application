package com.solopov.feature_instructor_impl.presentation.list

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.solopov.instructors.databinding.ItemInstructorBinding

class InstructorViewHolder (
    private val viewBinding: ItemInstructorBinding,
    private val showImage: (url: String, imageView: ImageView) -> Unit
) : ViewHolder(viewBinding.root) {

    fun bindItem(instructor: InstructorsAdapter.ListItem) {
        with (viewBinding) {
            with(instructor) {
                nameTv.text = name
                hourlyRateTv.text = hourlyRate.toString()
                ratingTv.text = rating.toString()
                descriptionTv.text = description
                instructorInfoTv.text = instructorInfo.format(experience, gender, age)

                showImage(photo, instructorIv)
            }
        }
    }

    companion object {
        private const val instructorInfo = "%s, %s, %d"
    }

}
