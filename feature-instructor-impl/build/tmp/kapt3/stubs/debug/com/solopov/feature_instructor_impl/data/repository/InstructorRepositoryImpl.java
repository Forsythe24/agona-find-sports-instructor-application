package com.solopov.feature_instructor_impl.data.repository;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B/\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u001f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0013"}, d2 = {"Lcom/solopov/feature_instructor_impl/data/repository/InstructorRepositoryImpl;", "Lcom/solopov/feature_instructor_api/domain/interfaces/InstructorRepository;", "api", "Lcom/solopov/feature_instructor_impl/data/network/InstructorApi;", "db", "Lcom/solopov/common/data/db/AppDatabase;", "instructorMappers", "Lcom/solopov/feature_instructor_impl/data/mappers/InstructorMappers;", "userFirebaseDao", "Lcom/solopov/common/data/firebase/dao/UserFirebaseDao;", "resManager", "Lcom/solopov/common/core/resources/ResourceManager;", "(Lcom/solopov/feature_instructor_impl/data/network/InstructorApi;Lcom/solopov/common/data/db/AppDatabase;Lcom/solopov/feature_instructor_impl/data/mappers/InstructorMappers;Lcom/solopov/common/data/firebase/dao/UserFirebaseDao;Lcom/solopov/common/core/resources/ResourceManager;)V", "getInstructorsBySportId", "", "Lcom/solopov/feature_instructor_api/domain/model/Instructor;", "sportId", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "feature-instructor-impl_debug"})
public final class InstructorRepositoryImpl implements com.solopov.feature_instructor_api.domain.interfaces.InstructorRepository {
    @org.jetbrains.annotations.NotNull
    private final com.solopov.feature_instructor_impl.data.network.InstructorApi api = null;
    @org.jetbrains.annotations.NotNull
    private final com.solopov.common.data.db.AppDatabase db = null;
    @org.jetbrains.annotations.NotNull
    private final com.solopov.feature_instructor_impl.data.mappers.InstructorMappers instructorMappers = null;
    @org.jetbrains.annotations.NotNull
    private final com.solopov.common.data.firebase.dao.UserFirebaseDao userFirebaseDao = null;
    @org.jetbrains.annotations.NotNull
    private final com.solopov.common.core.resources.ResourceManager resManager = null;
    
    @javax.inject.Inject
    public InstructorRepositoryImpl(@org.jetbrains.annotations.NotNull
    com.solopov.feature_instructor_impl.data.network.InstructorApi api, @org.jetbrains.annotations.NotNull
    com.solopov.common.data.db.AppDatabase db, @org.jetbrains.annotations.NotNull
    com.solopov.feature_instructor_impl.data.mappers.InstructorMappers instructorMappers, @org.jetbrains.annotations.NotNull
    com.solopov.common.data.firebase.dao.UserFirebaseDao userFirebaseDao, @org.jetbrains.annotations.NotNull
    com.solopov.common.core.resources.ResourceManager resManager) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getInstructorsBySportId(int sportId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.solopov.feature_instructor_api.domain.model.Instructor>> $completion) {
        return null;
    }
}