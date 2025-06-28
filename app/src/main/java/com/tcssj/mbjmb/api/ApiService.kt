package com.tcssj.mbjmb.api

import com.tcssj.mbjmb.model.VerifyResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Author: Zhong Shuihong
 * Create Time: 2025/6/28
 * Description:
 */
interface ApiService {

    @POST("auth/v3.1/user/sendVerifiyCode")
    suspend fun sendVerifyCode(@Body request: RequestBody): VerifyResponse<Any?>
}