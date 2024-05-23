package com.solopov.feature_instructor_impl.presentation

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.solopov.instructors.R
import com.solopov.instructors.databinding.ItemInstructorBinding

class InstructorViewHolder (
    private val viewBinding: ItemInstructorBinding,
    private val showImage: (url: String, imageView: ImageView) -> Unit,
    private val onItemClicked: (InstructorsAdapter.ListItem) -> Unit,
    private val getStringCallback: (id: Int) -> String
) : ViewHolder(viewBinding.root) {

    fun bindItem(instructor: InstructorsAdapter.ListItem) {
        with (viewBinding) {
            with(instructor) {
                nameTv.text = name
                hourlyRateTv.text = root.context.getString(R.string.hourly_rate_template).format(hourlyRate)
                ratingTv.text = getStringCallback(R.string.rating_template).format(rating)
                numberOfRatingsTv.text = getStringCallback(R.string.number_of_ratings_template).format(numberOfRatings)
                descriptionTv.text = description
                instructorInfoTv.text = instructorInfo.format(experience, gender, age)

                if (photo.isNotEmpty()) {
                    showImage(photo, instructorIv)
                }
            }

            root.setOnClickListener {
                onItemClicked(instructor)
            }
        }
    }

    companion object {
        private const val instructorInfo = "%s, %s, %d"
    }

}
