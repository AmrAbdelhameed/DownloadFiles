package com.example.downloadfiles_nagwatask.core.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.downloadfiles_nagwatask.presentation.uimodel.FileDataItem
import com.example.downloadfiles_nagwatask.utils.Constants.Data.PROGRESS
import com.example.downloadfiles_nagwatask.utils.Constants.Data.SEND_DOWNLOAD_ITEM_TO_DOWNLOAD_FILE_MANAGER
import com.example.downloadfiles_nagwatask.utils.Constants.Data.SEND_RESULTED_ITEM_VIEW_TO_UPDATE
import com.example.downloadfiles_nagwatask.utils.Constants.Error.EMPTY_FIELD_ERROR
import com.example.downloadfiles_nagwatask.utils.Constants.Error.EMPTY_RESPONSE_ERROR
import com.example.downloadfiles_nagwatask.utils.Constants.FileType.MP4_EXTENSION
import com.example.downloadfiles_nagwatask.utils.Constants.FileType.PDF
import com.example.downloadfiles_nagwatask.utils.Constants.FileType.PDF_EXTENSION
import com.example.downloadfiles_nagwatask.utils.deserializeFromGson
import com.example.downloadfiles_nagwatask.utils.serializeToGson
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL

class DownloadFilesWorkManager(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    private lateinit var item: FileDataItem

    override suspend fun doWork(): Result {
        val firstUpdate = workDataOf(PROGRESS to 0)
        val lastUpdate = workDataOf(PROGRESS to 100)
        val inData = this.inputData.getString(SEND_DOWNLOAD_ITEM_TO_DOWNLOAD_FILE_MANAGER) ?: ""
        val data = Data.Builder()
        return try {
            item = inData.deserializeFromGson(Gson())
            setProgressAsync(firstUpdate)
            download(item.url ?: EMPTY_FIELD_ERROR)
            data.putString(SEND_RESULTED_ITEM_VIEW_TO_UPDATE, item.serializeToGson(Gson()))
            setProgressAsync(lastUpdate)
            Result.success(data.build())
        } catch (e: Exception) {
            data.putString(EMPTY_RESPONSE_ERROR, item.serializeToGson(Gson()))
            Result.failure(data.build())
        }
    }

    private fun download(url: String) {
        URL(url).openStream().use { input ->
            setProgressAsync(workDataOf(PROGRESS to 75))
            FileOutputStream(createFile()).use { output -> input.copyTo(output) }
        }
    }

    @Throws(IOException::class)
    private fun createFile(): File {
        val suffix = if (item.type == PDF) PDF_EXTENSION else MP4_EXTENSION
        val storageDir: File = applicationContext.getExternalFilesDir(Environment.DIRECTORY_DCIM)!!
        return File.createTempFile(item.name ?: EMPTY_FIELD_ERROR, suffix, storageDir).apply {
            item.fileUri = this.absolutePath
            item.isDownloaded = true
        }
    }
}