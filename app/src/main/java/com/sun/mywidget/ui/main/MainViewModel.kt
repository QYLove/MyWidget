package com.sun.mywidget.ui.main

import androidx.lifecycle.viewModelScope
import com.sun.base.base.BaseViewModel
import com.sun.base.base.State
import com.sun.base.bean.FileBean
import com.sun.mywidget.repository.FileRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel :BaseViewModel(){

    sealed class MainState:State{
        data class UploadState(val data: FileBean? = null):MainState()
        data class UploadListState(val data: List<FileBean>) : MainState()
    }

    fun uploadFile(path: String) {
        FileRepository().uploadFile(path, MainState.UploadState()).onEach { dataState ->
            setState(dataState)
        }.launchIn(viewModelScope)
    }
}