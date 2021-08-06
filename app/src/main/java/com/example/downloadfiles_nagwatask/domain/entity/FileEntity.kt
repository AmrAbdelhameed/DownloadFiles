package com.example.downloadfiles_nagwatask.domain.entity

import com.squareup.moshi.Json

data class FileEntity(
    @field:Json(name = "id") var id: Int,
    @field:Json(name = "type") var type: String,
    @field:Json(name = "url") var url: String,
    @field:Json(name = "name") var name: String
)