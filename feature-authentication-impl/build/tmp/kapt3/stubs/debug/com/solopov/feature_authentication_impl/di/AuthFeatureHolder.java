package com.solopov.feature_authentication_impl.di;

@com.solopov.common.di.scope.ApplicationScope
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/solopov/feature_authentication_impl/di/AuthFeatureHolder;", "Lcom/solopov/common/di/FeatureApiHolder;", "featureContainer", "Lcom/solopov/common/di/FeatureContainer;", "authRouter", "Lcom/solopov/feature_authentication_impl/AuthRouter;", "(Lcom/solopov/common/di/FeatureContainer;Lcom/solopov/feature_authentication_impl/AuthRouter;)V", "initializeDependencies", "", "feature-authentication-impl_debug"})
public final class AuthFeatureHolder extends com.solopov.common.di.FeatureApiHolder {
    @org.jetbrains.annotations.NotNull
    private final com.solopov.feature_authentication_impl.AuthRouter authRouter = null;
    
    @javax.inject.Inject
    public AuthFeatureHolder(@org.jetbrains.annotations.NotNull
    com.solopov.common.di.FeatureContainer featureContainer, @org.jetbrains.annotations.NotNull
    com.solopov.feature_authentication_impl.AuthRouter authRouter) {
        super(null);
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    protected java.lang.Object initializeDependencies() {
        return null;
    }
}