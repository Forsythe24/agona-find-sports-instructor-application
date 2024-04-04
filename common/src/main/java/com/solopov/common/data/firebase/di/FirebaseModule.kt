package com.solopov.common.data.firebase.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.solopov.common.data.firebase.dao.UserFirebaseDao
import com.solopov.common.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class FirebaseModule {

    @ApplicationScope
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @ApplicationScope
    @Provides
    fun provideFirebaseDatabaseReference(): DatabaseReference = FirebaseDatabase.getInstance().reference

}
