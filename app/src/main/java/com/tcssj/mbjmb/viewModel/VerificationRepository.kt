package com.tcssj.mbjmb.viewModel

import com.tcssj.mbjmb.model.VerifyResponse
import com.tcssj.mbjmb.net.ParameterBuilder
import com.tcssj.mbjmb.net.RetrofitClient

/**
 * Author: Zhong Shuihong
 * Create Time: 2025/6/28
 * Description:
 */
class VerificationRepository {
    suspend fun sendVerificationCode(mobile: String, type: String = "text"): Result<VerifyResponse<Any?>> {
        return try {
            val request = ParameterBuilder.encryptParams(mapOf("mobile" to mobile, "type" to type))
            val response = RetrofitClient.apiService.sendVerifyCode(request)

            if (response.isSuccess) {
                Result.success(response)
            } else {
                Result.failure(Exception("Request failed: ${response}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}