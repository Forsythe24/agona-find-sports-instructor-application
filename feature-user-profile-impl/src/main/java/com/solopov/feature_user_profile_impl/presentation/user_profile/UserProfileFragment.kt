package com.solopov.feature_user_profile_impl.presentation.user_profile

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.solopov.com.solopov.feature_user_profile_impl.R
import com.solopov.feature_user_profile_impl.UserProfileRouter
import com.solopov.feature_user_profile_impl.di.UserProfileFeatureComponent
import com.solopov.com.solopov.feature_user_profile_impl.databinding.FragmentUserProfileBinding
import com.solopov.common.base.BaseFragment
import com.solopov.common.di.FeatureUtils
import com.solopov.common.model.UserCommon
import com.solopov.common.utils.ParamsKey
import com.solopov.feature_user_profile_api.di.UserProfileFeatureApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserProfileFragment: BaseFragment<UserProfileViewModel>() {

    private lateinit var binding: FragmentUserProfileBinding

    @Inject
    lateinit var router: UserProfileRouter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUser()
    }

    private fun setUser() {
//        val userId = requireArguments().getString(ParamsKey.USER_ID)
        
        val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getSerializable(ParamsKey.USER, UserCommon::class.java)
            } else {
                arguments?.getSerializable(ParamsKey.USER) as UserCommon?
            }

        if (user != null) {
            binding.editBtn.visibility = GONE
            viewModel.setUser(user)

        } else {
            viewModel.getCurrentUser()
        }
    }

    override fun initViews() {

    }


    override fun inject() {
        FeatureUtils.getFeature<UserProfileFeatureComponent>(this, UserProfileFeatureApi::class.java)
            .userProfileComponentFactory()
            .create(this)
            .inject(this)
    }


    override fun subscribe(viewModel: UserProfileViewModel) {


        with(viewModel) {
            with(binding) {
                userProfileFlow.observe {
                    it?.let { user ->

                        if (!user.isInstructor) {
                            hideInstructorSpecificViews()
                        } else {
                            hideRegularUserSpecificViews()
                        }

                        with(user) {
                            val fullGender = if (gender == "M") getString(R.string.male) else getString(R.string.female)
                            userInfoTv.text = resources.getString(R.string.instructor_info_template).format(fullGender, age)
                            nameTv.text = name
                            if (photo.isNullOrEmpty()) {
                                userIv.setImageResource(R.drawable.no_profile_photo)
                            } else {
                                showImage(photo!!, userIv)
                            }
                            rating?.let { rating ->
                                ratingTv.text = resources.getString(R.string.rating_template).format(rating)
                            }
                            hourlyRate?.let { hourlyRate ->
                                hourlyRateTv.text = resources.getString(R.string.hourly_rate_template).format(hourlyRate)
                            }
                        }
                    }

                }

                lifecycleScope.launch {
                    errorsChannel.consumeEach { error ->
                        val errorMessage = error.message ?: getString(R.string.unknown_error)

                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }

    private fun hideInstructorSpecificViews() {
        with(binding) {
            hourlyRateTv.visibility = GONE
            experienceTv.visibility = GONE
            descriptionTv.visibility = GONE
            numberOfRatingsTv.visibility = GONE
            ratingTv.visibility = GONE

            sendMessageBtn.visibility = GONE
        }
    }

    private fun hideRegularUserSpecificViews() {
        with(binding) {
            instructBtn.visibility = GONE
        }
    }

    private fun showImage(url: String, imageView: ImageView) {
        Glide.with(requireContext())
            .load(url)
            .into(imageView)

    }
}
