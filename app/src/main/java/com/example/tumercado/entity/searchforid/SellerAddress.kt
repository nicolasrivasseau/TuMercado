package com.example.tumercado.entity.searchforid

data class SellerAddress(
    val city: City,
    val country: Country,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val search_location: SearchLocation,
    val state: StateX
)