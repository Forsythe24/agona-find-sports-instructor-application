package com.solopov.feature_user_profile_impl.presentation.instruct

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.solopov.com.solopov.feature_user_profile_impl.R
import com.solopov.com.solopov.feature_user_profile_impl.databinding.FragmentInstructApplicationBinding
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.common.model.UserCommon
import com.solopov.common.utils.ParamsKey
import com.solopov.feature_user_profile_api.di.UserProfileFeatureApi
import com.solopov.feature_user_profile_api.domain.model.User
import com.solopov.feature_user_profile_impl.di.UserProfileFeatureComponent
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.UserProfile
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch


class InstructApplicationFragment: BaseFragment<InstructApplicationViewModel>(){
    private lateinit var binding: FragmentInstructApplicationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentInstructApplicationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initViews() {

        setOnSportsTypeSelectedListener()

        setSportsTypeDropDownMenuAdapter()

        with(binding) {

            hourlyRateSb.setOnSeekBarChangeListener(object: OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                    hourlyRateBroadcastTv.text = resources.getString(R.string.hourly_rate_broadcast_template).format(progress)
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

                    viewModel.updateUser(it)
                }
            }

        }
    }

    private fun setViewsToEditingMode() {
        with(binding) {
            applyBtn.text = getString(R.string.save)
            applicationTv.text = getString(R.string.instructors_bio_label)
            joinInstructorsTv.text = getString(R.string.edit_your_instructors_bio_here)
        }
    }

    private fun setSportsTypeDropDownMenuAdapter() {
        val sportsTypes = resources.getStringArray(R.array.sports_types)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.sports_type_dropdown_item, sportsTypes)

        binding.sportsTypeAutocompleteTv.setAdapter(arrayAdapter)
    }

    private fun extractUserArgument(): UserProfile? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requireArguments().getSerializable(ParamsKey.USER, UserProfile::class.java)
        } else {
            requireArguments().getSerializable(ParamsKey.USER) as UserProfile
        }
    }

    private fun setOnSportsTypeSelectedListener() {
        with(binding) {
            sportsTypeAutocompleteTv.onItemClickListener =
                OnItemClickListener { _, _, id, _ ->
                    val resourceId = when (id) {
                        0 -> R.drawable.baseline_sports_soccer_24
                        1 -> R.drawable.baseline_sports_basketball_24
                        2 -> R.drawable.baseline_sports_hockey_24
                        3 -> R.drawable.baseline_sports_volleyball_24
                        else -> R.drawable.baseline_sports_handball_24
                    }
                    specialtyTextInput.setStartIconDrawable(resourceId)
                }
        }
    }


    override fun inject() {
        FeatureUtils.getFeature<UserProfileFeatureComponent>(this, UserProfileFeatureApi::class.java)
            .instructApplicationComponentFactory()
            .create(this)
            .inject(this)
    }

    override fun subscribe(viewModel: InstructApplicationViewModel) {
        lifecycleScope.launch {
            viewModel.errorsChannel.consumeEach { error ->
                val errorMessage = error.message ?: getString(R.string.unknown_error)

                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
}
