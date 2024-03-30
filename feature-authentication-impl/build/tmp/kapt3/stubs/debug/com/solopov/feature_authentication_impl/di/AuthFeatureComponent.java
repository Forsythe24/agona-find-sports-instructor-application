package com.solopov.feature_authentication_impl.di;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001:\u0002\u0004\u0005J\b\u0010\u0002\u001a\u00020\u0003H&\u00a8\u0006\u0006"}, d2 = {"Lcom/solopov/feature_authentication_impl/di/AuthFeatureComponent;", "Lcom/solopov/feature_authentication_api/di/AuthFeatureApi;", "signUpComponentFactory", "Lcom/solopov/feature_authentication_impl/presentation/signup/di/SignUpComponent$Factory;", "AuthFeatureDependenciesComponent", "Builder", "feature-authentication-impl_debug"})
@dagger.Component(dependencies = {com.solopov.feature_authentication_impl.di.AuthFeatureDependencies.class}, modules = {com.solopov.feature_authentication_impl.di.AuthFeatureModule.class})
@com.solopov.common.di.scope.FeatureScope
public abstract interface AuthFeatureComponent extends com.solopov.feature_authentication_api.di.AuthFeatureApi {
    
    @org.jetbrains.annotations.NotNull
    public abstract com.solopov.feature_authentication_impl.presentation.signup.di.SignUpComponent.Factory signUpComponentFactory();
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0006H&\u00a8\u0006\u0007"}, d2 = {"Lcom/solopov/feature_authentication_impl/di/AuthFeatureComponent$Builder;", "", "build", "Lcom/solopov/feature_authentication_impl/di/AuthFeatureComponent;", "withDependencies", "deps", "Lcom/solopov/feature_authentication_impl/di/AuthFeatureDependencies;", "feature-authentication-impl_debug"})
    @dagger.Component.Builder
    public static abstract interface Builder {
        
        @org.jetbrains.annotations.NotNull
        public abstract com.solopov.feature_authentication_impl.di.AuthFeatureComponent.Builder withDependencies(@org.jetbrains.annotations.NotNull
        com.solopov.feature_authentication_impl.di.AuthFeatureDependencies deps);
        
        @org.jetbrains.annotations.NotNull
        public abstract com.solopov.feature_authentication_impl.di.AuthFeatureComponent build();
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001\u00a8\u0006\u0002"}, d2 = {"Lcom/solopov/feature_authentication_impl/di/AuthFeatureComponent$AuthFeatureDependenciesComponent;", "Lcom/solopov/feature_authentication_impl/di/AuthFeatureDependencies;", "feature-authentication-impl_debug"})
    @dagger.Component(dependencies = {com.solopov.common.di.CommonApi.class, com.solopov.common.data.firebase.di.FirebaseApi.class})
    public static abstract interface AuthFeatureDependenciesComponent extends com.solopov.feature_authentication_impl.di.AuthFeatureDependencies {
    }
}