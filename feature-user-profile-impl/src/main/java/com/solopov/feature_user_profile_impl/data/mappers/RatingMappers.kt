package com.solopov.feature_user_profile_impl.data.mappers

import com.solopov.common.data.db.model.RatingLocal
import com.solopov.feature_user_profile_api.domain.model.Rating
import com.solopov.feature_user_profile_impl.presentation.user_profile.model.RatingUi
import javax.inject.Inject

class RatingMappers @Inject constructor() {

    fun mapRatingLocalToRating(ratingLocal: RatingLocal): Rating {
        return with(ratingLocal) {
            Rating(
                id,
                instructorId,
                userId,
                rating
            )
        }
    }

    fun mapRatingUiToRating(ratingUi: RatingUi): Rating {
        return with(ratingUi) {
            Rating(
                id,
                instructorId,
                userId,
                rating
            )
        }
    }

    fun mapRatingToRatingUi(rating: Rating): RatingUi {
        return with(rating) {
            RatingUi(
                id,
                instructorId,
                userId,
                this.rating
            )
        }
    }

    fun mapRatingToRatingLocal(rating: Rating): RatingLocal {
        return with(rating) {
            RatingLocal(
                id,
                instructorId,
                userId,
                this.rating
            )
        }
    }

//    fun mapRatingToRatingLocal(rating: Rating): RatingLocal {
//        return with(rating) {
//            RatingLocal(
//                id,
//                instructorId,
//                userId,
//                this.rating
//            )
//        }
//    }
}
