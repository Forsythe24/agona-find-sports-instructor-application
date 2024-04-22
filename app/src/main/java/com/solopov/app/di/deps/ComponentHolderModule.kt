package com.solopov.app.di.deps

import com.solopov.app.App
import com.solopov.common.di.FeatureApiHolder
import com.solopov.common.di.FeatureContainer
import com.solopov.common.di.scope.ApplicationScope
import com.solopov.common.data.db.di.DbApi
import com.solopov.common.data.db.di.DbHolder
import com.solopov.common.data.firebase.di.FirebaseApi
import com.solopov.common.data.firebase.di.FirebaseHolder
import com.solopov.feature_authentication_api.di.AuthFeatureApi
import com.solopov.feature_authentication_impl.di.AuthFeatureHolder
import com.solopov.feature_instructor_api.di.InstructorFeatureApi
import com.solopov.feature_instructor_impl.di.InstructorFeatureComponent
import com.solopov.feature_instructor_impl.di.InstructorFeatureHolder
import com.solopov.feature_user_profile_api.di.UserProfileFeatureApi
import com.solopov.feature_user_profile_impl.di.UserProfileFeatureHolder
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
interface ComponentHolderModule {

    @ApplicationScope
    @Binds
    fun provideFeatureContainer(application: App): FeatureContainer

    @ApplicationScope
    @Binds
    @ClassKey(InstructorFeatureApi::class)
    @IntoMap
    fun provideInstructorFeatureHolder(instructorFeatureHolder: InstructorFeatureHolder): FeatureApiHolder

    @ApplicationScope
    @Binds
    @ClassKey(DbApi::class)
    @IntoMap
    fun provideDbFeature(dbHolder: DbHolder): FeatureApiHolder

    @ApplicationScope
    @Binds
    @ClassKey(AuthFeatureApi::class)
    @IntoMap
    fun provideAuthFeatureHolder(authFeatureHolder: AuthFeatureHolder): FeatureApiHolder

    @ApplicationScope
    @Binds
    @ClassKey(FirebaseApi::class)
    @IntoMap
    fun provideFirebaseFeatureHolder(firebaseHolder: FirebaseHolder): FeatureApiHolder

    @ApplicationScope
    @Binds
    @ClassKey(UserProfileFeatureApi::class)
    @IntoMap
    fun provideUserProfileFeatureHolder(userProfileFeatureHolder: UserProfileFeatureHolder): FeatureApiHolder

}
