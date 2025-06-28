package com.tcssj.mbjmb

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.gson.Gson
import com.tcssj.mbjmb.databinding.ActivityMainBinding
import com.tcssj.mbjmb.net.ParameterBuilder
import com.tcssj.mbjmb.net.RetrofitClient
import com.tcssj.mbjmb.viewModel.MainViewModel
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRequest.setOnClickListener {
            // 请求接口
            Toast.makeText(this, "请求接口", Toast.LENGTH_SHORT).show()


            lifecycleScope.launch {
                viewModel.sendVerificationCode("81991415335", "text")
            }

        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.verificationResult.collect { result ->
                    if (result != null) {
                        if (result.isSuccess) {
                            Toast.makeText(this@MainActivity, "请求成功", Toast.LENGTH_SHORT).show()
                            binding.tvResponse.text = result.getOrNull().toString()
                        } else {
                            Toast.makeText(this@MainActivity, "请求失败", Toast.LENGTH_SHORT).show()
                            binding.tvResponse.text = "请求失败"
                        }
                    } else {
                        binding.tvResponse.text = "请求失败"
                        Toast.makeText(this@MainActivity, "请求失败", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    suspend fun sendVerificationCode(mobile: String, type: String = "text") {
        try {
            val request = ParameterBuilder.encryptParams(mapOf("mobile" to mobile, "type" to type))
            Log.d("MainActivity", "Request: ${Gson().toJson(request)}")
            val response = RetrofitClient.apiService.sendVerifyCode(request)

            Log.d("MainActivity", "Response: ${Gson().toJson(response)}")

            val decryptedResponse = ParameterBuilder.decryptResponse(response?.message ?: "")

            Log.d("MainActivity", "Response decrypted: $decryptedResponse")

        } catch (e: Exception) {
            Log.e("MainActivity", "Error sending verification code", e)
//            Result.failure(e)
        }
    }
}