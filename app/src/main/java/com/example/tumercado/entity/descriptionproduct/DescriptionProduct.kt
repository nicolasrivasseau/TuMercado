package com.example.tumercado.entity.descriptionproduct

import com.google.gson.annotations.SerializedName

data class DescriptionProduct(
    @SerializedName("descriptionItem") val descriptionItem:ArrayList<DescriptionItem>
)