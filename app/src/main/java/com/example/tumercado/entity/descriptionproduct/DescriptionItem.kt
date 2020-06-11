package com.example.tumercado.entity.descriptionproduct

import com.google.gson.annotations.SerializedName

data class DescriptionItem(
    @SerializedName("created")  val created: String,
    @SerializedName("id")  val id: String,
    @SerializedName("plain_text") val plain_text: String,
    @SerializedName("snapshot") val snapshot: Snapshot,
    @SerializedName("text") val text: String
)