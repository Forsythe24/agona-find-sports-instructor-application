package com.solopov.feature_instructor_api.di

import com.solopov.feature_instructor_api.domain.InstructorRepository


interface InstructorFeatureApi {
    fun provideInstructorRepository(): InstructorRepository
}
