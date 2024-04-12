package com.solopov.feature_authentication_impl.di;

@com.solopov.common.di.scope.FeatureScope
@dagger.Component(dependencies = {com.solopov.feature_authentication_impl.di.AuthFeatureDependencies.class}, modules = {com.solopov.feature_authentication_impl.di.AuthFeatureModule.class})
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001:\u0002\u0006\u0007J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\b"}, d2 = {"Lcom/solopov/feature_authentication_impl/di/AuthFeatureComponent;", "Lcom/solopov/feature_authentication_api/di/AuthFeatureApi;", "logInComponentFactory", "Lcom/solopov/feature_authentication_impl/presentation/login/di/LogInComponent$Factory;", "signUpComponentFactory", "Lcom/solopov/feature_authentication_impl/presentation/signup/di/SignUpComponent$Factory;", "AuthFeatureDependenciesComponent", "Builder", "feature-authentication-impl_debug"})
public abstract interface AuthFeatureComponent extends com.solopov.feature_authentication_api.di.AuthFeatureApi {
    
    @org.jetbrains.annotations.NotNull
    public abstract com.solopov.feature_authentication_impl.presentation.signup.di.SignUpComponent.Factory signUpComponentFactory();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.solopov.feature_authentication_impl.presentation.login.di.LogInComponent.Factory logInComponentFactory();
    
    @dagger.Component(dependencies = {com.solopov.common.di.CommonApi.class, com.solopov.common.data.firebase.di.FirebaseApi.class})
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001\u00a8\u0006\u0002"}, d2 = {"Lcom/solopov/feature_authentication_impl/di/AuthFeatureComponent$AuthFeatureDependenciesComponent;", "Lcom/solopov/feature_authentication_impl/di/AuthFeatureDependencies;", "feature-authentication-impl_debug"})
    public static abstract interface AuthFeatureDependenciesComponent extends com.solopov.feature_authentication_impl.di.AuthFeatureDependencies {
    }
    
    @dagger.Component.Builder
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0007\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\tH&\u00a8\u0006\n"}, d2 = {"Lcom/solopov/feature_authentication_impl/di/AuthFeatureComponent$Builder;", "", "build", "Lcom/solopov/feature_authentication_impl/di/AuthFeatureComponent;", "router", "authRouter", "Lcom/solopov/feature_authentication_impl/AuthRouter;", "withDependencies", "deps", "Lcom/solopov/feature_authentication_impl/di/AuthFeatureDependencies;", "feature-authentication-impl_debug"})
    public static abstract interface Builder {
        
        @dagger.BindsInstance
        @org.jetbrains.annotations.NotNull
        public abstract com.solopov.feature_authentication_impl.di.AuthFeatureComponent.Builder router(@org.jetbrains.annotations.NotNull
        com.solopov.feature_authentication_impl.AuthRouter authRouter);
        
        @org.jetbrains.annotations.NotNull
        public abstract com.solopov.feature_authentication_impl.di.AuthFeatureComponent.Builder withDependencies(@org.jetbrains.annotations.NotNull
        com.solopov.feature_authentication_impl.di.AuthFeatureDependencies deps);
        
        @org.jetbrains.annotations.NotNull
        public abstract com.solopov.feature_authentication_impl.di.AuthFeatureComponent build();
    }
}