package com.example.tumercado

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tumercado.adapters.AdapterOfItems
import com.example.tumercado.api.Api
import com.example.tumercado.entity.searchforqueary.Result
import com.example.tumercado.entity.searchforqueary.SearchResult
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val adapter = AdapterOfItems{onDetailsClick(it)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        itemList.layoutManager = LinearLayoutManager(this)
        itemList.adapter = adapter




          searchProduct?.setOnKeyListener((View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {

                search(searchProduct.text.toString())
                return@OnKeyListener true
            }
            false
        }))

    }

        fun search(productName: String) {

            Api().search(productName, object : Callback<SearchResult> {
                override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                    Snackbar.make(mainContainer, "Sin conexion busqueda", Snackbar.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<SearchResult>,
                    response: Response<SearchResult>
                ) {
                    if (response.isSuccessful) {

                        var resultado = response.body()
                        adapter.items = resultado?.results!!
                        adapter.notifyDataSetChanged()
                    }
                }

            })

        }
    private fun onDetailsClick(item: Result) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.ITEM_PARAM, Gson().toJson(item))
        startActivity(intent)
    }
    }

