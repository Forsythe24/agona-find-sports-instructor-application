package com.solopov.feature_instructor_impl.di;

@com.solopov.common.di.scope.ApplicationScope
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/solopov/feature_instructor_impl/di/InstructorFeatureHolder;", "Lcom/solopov/common/di/FeatureApiHolder;", "featureContainer", "Lcom/solopov/common/di/FeatureContainer;", "instructorsRouter", "Lcom/solopov/feature_instructor_impl/InstructorsRouter;", "(Lcom/solopov/common/di/FeatureContainer;Lcom/solopov/feature_instructor_impl/InstructorsRouter;)V", "initializeDependencies", "", "feature-instructor-impl_debug"})
public final class InstructorFeatureHolder extends com.solopov.common.di.FeatureApiHolder {
    @org.jetbrains.annotations.NotNull
    private final com.solopov.feature_instructor_impl.InstructorsRouter instructorsRouter = null;
    
    @javax.inject.Inject
    public InstructorFeatureHolder(@org.jetbrains.annotations.NotNull
    com.solopov.common.di.FeatureContainer featureContainer, @org.jetbrains.annotations.NotNull
    com.solopov.feature_instructor_impl.InstructorsRouter instructorsRouter) {
        super(null);
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    protected java.lang.Object initializeDependencies() {
        return null;
    }
}