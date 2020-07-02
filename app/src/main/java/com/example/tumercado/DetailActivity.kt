package com.example.tumercado

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tumercado.adapters.AdapterOfImage
import com.example.tumercado.api.Api
import com.example.tumercado.entity.descriptionproduct.DescriptionItem
import com.example.tumercado.entity.searchforid.InfoItemId
import com.example.tumercado.entity.searchforqueary.Result
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailActivity : AppCompatActivity() {
    private val adapterImage = AdapterOfImage()

    private lateinit var item: Result
    private lateinit var itemDescription: InfoItemId
    private var productTextDescription = ArrayList<DescriptionItem>()
    private lateinit var id: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        imageProductRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        imageProductRecycler.adapter = adapterImage


        btn_back.setOnClickListener {
            onBackPressed()
        }

        if (intent.extras!!.containsKey(ITEM_PARAM)) {
            item = Gson().fromJson(
                intent.extras!!.getString(ITEM_PARAM),
                Result::class.java
            )
            id = item.id
        }


        searchForId(id)
        searchDescription(id)


    }

    private fun isConnected(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo

        return activeNetwork?.isConnectedOrConnecting == true
    }


    private fun searchForId(id: String) {
        var loading = Snackbar.make(mainDetails, R.string.loading, Snackbar.LENGTH_INDEFINITE)
        if (isConnected()) {
            loading.show()
            Api().searchForId(id, object : Callback<InfoItemId> {
                override fun onFailure(call: Call<InfoItemId>, t: Throwable) {
                    loading.dismiss()
                    Snackbar.make(mainDetails, R.string.unknown_error, Snackbar.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<InfoItemId>,
                    response: Response<InfoItemId>
                ) {
                    if (codes(response.code())) {
                        loading.dismiss()
                        var resultado = response.body()
                        itemDescription = resultado!!
                        if (!resultado.pictures.isEmpty()) {
                            adapterImage.image = resultado.pictures
                            adapterImage.notifyDataSetChanged()
                        }
                        setItemValues()
                    }
                }
            })
        } else {
            loading.dismiss()
            Snackbar.make(mainDetails, R.string.no_internet, Snackbar.LENGTH_LONG).show()
        }


    }

    private fun searchDescription(id: String) {
        if (isConnected()) {
            Api().searchDescription(id, object : Callback<ArrayList<DescriptionItem>> {
                override fun onFailure(call: Call<ArrayList<DescriptionItem>>, t: Throwable) {
                    Snackbar.make(mainDetails, R.string.unknown_error, Snackbar.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<ArrayList<DescriptionItem>>,
                    response: Response<ArrayList<DescriptionItem>>
                ) {
                    if (codes(response.code())) {
                        productTextDescription = response.body()!!
                        setDescription()
                    }
                }
            })
        }


    }

    private fun codes(codes: Int): Boolean {
        when (codes) {
            in 200..299 -> {
                return true
            }
            404 -> Toast.makeText(
                this@DetailActivity,
                R.string.resource_not_found,
                Toast.LENGTH_LONG
            )
                .show()
            400 -> Toast.makeText(
                this@DetailActivity,
                R.string.bad_request,
                Toast.LENGTH_LONG
            )
                .show()
            in 500..599 -> Toast.makeText(
                this@DetailActivity,
                R.string.server_error,
                Toast.LENGTH_LONG
            )
                .show()
            else -> Toast.makeText(
                this@DetailActivity,
                R.string.unknown_error,
                Toast.LENGTH_LONG
            )
                .show()
        }
        return false
    }


    private fun setDescription() {
        descriptionProduct.text = productTextDescription[0].plain_text
    }


    private fun setItemValues() {

        if (!this::item.isInitialized) return

        quantityProduct.text = itemDescription.available_quantity.toString()
        titleProduct.text = itemDescription.title
        priceProduct.text = "$ ${itemDescription.price.toString()}"

    }

    companion object {
            val ITEM_PARAM="ITEM_PARAM"
        }
}


