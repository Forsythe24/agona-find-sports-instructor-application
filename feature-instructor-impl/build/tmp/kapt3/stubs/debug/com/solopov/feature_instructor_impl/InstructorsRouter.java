package com.solopov.feature_instructor_impl;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&\u00a8\u0006\u0007"}, d2 = {"Lcom/solopov/feature_instructor_impl/InstructorsRouter;", "", "openInstructor", "", "instructorId", "", "returnToInstructors", "feature-instructor-impl_debug"})
public abstract interface InstructorsRouter {
    
    public abstract void openInstructor(@org.jetbrains.annotations.NotNull
    java.lang.String instructorId);
    
    public abstract void returnToInstructors();
}