package com.example.downloadfiles_nagwatask.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.example.downloadfiles_nagwatask.core.extension.setError
import com.example.downloadfiles_nagwatask.core.extension.setLoading
import com.example.downloadfiles_nagwatask.core.extension.setSuccess
import com.example.downloadfiles_nagwatask.core.model.BaseModel
import com.example.downloadfiles_nagwatask.core.utils.DownloadFilesWorkManager
import com.example.downloadfiles_nagwatask.core.viewmodel.BaseViewModel
import com.example.downloadfiles_nagwatask.domain.interactor.FilesUseCase
import com.example.downloadfiles_nagwatask.presentation.uimodel.FileDataItem
import com.example.downloadfiles_nagwatask.utils.Constants
import com.example.downloadfiles_nagwatask.utils.deserializeFromGson
import com.example.downloadfiles_nagwatask.utils.serializeToGson
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FileViewModel @Inject constructor(
    private val filesUseCase: FilesUseCase,
    private val workManager: WorkManager,
    private val constraints: Constraints,
    private val dataBuilder: Data.Builder,
    private val gson: Gson
) : BaseViewModel() {
    var operationState: LiveData<WorkInfo>? = null
    private var _fileLiveData: MutableLiveData<BaseModel<List<FileDataItem>>> = MutableLiveData()
    val filesList: LiveData<BaseModel<List<FileDataItem>>>
        get() = _fileLiveData

    init {
        getFiles()
    }

    private fun getFiles() {
        addDisposable(filesUseCase.getFiles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _fileLiveData.setLoading() }
            .subscribe({
                _fileLiveData.setSuccess(it.map { fileEntity ->
                    FileDataItem(
                        fileEntity.id,
                        fileEntity.type,
                        fileEntity.url,
                        fileEntity.name
                    )
                })
            }, {
                _fileLiveData.setError(it.message)
            })
        )
    }

    fun downloadFile(item: FileDataItem) {
        val oneTimeWorkerBuilder = OneTimeWorkRequest
            .Builder(DownloadFilesWorkManager::class.java)
            .setConstraints(constraints)
        dataBuilder.putString(
            Constants.Data.SEND_DOWNLOAD_ITEM_TO_DOWNLOAD_FILE_MANAGER,
            item.serializeToGson(gson)
        )
        oneTimeWorkerBuilder.setInputData(dataBuilder.build())
        oneTimeWorkerBuilder.build()
        val workRequest = oneTimeWorkerBuilder.build()
        val id = workRequest.id
        workManager.enqueue(workRequest)
        operationState = workManager.getWorkInfoByIdLiveData(id)
    }

    fun updateFilesList(itemString: String) {
        val item = itemString.deserializeFromGson(gson)
        val items = filesList.value?.data
        items?.let { uiModelList ->
            uiModelList.find { it.id == item.id }?.let {
                val newItem = it.copy(
                    id = item.id,
                    type = item.type,
                    url = item.url,
                    name = item.name,
                    fileUri = item.fileUri,
                    isDownloaded = item.isDownloaded
                )
                _fileLiveData.setSuccess(uiModelList.map { uiItem -> if (uiItem.id == item.id) newItem else uiItem })
            }
        }
    }
}