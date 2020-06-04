package com.example.tumercado

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tumercado.adapters.AdapterOfItems
import com.example.tumercado.api.Api
import com.example.tumercado.entity.SearchResult
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val adapter = AdapterOfItems()


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
                    Snackbar.make(mainContainer, "Sin conexion", Snackbar.LENGTH_LONG).show()
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
    }

