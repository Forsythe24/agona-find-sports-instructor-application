package com.solopov.feature_instructor_impl.di;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0007H&J\b\u0010\b\u001a\u00020\tH&\u00a8\u0006\n"}, d2 = {"Lcom/solopov/feature_instructor_impl/di/InstructorFeatureDependencies;", "", "db", "Lcom/solopov/common/data/db/AppDatabase;", "networkApiCreator", "Lcom/solopov/common/data/network/NetworkApiCreator;", "resourceManager", "Lcom/solopov/common/core/resources/ResourceManager;", "userFirebaseDao", "Lcom/solopov/common/data/firebase/dao/UserFirebaseDao;", "feature-instructor-impl_debug"})
public abstract interface InstructorFeatureDependencies {
    
    @org.jetbrains.annotations.NotNull
    public abstract com.solopov.common.data.network.NetworkApiCreator networkApiCreator();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.solopov.common.data.firebase.dao.UserFirebaseDao userFirebaseDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.solopov.common.data.db.AppDatabase db();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.solopov.common.core.resources.ResourceManager resourceManager();
}