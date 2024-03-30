package com.solopov.feature_authentication_impl.data.repository;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/solopov/feature_authentication_impl/data/repository/AuthRepositoryImpl;", "Lcom/solopov/feature_authentication_api/domain/interfaces/AuthRepository;", "userFirebaseDao", "Lcom/solopov/common/data/firebase/dao/UserFirebaseDao;", "userMappers", "Lcom/solopov/feature_authentication_impl/data/mappers/UserMappers;", "(Lcom/solopov/common/data/firebase/dao/UserFirebaseDao;Lcom/solopov/feature_authentication_impl/data/mappers/UserMappers;)V", "createUser", "", "email", "", "password", "feature-authentication-impl_debug"})
public final class AuthRepositoryImpl implements com.solopov.feature_authentication_api.domain.interfaces.AuthRepository {
    private final com.solopov.common.data.firebase.dao.UserFirebaseDao userFirebaseDao = null;
    private final com.solopov.feature_authentication_impl.data.mappers.UserMappers userMappers = null;
    
    @javax.inject.Inject
    public AuthRepositoryImpl(@org.jetbrains.annotations.NotNull
    com.solopov.common.data.firebase.dao.UserFirebaseDao userFirebaseDao, @org.jetbrains.annotations.NotNull
    com.solopov.feature_authentication_impl.data.mappers.UserMappers userMappers) {
        super();
    }
    
    @java.lang.Override
    public void createUser(@org.jetbrains.annotations.NotNull
    java.lang.String email, @org.jetbrains.annotations.NotNull
    java.lang.String password) {
    }
}