package com.solopov.common.data.network.utils

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkApiCreator(
    var okHttpClient: OkHttpClient,
    private val baseUrl: String,
) {

    fun <T> create(service: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(service)
    }

}
