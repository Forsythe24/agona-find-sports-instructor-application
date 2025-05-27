package com.solopov.feature_instructor_impl.presentation.recycler_view

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.solopov.instructors.R
import com.solopov.instructors.databinding.ItemInstructorBinding

class InstructorViewHolder(
    private val viewBinding: ItemInstructorBinding,
    private val showImage: (url: String, imageView: ImageView) -> Unit,
    private val onItemClicked: (InstructorsAdapter.ListItem) -> Unit,
) : ViewHolder(viewBinding.root) {

    fun bindItem(instructor: InstructorsAdapter.ListItem) {
        with(viewBinding) {
            with(instructor) {
                nameTv.text = name
                hourlyRateTv.text = root.context.getString(R.string.hourly_rate_template).format(hourlyRate)
                ratingTv.text = root.context.getString(R.string.rating_template).format(rating)
                numberOfRatingsTv.text =
                    root.context.getString(R.string.number_of_ratings_template).format(numberOfRatings)

                experienceTv.text = experience

                instructorInfoTv.text =
                    root.context.getString(R.string.instructor_info_template).format(gender, age)

                descriptionTv.text = description

                if (photo.isNotEmpty()) {
                    showImage(photo, instructorIv)
                }
            }

            root.setOnClickListener {
                onItemClicked(instructor)
            }
        }
    }
}
