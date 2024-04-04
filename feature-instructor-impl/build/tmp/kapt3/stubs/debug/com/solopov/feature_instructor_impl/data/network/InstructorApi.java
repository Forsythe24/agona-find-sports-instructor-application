package com.solopov.feature_instructor_impl.data.network;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001d\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0007"}, d2 = {"Lcom/solopov/feature_instructor_impl/data/network/InstructorApi;", "", "getInstructorsBySportId", "Lcom/solopov/feature_instructor_impl/data/network/pojo/response/InstructorsResponse;", "id", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "feature-instructor-impl_debug"})
public abstract interface InstructorApi {
    
    @retrofit2.http.GET(value = "{id}/players")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getInstructorsBySportId(@retrofit2.http.Path(value = "id")
    int id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.solopov.feature_instructor_impl.data.network.pojo.response.InstructorsResponse> $completion);
}