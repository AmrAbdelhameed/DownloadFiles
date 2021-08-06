package com.example.downloadfiles_nagwatask.utils

object Constants {
    object Error {
        const val EMPTY_RESPONSE_ERROR = "err"
        const val EMPTY_FIELD_ERROR = "null_field"
    }

    object FileType {
        const val PDF = "PDF"
        const val VIDEO = "VIDEO"
        const val PDF_EXTENSION = ".pdf"
        const val MP4_EXTENSION = ".mp4"
    }

    object Data {
        const val SEND_DOWNLOAD_ITEM_TO_DOWNLOAD_FILE_MANAGER = "download_item"
        const val SEND_RESULTED_ITEM_VIEW_TO_UPDATE = "downloaded_item"
        const val PROGRESS = "progress"
    }
}