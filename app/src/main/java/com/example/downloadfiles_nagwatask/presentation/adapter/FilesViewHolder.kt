package com.example.downloadfiles_nagwatask.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.downloadfiles_nagwatask.R
import com.example.downloadfiles_nagwatask.databinding.ItemFileBinding
import com.example.downloadfiles_nagwatask.presentation.uimodel.FileDataItem
import com.example.downloadfiles_nagwatask.utils.Constants
import com.example.downloadfiles_nagwatask.utils.gone
import com.example.downloadfiles_nagwatask.utils.setResourceToImageView
import com.example.downloadfiles_nagwatask.utils.visible

class FilesViewHolder(
    private val view: ItemFileBinding,
    private var onDownloadClicked: (FileDataItem) -> Unit,
    private var onViewVideoClicked: (String?) -> Unit
) : RecyclerView.ViewHolder(view.root) {
    fun bind(item: FileDataItem) {
        view.fileTitleTextView.text = item.name ?: ""
        view.root.setOnClickListener {
            if (item.fileUri.isNotEmpty())
                onViewVideoClicked.invoke(item.fileUri)
            else {
                view.status.visible()
                view.percentage.visible()
                view.status.text = itemView.context.getString(R.string.downloading)
                onDownloadClicked.invoke(item)
            }
        }
        if (item.isDownloaded) {
            view.percentage.visible()
            view.percentage.text = itemView.context.getString(R.string._100)
            view.status.text = itemView.context.getString(R.string.downloaded)
            view.icDownload.setResourceToImageView(R.drawable.ic_done)
        }
        when (item.type) {
            Constants.FileType.PDF -> view.imageType.setResourceToImageView(R.drawable.pdf_image)
            Constants.FileType.VIDEO -> view.imageType.setResourceToImageView(R.drawable.video_image)
        }
    }
}