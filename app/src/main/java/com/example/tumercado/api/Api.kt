package com.example.tumercado.api

import com.example.tumercado.entity.descriptionproduct.DescriptionProduct
import com.example.tumercado.entity.searchforid.InfoItemId
import com.example.tumercado.entity.searchforqueary.SearchResult
import com.google.gson.Gson
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {
    private fun getApi(): TuMercadoApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.mercadolibre.com/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()

        return retrofit.create(TuMercadoApi::class.java)
    }

    fun search(
        productName: String,
        callback: Callback<SearchResult>) {
        getApi().search(productName.replace(" ", "+")).enqueue(callback)
    }
    fun searchForId(
        id: String,
        callback: Callback<InfoItemId>) {
        getApi().searchForId(id).enqueue(callback)
    }
    fun searchDescription(
        id: String,
        callback: Callback<ArrayList<DescriptionProduct>>) {
        getApi().searchDescription(id).enqueue(callback)

    }
}