package com.example.downloadfiles_nagwatask.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.downloadfiles_nagwatask.BuildConfig
import com.example.downloadfiles_nagwatask.R
import com.example.downloadfiles_nagwatask.presentation.uimodel.FileDataItem
import com.google.gson.Gson
import java.io.File
import java.util.*

fun String.getMimeType(): String? {
    var type: String? = null
    val extension: String? = MimeTypeMap.getFileExtensionFromUrl(this)
    if (extension != null) {
        type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
    }
    return type
}

fun String.getFileUri(context: Context): Uri? {
    val file = File(this)
    return FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider", file
    )
}

fun Activity.createChooserIntent(uri: Uri?) {
    Intent(ACTION_VIEW).apply {
        this.setDataAndType(uri, uri?.toString()?.getMimeType())
        this.addFlags(
            Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        if (this.resolveActivity(packageManager) != null) {
            val intentChooser =
                Intent.createChooser(this, this@createChooserIntent.getString(R.string.open_with))
            this@createChooserIntent.startActivity(intentChooser)
        }
    }
}

fun <T> T.serializeToGson(gson: Gson): String {
    return gson.toJson(this)
}

fun String.deserializeFromGson(gson: Gson): FileDataItem {
    return gson.fromJson(this, FileDataItem::class.java)
}

fun ImageView.setResourceToImageView(@DrawableRes id: Int) {
    this.setImageDrawable(ContextCompat.getDrawable(this.context, id))
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}