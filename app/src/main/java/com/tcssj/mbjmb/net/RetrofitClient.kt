package com.tcssj.mbjmb.net

import com.google.gson.Gson
import com.tcssj.mbjmb.api.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Author: Zhong Shuihong
 * Create Time: 2025/6/28
 * Description:
 */
object RetrofitClient {
    private const val BASE_URL = "http://47.117.39.225:10018/"

    private val gson = Gson()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()

            // 添加加密请求
            val header = ParameterBuilder.buildHeaders(gson)
            val requestBuilder = originalRequest.newBuilder()
            header.forEach { (key, value) ->
                requestBuilder.addHeader(key, value)
            }


            chain.proceed(requestBuilder.build())
        }
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
//            .addConverterFactory(DecryptConverterFactory("jYVS85xRwzJMBITy", Gson()))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}