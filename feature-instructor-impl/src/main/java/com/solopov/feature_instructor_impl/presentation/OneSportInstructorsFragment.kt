package com.solopov.feature_instructor_impl.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.query
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.feature_instructor_api.di.InstructorFeatureApi
import com.solopov.feature_instructor_impl.InstructorsRouter
import com.solopov.feature_instructor_impl.di.InstructorFeatureComponent
import com.solopov.common.utils.ParamsKey
import com.solopov.feature_instructor_impl.data.mappers.InstructorMappers
import com.solopov.instructors.R
import com.solopov.instructors.databinding.FragmentOneSportInstructorsBinding
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class OneSportInstructorsFragment : BaseFragment<InstructorsViewModel>() {
    @Inject
    lateinit var router: InstructorsRouter

    @Inject
    lateinit var mappers: InstructorMappers

    private lateinit var binding: FragmentOneSportInstructorsBinding

    private var instructorsList: List<InstructorsAdapter.ListItem> = listOf()

    private lateinit var instructorsAdapter: InstructorsAdapter

    private lateinit var instructorsSearchView: SearchView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOneSportInstructorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getInstructorsBySportId(requireArguments().getInt(ParamsKey.SPORT_KEY))
    }

    override fun onStart() {
        super.onStart()
        instructorsSearchView = requireParentFragment().requireView().findViewById(R.id.instructors_sv)
    }

    override fun onResume() {
        super.onResume()
        instructorsSearchView.setQuery("", false)
        setOnInstructorsSearchListener()

    }

    override fun inject() {
        FeatureUtils.getFeature<InstructorFeatureComponent>(this, InstructorFeatureApi::class.java)
            .instructorsComponentFactory()
            .create(this)
            .inject(this)
    }

    override fun subscribe(viewModel: InstructorsViewModel) {
        with(viewModel) {
            currentInstructorsFlow.observe {
                if (it != null) {
                    instructorsList = it
                    updateInstructors(it)
                }
            }
            lifecycleScope.launch {
                errorsChannel.consumeEach { error ->
                    val errorMessage = error.message ?: getString(R.string.unknown_error_occurred)
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun updateInstructors(instructors: List<InstructorsAdapter.ListItem>) {
        with(binding) {
            if (instructorsRv.adapter == null) {
                instructorsAdapter = InstructorsAdapter(::showImage, ::onItemClicked, ::getStringCallback)
                instructorsRv.adapter = instructorsAdapter
            }
            (instructorsRv.adapter as InstructorsAdapter).submitList(instructors)
        }
    }

    private fun showImage(url: String, imageView: ImageView) {
        Glide.with(requireContext())
            .load(url)
            .into(imageView)

    }

    override fun initViews() {
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.instructorsRv.layoutManager = layoutManager
    }

    private fun setOnInstructorsSearchListener() {

        instructorsSearchView.setOnQueryTextListener(object:
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                if (text != null) {
                    filterInstructorsList(text)
                }

                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                if (text != null) {
                    filterInstructorsList(text)
                }

                return true
            }

        })
    }

    private fun onItemClicked(instructor: InstructorsAdapter.ListItem) {
        if (instructor.isFromApi) {
            router.openInstructor(mappers.mapInstructorListItemToUserCommon(instructor))
        } else {
            router.openInstructor(instructor.id)
        }
    }

    private fun getStringCallback(id: Int): String {
        return getString(id)
    }

    private fun filterInstructorsList(text: String) {
        val query = text.trim().lowercase()
        val filteredList = mutableListOf<InstructorsAdapter.ListItem>()
        instructorsList.forEach {instructor ->
            if (instructor.name.lowercase().startsWith(query)) {
                filteredList.add(instructor)
            }
        }

        if (filteredList.isEmpty()) {
            binding.noInstructorsFoundTv.visibility = VISIBLE
        } else {
            binding.noInstructorsFoundTv.visibility = GONE
        }
        updateInstructors(filteredList)
    }
    
    companion object {
        fun newInstance(sport: Int) = OneSportInstructorsFragment().apply {
            arguments = bundleOf(ParamsKey.SPORT_KEY to sport)
        }
    }
}
