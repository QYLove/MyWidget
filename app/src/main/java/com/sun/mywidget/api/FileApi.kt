package com.sun.mywidget.api

import com.sun.base.base.BaseReqData
import com.sun.base.bean.FileBean
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FileApi {

    /**
     * 文件上传
     */
    @Multipart
    @POST("/file/upload")
    suspend fun uploadFile(
        @Part("description") description: RequestBody,
        @Part part: MultipartBody.Part
    ): BaseReqData<FileBean>

    /**
     * 批量文件上传
     */
    @Multipart
    @POST("/file/batch/upload")
    suspend fun batchUploadFile(@Part parts: List<MultipartBody.Part>): BaseReqData<List<FileBean>>

}