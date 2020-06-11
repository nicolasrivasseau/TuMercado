package com.example.tumercado.entity.descriptionproduct

import com.google.gson.annotations.SerializedName

data class Snapshot(
    @SerializedName("height")  val height: Int,
    @SerializedName("status")  val status: String,
    @SerializedName("url")  val url: String,
    @SerializedName("width") val width: Int
)