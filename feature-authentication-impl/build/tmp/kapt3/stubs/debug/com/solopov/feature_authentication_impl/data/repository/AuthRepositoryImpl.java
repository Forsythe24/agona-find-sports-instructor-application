package com.solopov.feature_authentication_impl.data.repository;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J9\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\nH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0010J%\u0010\u0011\u001a\u00020\u00122\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\nH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0014"}, d2 = {"Lcom/solopov/feature_authentication_impl/data/repository/AuthRepositoryImpl;", "Lcom/solopov/feature_authentication_api/domain/interfaces/AuthRepository;", "userFirebaseDao", "Lcom/solopov/common/data/firebase/dao/UserFirebaseDao;", "userMappers", "Lcom/solopov/feature_authentication_impl/data/mappers/UserMappers;", "(Lcom/solopov/common/data/firebase/dao/UserFirebaseDao;Lcom/solopov/feature_authentication_impl/data/mappers/UserMappers;)V", "createUser", "Lcom/solopov/feature_authentication_api/domain/model/User;", "email", "", "password", "name", "age", "", "gender", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signInUser", "", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "feature-authentication-impl_debug"})
public final class AuthRepositoryImpl implements com.solopov.feature_authentication_api.domain.interfaces.AuthRepository {
    @org.jetbrains.annotations.NotNull
    private final com.solopov.common.data.firebase.dao.UserFirebaseDao userFirebaseDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.solopov.feature_authentication_impl.data.mappers.UserMappers userMappers = null;
    
    @javax.inject.Inject
    public AuthRepositoryImpl(@org.jetbrains.annotations.NotNull
    com.solopov.common.data.firebase.dao.UserFirebaseDao userFirebaseDao, @org.jetbrains.annotations.NotNull
    com.solopov.feature_authentication_impl.data.mappers.UserMappers userMappers) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object createUser(@org.jetbrains.annotations.NotNull
    java.lang.String email, @org.jetbrains.annotations.NotNull
    java.lang.String password, @org.jetbrains.annotations.NotNull
    java.lang.String name, int age, @org.jetbrains.annotations.NotNull
    java.lang.String gender, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.solopov.feature_authentication_api.domain.model.User> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object signInUser(@org.jetbrains.annotations.Nullable
    java.lang.String email, @org.jetbrains.annotations.Nullable
    java.lang.String password, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
}