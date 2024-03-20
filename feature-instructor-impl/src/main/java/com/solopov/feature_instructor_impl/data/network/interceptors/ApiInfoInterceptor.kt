package com.solopov.feature_instructor_impl.data.network.interceptors

import com.solopov.feature_instructor_impl.utils.ParamKeys
import com.solopov.instructors.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiInfoInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
            .addHeader(ParamKeys.API_KEY, BuildConfig.API_KEY)
            .addHeader(ParamKeys.API_HOST, BuildConfig.API_HOST)

        return chain.proceed(requestBuilder.build())
    }
}
