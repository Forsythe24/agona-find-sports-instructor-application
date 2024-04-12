package com.solopov.feature_authentication_impl.presentation.signup;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0013H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0002J$\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u0013H\u0002J\u0010\u0010 \u001a\u00020\u00132\u0006\u0010!\u001a\u00020\u0002H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001e\u0010\f\u001a\u00020\r8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011\u00a8\u0006\""}, d2 = {"Lcom/solopov/feature_authentication_impl/presentation/signup/SignUpFragment;", "Lcom/solopov/common/base/BaseFragment;", "Lcom/solopov/feature_authentication_impl/presentation/signup/SignUpViewModel;", "()V", "binding", "Lcom/solopov/feature_authentication_impl/databinding/FragmentSignUpBinding;", "router", "Lcom/solopov/feature_authentication_impl/AuthRouter;", "getRouter", "()Lcom/solopov/feature_authentication_impl/AuthRouter;", "setRouter", "(Lcom/solopov/feature_authentication_impl/AuthRouter;)V", "validator", "Lcom/solopov/feature_authentication_impl/utils/UserDataValidator;", "getValidator", "()Lcom/solopov/feature_authentication_impl/utils/UserDataValidator;", "setValidator", "(Lcom/solopov/feature_authentication_impl/utils/UserDataValidator;)V", "initViews", "", "inject", "isValidForm", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "showInvalidFormAlert", "subscribe", "viewModel", "feature-authentication-impl_debug"})
public final class SignUpFragment extends com.solopov.common.base.BaseFragment<com.solopov.feature_authentication_impl.presentation.signup.SignUpViewModel> {
    private com.solopov.feature_authentication_impl.databinding.FragmentSignUpBinding binding;
    @javax.inject.Inject
    public com.solopov.feature_authentication_impl.AuthRouter router;
    @javax.inject.Inject
    public com.solopov.feature_authentication_impl.utils.UserDataValidator validator;
    
    public SignUpFragment() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.solopov.feature_authentication_impl.AuthRouter getRouter() {
        return null;
    }
    
    public final void setRouter(@org.jetbrains.annotations.NotNull
    com.solopov.feature_authentication_impl.AuthRouter p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.solopov.feature_authentication_impl.utils.UserDataValidator getValidator() {
        return null;
    }
    
    public final void setValidator(@org.jetbrains.annotations.NotNull
    com.solopov.feature_authentication_impl.utils.UserDataValidator p0) {
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
    public void initViews() {
    }
    
    private final void showInvalidFormAlert() {
    }
    
    private final boolean isValidForm() {
        return false;
    }
    
    @java.lang.Override
    public void inject() {
    }
    
    @java.lang.Override
    public void subscribe(@org.jetbrains.annotations.NotNull
    com.solopov.feature_authentication_impl.presentation.signup.SignUpViewModel viewModel) {
    }
}