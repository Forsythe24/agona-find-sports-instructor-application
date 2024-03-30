package com.solopov.feature_instructor_impl.presentation.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.solopov.instructors.databinding.ItemInstructorBinding

class InstructorsAdapter(
    private val items: MutableList<ListItem>,
    private val showImage: (url: String, imageView: ImageView) -> Unit
) : RecyclerView.Adapter<InstructorViewHolder>() {

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
        var hourlyRate: Float,
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructorViewHolder {
        return InstructorViewHolder(ItemInstructorBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ), showImage)
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: InstructorViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

}
