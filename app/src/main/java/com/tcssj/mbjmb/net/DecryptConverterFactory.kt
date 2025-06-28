package com.tcssj.mbjmb.net

import android.util.Log
import com.google.gson.Gson
import com.tcssj.mbjmb.utils.AESUtil2
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.Type

/**
 * Author: Zhong Shuihong
 * Create Time: 2025/6/28
 * Description:
 */
class DecryptConverterFactory(private val decryptKey: String,
                              private val gson: Gson
): retrofit2.Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val delegate = retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
        return Converter<ResponseBody, Any> { value ->
            try {
                // 获取加密的响应字符串
                val encryptedResponse = value.string()
                Log.d("DecryptConverterFactory", "encryptedResponse: $encryptedResponse")
                val result = gson.fromJson<Type>(encryptedResponse, type)
                // 解密响应
                val decryptedResponse = AESUtil2.decrypt(encryptedResponse, decryptKey)
                // 将解密后的JSON字符串转换为对象
//                gson.fromJson(decryptedResponse, type)
                Log.d("DecryptConverterFactory", "decryptedResponse: $decryptedResponse")
                decryptedResponse
            } catch (e: Exception) {
                throw IOException("Failed to decrypt response", e)
            }
        }
    }
}