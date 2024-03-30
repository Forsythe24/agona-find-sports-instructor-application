package com.solopov.feature_authentication_impl.presentation.signup;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\rH\u0016J$\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u0002H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b\u00a8\u0006\u0019"}, d2 = {"Lcom/solopov/feature_authentication_impl/presentation/signup/SignUpFragment;", "Lcom/solopov/common/base/BaseFragment;", "Lcom/solopov/feature_authentication_impl/presentation/SignUpViewModel;", "()V", "binding", "Lcom/solopov/feature_authentication_impl/databinding/FragmentSignUpBinding;", "repository", "Lcom/solopov/feature_authentication_api/domain/interfaces/AuthRepository;", "getRepository", "()Lcom/solopov/feature_authentication_api/domain/interfaces/AuthRepository;", "setRepository", "(Lcom/solopov/feature_authentication_api/domain/interfaces/AuthRepository;)V", "initViews", "", "inject", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "subscribe", "viewModel", "feature-authentication-impl_debug"})
public final class SignUpFragment extends com.solopov.common.base.BaseFragment<com.solopov.feature_authentication_impl.presentation.SignUpViewModel> {
    private com.solopov.feature_authentication_impl.databinding.FragmentSignUpBinding binding;
    @javax.inject.Inject
    public com.solopov.feature_authentication_api.domain.interfaces.AuthRepository repository;
    
    public SignUpFragment() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.solopov.feature_authentication_api.domain.interfaces.AuthRepository getRepository() {
        return null;
    }
    
    public final void setRepository(@org.jetbrains.annotations.NotNull
    com.solopov.feature_authentication_api.domain.interfaces.AuthRepository p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override
    public void initViews() {
    }
    
    @java.lang.Override
    public void inject() {
    }
    
    @java.lang.Override
    public void subscribe(@org.jetbrains.annotations.NotNull
    com.solopov.feature_authentication_impl.presentation.SignUpViewModel viewModel) {
    }
}