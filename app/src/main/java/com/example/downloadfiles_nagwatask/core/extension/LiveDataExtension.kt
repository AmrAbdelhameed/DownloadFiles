package com.example.downloadfiles_nagwatask.core.extension

import androidx.lifecycle.MutableLiveData
import com.example.downloadfiles_nagwatask.core.model.BaseModel
import com.example.downloadfiles_nagwatask.core.viewstate.MainState

fun <T> MutableLiveData<BaseModel<T>>.setSuccess(data: T) {
    postValue(BaseModel(MainState.SUCCESS, data))
}

fun <T> MutableLiveData<BaseModel<T>>.setLoading() {
    postValue(BaseModel(MainState.LOADING, value?.data))
}

fun <T> MutableLiveData<BaseModel<T>>.setError(message: String? = null) {
    postValue(BaseModel(MainState.ERROR, value?.data, message))
}