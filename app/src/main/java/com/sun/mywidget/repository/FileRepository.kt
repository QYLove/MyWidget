package com.sun.mywidget.repository

import com.sun.base.base.ErrorState
import com.sun.base.base.FailureState
import com.sun.base.base.LoadingState
import com.sun.base.base.State
import com.sun.base.expand.getReqBodyFormData
import com.sun.base.expand.getReqPart
import com.sun.base.expand.getReqPartList
import com.sun.base.network.HttpErrorDeal
import com.sun.base.network.RetrofitServiceBuilder
import com.sun.mywidget.api.FileApi
import com.sun.mywidget.ui.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface FileRep {

    fun uploadFile(path: String, state: State): Flow<State>

    fun uploadFile(list: List<String>): Flow<State>
}

class FileRepository : FileRep {

    private var network = RetrofitServiceBuilder.createService(FileApi::class.java)

    override fun uploadFile(path: String, state: State): Flow<State> = flow {
        emit(LoadingState)
        network?.uploadFile(getReqBodyFormData("file"), getReqPart(path))?.let {
            if (it.code == 200) {
                when (state) {
                    is MainViewModel.MainState.UploadState -> {
                        emit(MainViewModel.MainState.UploadState(it.data!!))
                    }
                }
            } else {
                emit(FailureState(it.code, it.msg))
            }
        }
    }.catch {
        emit(ErrorState(it))
        if (it is Exception) {
            HttpErrorDeal.dealHttpError(it)
        }
    }.flowOn(Dispatchers.IO)

    override fun uploadFile(list: List<String>): Flow<State> = flow {
        emit(LoadingState)
        network?.batchUploadFile(getReqPartList(list))?.let {
            if (it.code == 200) {
                emit(MainViewModel.MainState.UploadListState(it.data!!))
            } else {
                emit(FailureState(it.code, it.msg))
            }
        }
    }.catch {
        emit(ErrorState(it))
        if (it is Exception) {
            HttpErrorDeal.dealHttpError(it)
        }
    }.flowOn(Dispatchers.IO)
}