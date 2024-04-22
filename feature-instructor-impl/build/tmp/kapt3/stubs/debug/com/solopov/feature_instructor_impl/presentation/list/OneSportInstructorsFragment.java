package com.solopov.feature_instructor_impl.presentation.list;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\u0018\u0000 /2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001/B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0017H\u0016J$\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\u0010\u0010!\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020#H\u0002J\u001a\u0010$\u001a\u00020\u00172\u0006\u0010%\u001a\u00020\u001a2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\u0018\u0010&\u001a\u00020\u00172\u0006\u0010\'\u001a\u00020\u00132\u0006\u0010(\u001a\u00020)H\u0002J\u0010\u0010*\u001a\u00020\u00172\u0006\u0010+\u001a\u00020\u0002H\u0016J\u0016\u0010,\u001a\u00020\u00172\f\u0010-\u001a\b\u0012\u0004\u0012\u00020#0.H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001e\u0010\f\u001a\u00020\r8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011\u00a8\u00060"}, d2 = {"Lcom/solopov/feature_instructor_impl/presentation/list/OneSportInstructorsFragment;", "Lcom/solopov/common/base/BaseFragment;", "Lcom/solopov/feature_instructor_impl/presentation/list/InstructorsViewModel;", "()V", "binding", "Lcom/solopov/instructors/databinding/FragmentOneSportInstructorsBinding;", "mappers", "Lcom/solopov/feature_instructor_impl/data/mappers/InstructorMappers;", "getMappers", "()Lcom/solopov/feature_instructor_impl/data/mappers/InstructorMappers;", "setMappers", "(Lcom/solopov/feature_instructor_impl/data/mappers/InstructorMappers;)V", "router", "Lcom/solopov/feature_instructor_impl/InstructorsRouter;", "getRouter", "()Lcom/solopov/feature_instructor_impl/InstructorsRouter;", "setRouter", "(Lcom/solopov/feature_instructor_impl/InstructorsRouter;)V", "getStringCallback", "", "id", "", "initViews", "", "inject", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onItemClicked", "instructor", "Lcom/solopov/feature_instructor_impl/presentation/list/InstructorsAdapter$ListItem;", "onViewCreated", "view", "showImage", "url", "imageView", "Landroid/widget/ImageView;", "subscribe", "viewModel", "updateInstructors", "instructors", "", "Companion", "feature-instructor-impl_debug"})
public final class OneSportInstructorsFragment extends com.solopov.common.base.BaseFragment<com.solopov.feature_instructor_impl.presentation.list.InstructorsViewModel> {
    @javax.inject.Inject
    public com.solopov.feature_instructor_impl.InstructorsRouter router;
    @javax.inject.Inject
    public com.solopov.feature_instructor_impl.data.mappers.InstructorMappers mappers;
    private com.solopov.instructors.databinding.FragmentOneSportInstructorsBinding binding;
    @org.jetbrains.annotations.NotNull
    public static final com.solopov.feature_instructor_impl.presentation.list.OneSportInstructorsFragment.Companion Companion = null;
    
    public OneSportInstructorsFragment() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.solopov.feature_instructor_impl.InstructorsRouter getRouter() {
        return null;
    }
    
    public final void setRouter(@org.jetbrains.annotations.NotNull
    com.solopov.feature_instructor_impl.InstructorsRouter p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.solopov.feature_instructor_impl.data.mappers.InstructorMappers getMappers() {
        return null;
    }
    
    public final void setMappers(@org.jetbrains.annotations.NotNull
    com.solopov.feature_instructor_impl.data.mappers.InstructorMappers p0) {
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override
    public void onViewCreated(@org.jetbrains.annotations.NotNull
    android.view.View view, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override
    public void inject() {
    }
    
    @java.lang.Override
    public void subscribe(@org.jetbrains.annotations.NotNull
    com.solopov.feature_instructor_impl.presentation.list.InstructorsViewModel viewModel) {
    }
    
    private final void updateInstructors(java.util.List<com.solopov.feature_instructor_impl.presentation.list.InstructorsAdapter.ListItem> instructors) {
    }
    
    private final void showImage(java.lang.String url, android.widget.ImageView imageView) {
    }
    
    @java.lang.Override
    public void initViews() {
    }
    
    private final void onItemClicked(com.solopov.feature_instructor_impl.presentation.list.InstructorsAdapter.ListItem instructor) {
    }
    
    private final java.lang.String getStringCallback(int id) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/solopov/feature_instructor_impl/presentation/list/OneSportInstructorsFragment$Companion;", "", "()V", "newInstance", "Lcom/solopov/feature_instructor_impl/presentation/list/OneSportInstructorsFragment;", "sport", "", "feature-instructor-impl_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.solopov.feature_instructor_impl.presentation.list.OneSportInstructorsFragment newInstance(int sport) {
            return null;
        }
    }
}