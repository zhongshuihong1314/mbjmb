package com.tcssj.mbjmb.net

import com.google.gson.Gson
import com.tcssj.mbjmb.utils.AESUtil2
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

/**
 * Author: Zhong Shuihong
 * Create Time: 2025/6/28
 * Description:
 */
object ParameterBuilder {

    // 包别名
    private const val PACKAGE_NAME = "mbjmb"
    // 请求头参数名称
    private const val HEADER_KEY_NAME = "HCFQ"
    // 请求参数加密名称
    private const val PARAM_ENCRYPT_NAME = "BFBPY"
    // 请求头加密key
    private const val HEADER_ENCRYPT_KEY = "xDBrgJdnnY2w1Do7Ik6otonXQRgQyt46"
    // 请求参数加密key
    private const val PARAM_ENCRYPT_KEY = "jYVS85xRwzJMBITy"
    // 应用版本号
    private const val VERSION = "12.0.0"
    // 包名
    private const val PACKAGE = "com.tcssj.mbjmb"

    private val token = mapOf(
        "sourceChannel" to "Organic",
        "packageName" to PACKAGE_NAME,
        "adid" to "",
        "version" to VERSION,
        "uuId" to "",
        "userId" to ""
    )

    fun buildHeaders(gson: Gson): Map<String, String> {
//        val token = """
//            {
//                "sourceChannel":"Organic",
//                "packageName":"$PACKAGE",
//                "adid":"",
//                "version":"$VERSION",
//                "uuId":"",
//                "userId":""
//            }
//        """.trimIndent()

        return mapOf(
            "packageName" to PACKAGE_NAME,
            HEADER_KEY_NAME to AESUtil2.encrypt(gson.toJson(token), HEADER_ENCRYPT_KEY)
        )
    }

    fun encryptParams(params: Map<String, String>): RequestBody {
        val jsonParams = Gson().toJson(params)
        val encryptedParams = AESUtil2.encrypt(jsonParams, PARAM_ENCRYPT_KEY)

        return RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            encryptedParams.toByteArray()
        )
    }

    fun encryptParams(params: String): String {
        return AESUtil2.encrypt(params, PARAM_ENCRYPT_KEY)
    }

    fun decryptResponse(encryptedResponse: String): String {
        val decryptedResponse = AESUtil2.decrypt(encryptedResponse, PARAM_ENCRYPT_KEY)
        return decryptedResponse
    }
}