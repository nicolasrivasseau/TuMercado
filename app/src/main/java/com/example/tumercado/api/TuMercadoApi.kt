package com.example.tumercado.api

import com.example.tumercado.entity.descriptionproduct.DescriptionProduct
import com.example.tumercado.entity.searchforid.InfoItemId
import com.example.tumercado.entity.searchforqueary.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TuMercadoApi {
    @GET("sites/MLA/search")
    fun search(@Query("q")productDescription:String):Call<SearchResult>

    @GET("items/{id}")
    fun searchForId(@Path("id")id:String):Call<InfoItemId>

    @GET("items/{id}/descriptions")
    fun searchDescription(@Path("id")id:String):Call<ArrayList<DescriptionProduct>>
}
