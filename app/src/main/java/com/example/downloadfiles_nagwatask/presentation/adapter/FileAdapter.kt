package com.example.downloadfiles_nagwatask.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.downloadfiles_nagwatask.databinding.ItemFileBinding
import com.example.downloadfiles_nagwatask.presentation.uimodel.FileDataItem
import javax.inject.Inject

class FileAdapter @Inject constructor() :
    RecyclerView.Adapter<FilesViewHolder>() {
    private lateinit var onDownloadClicked: (FileDataItem) -> Unit
    private lateinit var onViewVideoClicked: (String?) -> Unit

    private val diffCallback = object : DiffUtil.ItemCallback<FileDataItem>() {
        override fun areItemsTheSame(oldItem: FileDataItem, newItem: FileDataItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: FileDataItem, newItem: FileDataItem): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilesViewHolder {
        val binding = ItemFileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilesViewHolder(binding, onDownloadClicked, onViewVideoClicked)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: FilesViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    fun setOnClicked(onDownloadClicked: (FileDataItem) -> Unit) {
        this.onDownloadClicked = onDownloadClicked
    }

    fun setOnViewVideoClicked(onViewVideoClick: (String?) -> Unit) {
        this.onViewVideoClicked = onViewVideoClick
    }

    fun setItems(filesList: List<FileDataItem?>) {
        differ.submitList(filesList)
    }
}