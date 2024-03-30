package com.solopov.feature_instructor_impl.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.feature_instructor_api.di.InstructorFeatureApi
import com.solopov.feature_instructor_impl.InstructorsRouter
import com.solopov.feature_instructor_impl.di.InstructorFeatureComponent
import com.solopov.feature_instructor_impl.utils.ParamsKey
import com.solopov.instructors.databinding.FragmentOneSportInstructorsBinding
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class OneSportInstructorsFragment : BaseFragment<InstructorsViewModel>() {
    @Inject
    lateinit var router: InstructorsRouter

    private lateinit var binding: FragmentOneSportInstructorsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentOneSportInstructorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getInstructorsBySportId(requireArguments().getInt(ParamsKey.SPORT_KEY))
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
                    updateInstructors(it)
                }
            }

            lifecycleScope.launch {
                errorsChannel.consumeEach { error ->
                    val errorMessage = error.message ?: "Unknown error occurred"
                    println(errorMessage)
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun updateInstructors(instructors: List<InstructorsAdapter.ListItem>) {
        with(binding) {
            if (instructorsRv.adapter == null) {

                instructorsRv.adapter = InstructorsAdapter(instructors as MutableList<InstructorsAdapter.ListItem>, ::showImage)
            }
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
    
    companion object {
        fun newInstance(sport: Int) = OneSportInstructorsFragment().apply {
            arguments = bundleOf(ParamsKey.SPORT_KEY to sport)
        }
    }
}
