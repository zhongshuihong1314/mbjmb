package com.tcssj.mbjmb.model

/**
 * Author: Zhong Shuihong
 * Create Time: 2025/6/28
 * Description:
 */
data class VerifyResponse<T> (
    val status: String,
    val message: String?,
    val body: T?,
    val timestamp: String? // 根据实际返回数据结构调整
)
{
    val isSuccess: Boolean
        get() =  status == "500"

    override fun toString(): String {
        return "status: $status, \nmessage: $message, \nbody: $body, \ntimestamp: $timestamp"
    }
}