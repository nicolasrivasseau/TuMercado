package com.example.tumercado

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.tumercado.api.Api
import com.example.tumercado.entity.descriptionproduct.DescriptionProduct
import com.example.tumercado.entity.searchforid.InfoItemId
import com.example.tumercado.entity.searchforqueary.Result
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates


class DetailActivity : AppCompatActivity() {

    private lateinit var item: Result
    private lateinit var itemDescription: InfoItemId
    private lateinit var productTextDescription: ArrayList<DescriptionProduct>
    private lateinit var id: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)





        if(intent.extras!!.containsKey(ITEM_PARAM)){
            item = Gson().fromJson(intent.extras!!.getString(ITEM_PARAM),
                Result::class.java)
            id=item.id
            Log.i(TAG,"Pasa por if")
        }
        searchForId(id)
        searchDescription(id)
        setItemValues()
    }

    fun searchForId(id:String){
        Api().searchForId(id,object : Callback<InfoItemId> {
            override fun onFailure(call: Call<InfoItemId>, t: Throwable) {
                Snackbar.make(mainContainer, "Sin conexion atributos", Snackbar.LENGTH_LONG).show()
            }


            override fun onResponse(
                call: Call<InfoItemId>,
                response: Response<InfoItemId>
            ) {
                if (response.isSuccessful) {
                    itemDescription = response.body()!!
                }
            }
        })


    }

    fun searchDescription(id:String){
        Log.i(TAG,"prueba 5")
        Api().searchDescription(id,object : Callback<ArrayList<DescriptionProduct>> {
            override fun onFailure(call: Call<ArrayList<DescriptionProduct>>, t: Throwable) {

                Snackbar.make(mainContainer, "Sin conexion descripcion", Snackbar.LENGTH_LONG).show()
                Log.i(TAG,"Pasa por onFailure")
            }

            override fun onResponse(
                call: Call<ArrayList<DescriptionProduct>>,
                response: Response<ArrayList<DescriptionProduct>>
            ) {
                if (response.isSuccessful) {
                    productTextDescription = response.body()!!
                }
                Log.i(TAG,"Pasa por onResponse")
            }
        })
        Log.i(TAG,"Termina funcion")


    }
    private fun setItemValues(){

        if(!this::item.isInitialized&&!this::itemDescription.isInitialized&&!this::productTextDescription.isInitialized) return

       /* quantityProduct.text= itemDescription.available_quantity.toString()
        titleProduct.text= itemDescription.title
        priceProduct.text= itemDescription.price.toString()*/
        descriptionProduct.text= productTextDescription[0].descriptionItem[0].toString()


        /*Picasso.get()
            .load(itemDescription.pictures[0].url.replace("http", "https").toString())
            .placeholder(R.drawable.loading_spinner)
            .into(imageDetailsProduct)*/
    }
    private val TAG = "Instanciar descripcion"

        companion object{
            val ITEM_PARAM="ITEM_PARAM"
        }
}


