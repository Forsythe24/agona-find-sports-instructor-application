package com.solopov.feature_instructor_impl.presentation.details;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bR\u0016\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/solopov/feature_instructor_impl/presentation/details/InstructorViewModel;", "Lcom/solopov/common/base/BaseViewModel;", "interactor", "Lcom/solopov/feature_instructor_api/domain/interfaces/InstructorInteractor;", "isntructorId", "", "resourceManager", "Lcom/solopov/common/core/resources/ResourceManager;", "(Lcom/solopov/feature_instructor_api/domain/interfaces/InstructorInteractor;Ljava/lang/String;Lcom/solopov/common/core/resources/ResourceManager;)V", "_instructorFlow", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/solopov/feature_instructor_impl/presentation/details/model/InstructorDetailsModel;", "feature-instructor-impl_debug"})
public final class InstructorViewModel extends com.solopov.common.base.BaseViewModel {
    private final com.solopov.feature_instructor_api.domain.interfaces.InstructorInteractor interactor = null;
    private final java.lang.String isntructorId = null;
    private final com.solopov.common.core.resources.ResourceManager resourceManager = null;
    private final kotlinx.coroutines.flow.MutableStateFlow<com.solopov.feature_instructor_impl.presentation.details.model.InstructorDetailsModel> _instructorFlow = null;
    
    @javax.inject.Inject
    public InstructorViewModel(@org.jetbrains.annotations.NotNull
    com.solopov.feature_instructor_api.domain.interfaces.InstructorInteractor interactor, @org.jetbrains.annotations.NotNull
    java.lang.String isntructorId, @org.jetbrains.annotations.NotNull
    com.solopov.common.core.resources.ResourceManager resourceManager) {
        super();
    }
}