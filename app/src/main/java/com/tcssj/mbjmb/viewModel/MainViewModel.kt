package com.tcssj.mbjmb.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcssj.mbjmb.model.VerifyResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * Author: Zhong Shuihong
 * Create Time: 2025/6/28
 * Description:
 */
class MainViewModel: ViewModel() {
    private val repository = VerificationRepository()

    val verificationResult = MutableStateFlow<Result<VerifyResponse<Any?>?>?>(null)

    fun sendVerificationCode(mobile: String, type: String = "text") {
        viewModelScope.launch {
//            verificationResult.value = Result.loading()
            repository.sendVerificationCode(mobile, type).let { result ->
                verificationResult.value = result
            }
        }

    }
}