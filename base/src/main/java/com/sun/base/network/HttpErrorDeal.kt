package com.sun.base.network

import com.blankj.utilcode.util.ToastUtils
import com.sun.base.R
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException

object HttpErrorDeal {

    /**
     * 处理http异常
     * @param error 异常信息
     * @param deal 异常时处理方法
     */
    fun dealHttpError(error: Throwable, deal: (() -> Unit)? = null) {
        when (error) {
            is SocketException -> {
                ToastUtils.showShort(R.string.server_connection_abnormal)
            }
            is HttpException -> {
                ToastUtils.showShort(R.string.server_connection_failed)
            }
            is SocketTimeoutException -> {
                ToastUtils.showShort(R.string.request_timed_out)
            }
            is IOException -> {
                ToastUtils.showShort(R.string.server_connection_failed)
            }
            is CancellationException -> {
                //协程被取消  正常情况  不提示
            }
            else -> {
                error.message?.let {
                    if (it.isNotEmpty()) {
                        ToastUtils.showShort(it)
                    } else {
                        ToastUtils.showShort(R.string.null_pointer_exception)
                    }
                }
            }
        }

        if (deal != null) {
            deal()
        }
    }

}