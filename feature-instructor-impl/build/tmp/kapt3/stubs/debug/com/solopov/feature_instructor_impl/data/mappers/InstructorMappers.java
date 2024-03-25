package com.solopov.feature_instructor_impl.data.mappers;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\b"}, d2 = {"Lcom/solopov/feature_instructor_impl/data/mappers/InstructorMappers;", "", "()V", "mapInstructorDataToInstructor", "Lcom/solopov/feature_instructor_api/domain/model/Instructor;", "instructorData", "Lcom/solopov/feature_instructor_impl/data/network/pojo/response/InstructorData;", "Companion", "feature-instructor-impl_debug"})
public final class InstructorMappers {
    @org.jetbrains.annotations.NotNull
    public static final com.solopov.feature_instructor_impl.data.mappers.InstructorMappers.Companion Companion = null;
    @org.jetbrains.annotations.NotNull
    private static final java.util.List<java.lang.String> sportTypes = null;
    
    @javax.inject.Inject
    public InstructorMappers() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.solopov.feature_instructor_api.domain.model.Instructor mapInstructorDataToInstructor(@org.jetbrains.annotations.NotNull
    com.solopov.feature_instructor_impl.data.network.pojo.response.InstructorData instructorData) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lcom/solopov/feature_instructor_impl/data/mappers/InstructorMappers$Companion;", "", "()V", "sportTypes", "", "", "getSportTypes", "()Ljava/util/List;", "feature-instructor-impl_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.List<java.lang.String> getSportTypes() {
            return null;
        }
    }
}