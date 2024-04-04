package com.solopov.feature_instructor_impl.data.repository;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u001f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\rH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u000f"}, d2 = {"Lcom/solopov/feature_instructor_impl/data/repository/InstructorRepositoryImpl;", "Lcom/solopov/feature_instructor_api/domain/interfaces/InstructorRepository;", "api", "Lcom/solopov/feature_instructor_impl/data/network/InstructorApi;", "db", "Lcom/solopov/common/data/db/AppDatabase;", "instructorMappers", "Lcom/solopov/feature_instructor_impl/data/mappers/InstructorMappers;", "(Lcom/solopov/feature_instructor_impl/data/network/InstructorApi;Lcom/solopov/common/data/db/AppDatabase;Lcom/solopov/feature_instructor_impl/data/mappers/InstructorMappers;)V", "getInstructorsBySportId", "", "Lcom/solopov/feature_instructor_api/domain/model/Instructor;", "sportId", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "feature-instructor-impl_debug"})
public final class InstructorRepositoryImpl implements com.solopov.feature_instructor_api.domain.interfaces.InstructorRepository {
    @org.jetbrains.annotations.NotNull
    private final com.solopov.feature_instructor_impl.data.network.InstructorApi api = null;
    @org.jetbrains.annotations.NotNull
    private final com.solopov.common.data.db.AppDatabase db = null;
    @org.jetbrains.annotations.NotNull
    private final com.solopov.feature_instructor_impl.data.mappers.InstructorMappers instructorMappers = null;
    
    @javax.inject.Inject
    public InstructorRepositoryImpl(@org.jetbrains.annotations.NotNull
    com.solopov.feature_instructor_impl.data.network.InstructorApi api, @org.jetbrains.annotations.NotNull
    com.solopov.common.data.db.AppDatabase db, @org.jetbrains.annotations.NotNull
    com.solopov.feature_instructor_impl.data.mappers.InstructorMappers instructorMappers) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getInstructorsBySportId(int sportId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.solopov.feature_instructor_api.domain.model.Instructor>> $completion) {
        return null;
    }
}