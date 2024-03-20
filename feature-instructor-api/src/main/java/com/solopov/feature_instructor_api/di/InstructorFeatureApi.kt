package com.solopov.feature_instructor_api.di

import com.solopov.feature_instructor_api.domain.interfaces.InstructorInteractor
import com.solopov.feature_instructor_api.domain.interfaces.InstructorRepository


interface InstructorFeatureApi {

    fun provideInstructorRepository(): InstructorRepository

    fun provideInstructorInteractor(): InstructorInteractor
}
