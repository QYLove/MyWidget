package com.sun.base.expand

import com.blankj.utilcode.util.GsonUtils
import com.zxy.tiny.Tiny
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

fun getReqBodyFromMap(param: Any): RequestBody? {
    if (param == null) {
        return null
    }
    return GsonUtils.toJson(param)
        ?.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
}

fun getFileForm(file: File): RequestBody {
    return file.asRequestBody("text/plain".toMediaTypeOrNull())
}

fun getImageForm(file: File): RequestBody {
    return file.asRequestBody("image/*".toMediaTypeOrNull())
}

fun getReqBodyFormData(param: String): RequestBody {
    return param.toRequestBody("multipart/form-data".toMediaTypeOrNull())
}

fun getReqBodyImage(param: String): RequestBody {
    return param.toRequestBody("image/*".toMediaTypeOrNull())
}

fun getReqPart(b: ByteArray): MultipartBody.Part {
    val result =
        Tiny.getInstance().source(b).asFile().asFile().withOptions(Tiny.FileCompressOptions())
            .compressSync()
    val file = File(result.outfile)
    return MultipartBody.Part.createFormData("file", file.name, getImageForm(file))
}

fun getReqPart(path: String): MultipartBody.Part {
    val result = Tiny.getInstance().source(path).asFile().withOptions(Tiny.FileCompressOptions())
        .compressSync()
    val file = File(result.outfile)
    return MultipartBody.Part.createFormData("file", file.name, getImageForm(file))
}

fun getReqPartList(list: List<String>): List<MultipartBody.Part> {
    val result = Tiny.getInstance().source(list.toTypedArray())
        .batchAsFile().withOptions(Tiny.FileCompressOptions()).batchCompressSync()
    val res = mutableListOf<MultipartBody.Part>()
    for (s in result.outfiles) {
        val file = File(s)
        val part = MultipartBody.Part.createFormData("files", file.name, getImageForm(file))
        res.add(part)
    }
    return res
}