package com.solopov.feature_instructor_impl.presentation.list;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B|\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00126\u0010\u0004\u001a2\u0012\u0013\u0012\u00110\u0006\u00a2\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\f0\u0005\u0012\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\f0\u000e\u0012!\u0010\u0010\u001a\u001d\u0012\u0013\u0012\u00110\u0011\u00a2\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u00060\u000e\u00a2\u0006\u0002\u0010\u0013J\u000e\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u000fR)\u0010\u0010\u001a\u001d\u0012\u0013\u0012\u00110\u0011\u00a2\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u00060\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R>\u0010\u0004\u001a2\u0012\u0013\u0012\u00110\u0006\u00a2\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\t\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b\u0007\u0012\b\b\b\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\f0\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/solopov/feature_instructor_impl/presentation/list/InstructorViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "viewBinding", "Lcom/solopov/instructors/databinding/ItemInstructorBinding;", "showImage", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "url", "Landroid/widget/ImageView;", "imageView", "", "onItemClicked", "Lkotlin/Function1;", "Lcom/solopov/feature_instructor_impl/presentation/list/InstructorsAdapter$ListItem;", "getStringCallback", "", "id", "(Lcom/solopov/instructors/databinding/ItemInstructorBinding;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "bindItem", "instructor", "Companion", "feature-instructor-impl_debug"})
public final class InstructorViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
    @org.jetbrains.annotations.NotNull
    private final com.solopov.instructors.databinding.ItemInstructorBinding viewBinding = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function2<java.lang.String, android.widget.ImageView, kotlin.Unit> showImage = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function1<com.solopov.feature_instructor_impl.presentation.list.InstructorsAdapter.ListItem, kotlin.Unit> onItemClicked = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function1<java.lang.Integer, java.lang.String> getStringCallback = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String instructorInfo = "%s, %s, %d";
    @org.jetbrains.annotations.NotNull
    public static final com.solopov.feature_instructor_impl.presentation.list.InstructorViewHolder.Companion Companion = null;
    
    public InstructorViewHolder(@org.jetbrains.annotations.NotNull
    com.solopov.instructors.databinding.ItemInstructorBinding viewBinding, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super android.widget.ImageView, kotlin.Unit> showImage, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.solopov.feature_instructor_impl.presentation.list.InstructorsAdapter.ListItem, kotlin.Unit> onItemClicked, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super java.lang.Integer, java.lang.String> getStringCallback) {
        super(null);
    }
    
    public final void bindItem(@org.jetbrains.annotations.NotNull
    com.solopov.feature_instructor_impl.presentation.list.InstructorsAdapter.ListItem instructor) {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/solopov/feature_instructor_impl/presentation/list/InstructorViewHolder$Companion;", "", "()V", "instructorInfo", "", "feature-instructor-impl_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}