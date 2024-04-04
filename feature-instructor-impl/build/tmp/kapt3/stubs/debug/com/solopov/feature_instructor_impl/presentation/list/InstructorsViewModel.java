package com.solopov.feature_instructor_impl.presentation.list;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017J\u0010\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u001c\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001a0\tH\u0002J\b\u0010\u001d\u001a\u00020\u0015H\u0014R\u001c\u0010\u0007\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001f\u0010\u000b\u001a\u0010\u0012\f\u0012\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t0\f8F\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/solopov/feature_instructor_impl/presentation/list/InstructorsViewModel;", "Lcom/solopov/common/base/BaseViewModel;", "interactor", "Lcom/solopov/feature_instructor_api/domain/interfaces/InstructorInteractor;", "resourceManager", "Lcom/solopov/common/core/resources/ResourceManager;", "(Lcom/solopov/feature_instructor_api/domain/interfaces/InstructorInteractor;Lcom/solopov/common/core/resources/ResourceManager;)V", "_currentInstructorsFlow", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/solopov/feature_instructor_impl/presentation/list/InstructorsAdapter$ListItem;", "currentInstructorsFlow", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentInstructorsFlow", "()Lkotlinx/coroutines/flow/StateFlow;", "errorsChannel", "Lkotlinx/coroutines/channels/Channel;", "", "getErrorsChannel", "()Lkotlinx/coroutines/channels/Channel;", "getInstructorsBySportId", "", "sportId", "", "mapInstructorToListItem", "instructor", "Lcom/solopov/feature_instructor_api/domain/model/Instructor;", "mapInstructorsToListItems", "instructors", "onCleared", "feature-instructor-impl_debug"})
public final class InstructorsViewModel extends com.solopov.common.base.BaseViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.solopov.feature_instructor_api.domain.interfaces.InstructorInteractor interactor = null;
    @org.jetbrains.annotations.NotNull
    private final com.solopov.common.core.resources.ResourceManager resourceManager = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.solopov.feature_instructor_impl.presentation.list.InstructorsAdapter.ListItem>> _currentInstructorsFlow = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.channels.Channel<java.lang.Throwable> errorsChannel = null;
    
    @javax.inject.Inject
    public InstructorsViewModel(@org.jetbrains.annotations.NotNull
    com.solopov.feature_instructor_api.domain.interfaces.InstructorInteractor interactor, @org.jetbrains.annotations.NotNull
    com.solopov.common.core.resources.ResourceManager resourceManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.solopov.feature_instructor_impl.presentation.list.InstructorsAdapter.ListItem>> getCurrentInstructorsFlow() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.channels.Channel<java.lang.Throwable> getErrorsChannel() {
        return null;
    }
    
    public final void getInstructorsBySportId(int sportId) {
    }
    
    private final com.solopov.feature_instructor_impl.presentation.list.InstructorsAdapter.ListItem mapInstructorToListItem(com.solopov.feature_instructor_api.domain.model.Instructor instructor) {
        return null;
    }
    
    private final java.util.List<com.solopov.feature_instructor_impl.presentation.list.InstructorsAdapter.ListItem> mapInstructorsToListItems(java.util.List<com.solopov.feature_instructor_api.domain.model.Instructor> instructors) {
        return null;
    }
    
    @java.lang.Override
    protected void onCleared() {
    }
}