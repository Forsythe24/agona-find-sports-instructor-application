package com.solopov.feature_instructor_impl.data.network.interceptors

import com.solopov.common.utils.ParamsKey
import com.solopov.instructors.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiInfoInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(ParamsKey.API_KEY, BuildConfig.API_KEY)
            .addHeader(ParamsKey.API_HOST, BuildConfig.API_HOST)
            .build()
        return chain.proceed(request)
    }
}
