package com.solopov.feature_user_profile_impl.presentation.instruct

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.solopov.com.solopov.feature_user_profile_impl.R
import com.solopov.com.solopov.feature_user_profile_impl.databinding.FragmentInstructApplicationBinding
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.common.utils.ParamsKey
import com.solopov.feature_user_profile_api.di.UserProfileFeatureApi
import com.solopov.feature_user_profile_impl.UserProfileRouter
import com.solopov.feature_user_profile_impl.di.UserProfileFeatureComponent
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import javax.inject.Inject


class InstructApplicationFragment : BaseFragment<InstructApplicationViewModel>() {
    private lateinit var binding: FragmentInstructApplicationBinding

    @Inject
    lateinit var router: UserProfileRouter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInstructApplicationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initViews() {

        setOnSportsTypeSelectedListener()

        setSportsTypeDropDownMenuAdapter()

        with(binding) {

            backBtn.setOnClickListener {
                router.goBack()
            }

            hourlyRateSb.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                    hourlyRateBroadcastTv.text =
                        resources.getString(R.string.hourly_rate_broadcast_template)
                            .format(progress)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(p0: SeekBar?) {}

            })

            val userProfile = extractUserArgument()

            userProfile?.let {
                if (it.isInstructor) {

                    setViewsToEditingMode() //since the user is already an instructor that means they are just editing the information provided before

                    descriptionEt.setText(it.description)
                    experienceEt.setText(it.experience)
                    hourlyRateSb.progress = it.hourlyRate!!.toInt()

                    sportsTypeAutocompleteTv.setText(it.sport)

                    val drawableId = getSportsTypeDrawableId(resources.getStringArray(R.array.current_sports_types).indexOf(it.sport))
                    specialtyTextInput.setStartIconDrawable(drawableId)



                    setSportsTypeDropDownMenuAdapter()
                }
            }

            applyBtn.setOnClickListener {

                userProfile?.let {
                    it.description = descriptionEt.text.toString()
                    it.experience = experienceEt.text.toString()
                    it.sport = sportsTypeAutocompleteTv.text.toString()
                    it.hourlyRate = hourlyRateSb.progress.toFloat()
                    it.isInstructor = true

                    viewModel.updateUser(it, ::onUserUpdatedCallback)
                }
            }

        }
    }

    private fun onUserUpdatedCallback() {
        router.goBack()
        Snackbar.make(binding.root, getString(R.string.your_instructor_s_bio_has_been_successfully_saved), Snackbar.LENGTH_SHORT).show()
    }

    private fun getSportsTypeDrawableId(index: Int): Int =
        when (index) {
            0 -> R.drawable.baseline_sports_soccer_24
            1 -> R.drawable.baseline_sports_basketball_24
            2 -> R.drawable.baseline_sports_hockey_24
            3 -> R.drawable.baseline_sports_volleyball_24
            else -> R.drawable.baseline_sports_handball_24
        }

    private fun setViewsToEditingMode() {
        with(binding) {
            applyBtn.setText(getString(R.string.save))
            applicationTv.text = getString(R.string.instructors_bio_label)
            joinInstructorsTv.text = getString(R.string.edit_your_instructors_bio_here)
        }
    }

    private fun setSportsTypeDropDownMenuAdapter() {
        val sportsTypes = resources.getStringArray(R.array.current_sports_types)
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.sports_type_dropdown_item, sportsTypes)

        binding.sportsTypeAutocompleteTv.setAdapter(arrayAdapter)
    }

    private fun extractUserArgument(): UserProfile? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getSerializable(ParamsKey.USER_KEY, UserProfile::class.java)
        } else {
            requireArguments().getSerializable(ParamsKey.USER_KEY) as UserProfile
        }
    }

    private fun setOnSportsTypeSelectedListener() {
        with(binding) {
            sportsTypeAutocompleteTv.onItemClickListener =
                OnItemClickListener { _, _, index, _ ->
                    val drawableId = getSportsTypeDrawableId(index)
                    specialtyTextInput.setStartIconDrawable(drawableId)
                }
        }
    }


    override fun inject() {
        FeatureUtils.getFeature<UserProfileFeatureComponent>(
            this,
            UserProfileFeatureApi::class.java
        )
            .instructApplicationComponentFactory()
            .create(this)
            .inject(this)
    }

    override fun subscribe(viewModel: InstructApplicationViewModel) {

        viewModel.progressBarFlow.observe { isLoading ->
            binding.applyBtn.setLoading(isLoading)
        }

        lifecycleScope.launch {
            viewModel.errorsChannel.consumeEach { error ->
                val errorMessage = error.message ?: getString(R.string.unknown_error)

                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
}
