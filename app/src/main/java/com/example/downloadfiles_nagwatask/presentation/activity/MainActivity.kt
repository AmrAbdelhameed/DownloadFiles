package com.example.downloadfiles_nagwatask.presentation.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.work.WorkInfo
import com.example.downloadfiles_nagwatask.R
import com.example.downloadfiles_nagwatask.core.viewstate.MainState
import com.example.downloadfiles_nagwatask.presentation.adapter.FileAdapter
import com.example.downloadfiles_nagwatask.presentation.viewmodel.FileViewModel
import com.example.downloadfiles_nagwatask.utils.*
import com.example.downloadfiles_nagwatask.utils.Constants.Data.PROGRESS
import com.example.downloadfiles_nagwatask.utils.Constants.Data.SEND_RESULTED_ITEM_VIEW_TO_UPDATE
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var fileAdapter: FileAdapter

    @Inject
    lateinit var fileViewModel: FileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        initRecyclerView()
        loadFiles()
    }

    private fun initRecyclerView() {
        recycler_view_files.adapter = fileAdapter
    }

    private fun loadFiles() {
        fileViewModel.filesList.observe(this, {
            when (it.state) {
                MainState.SUCCESS -> {
                    it.data?.let { it1 -> fileAdapter.setItems(it1) }
                    progress_bar.visibility = View.GONE
                }
                MainState.LOADING -> {
                    progress_bar.visibility = View.VISIBLE
                }
                else -> progress_bar.visibility = View.GONE
            }
            fileAdapter.setOnClicked { item ->
                fileViewModel.downloadFile(item)
                observeWorkManager()
            }
            fileAdapter.setOnViewVideoClicked { uri ->
                createChooserIntent(uri?.getFileUri(this@MainActivity))
            }
        })
    }

    private fun observeWorkManager() {
        fileViewModel.operationState?.let { workInfo ->
            workInfo.observe(this, { info ->
                val outputData = info.outputData.getString(SEND_RESULTED_ITEM_VIEW_TO_UPDATE) ?: ""
                when {
                    info.state == WorkInfo.State.RUNNING -> {
                        val progress = info.progress.getInt(PROGRESS, 0)
                        Log.d(TAG, "observeWorkManager: $progress")
                    }
                    info.state.isFinished -> {
                        fileViewModel.updateFilesList(outputData)
                    }
                    info.state == WorkInfo.State.FAILED -> {
                        fileViewModel.updateFilesList(outputData)
                    }
                }
            })
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}