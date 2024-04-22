package com.solopov.feature_authentication_impl.di;

@dagger.Module
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\bH\u0007\u00a8\u0006\t"}, d2 = {"Lcom/solopov/feature_authentication_impl/di/AuthFeatureModule;", "", "()V", "provideAuthInteractor", "Lcom/solopov/feature_authentication_api/domain/interfaces/AuthInteractor;", "authRepository", "Lcom/solopov/feature_authentication_api/domain/interfaces/AuthRepository;", "provideAuthRepository", "Lcom/solopov/feature_authentication_impl/data/repository/AuthRepositoryImpl;", "feature-authentication-impl_debug"})
public final class AuthFeatureModule {
    
    public AuthFeatureModule() {
        super();
    }
    
    @dagger.Provides
    @com.solopov.common.di.scope.FeatureScope
    @org.jetbrains.annotations.NotNull
    public final com.solopov.feature_authentication_api.domain.interfaces.AuthRepository provideAuthRepository(@org.jetbrains.annotations.NotNull
    com.solopov.feature_authentication_impl.data.repository.AuthRepositoryImpl authRepository) {
        return null;
    }
    
    @dagger.Provides
    @com.solopov.common.di.scope.FeatureScope
    @org.jetbrains.annotations.NotNull
    public final com.solopov.feature_authentication_api.domain.interfaces.AuthInteractor provideAuthInteractor(@org.jetbrains.annotations.NotNull
    com.solopov.feature_authentication_api.domain.interfaces.AuthRepository authRepository) {
        return null;
    }
}