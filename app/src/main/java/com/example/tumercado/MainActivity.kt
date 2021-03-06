package com.example.tumercado


import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.recyclerview.widget.GridLayoutManager
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
    private val adapter = AdapterOfItems { onDetailsClick(it) }
    private var currentSearch: SearchResult? = null
    private var currentSearchTerm: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTheme(R.style.AppTheme)
        setSupportActionBar(findViewById(R.id.toolBar))
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            itemList.layoutManager = GridLayoutManager(this, 2)
        } else {
            itemList.layoutManager = GridLayoutManager(this, 3)
        }
        itemList.adapter = adapter


        btn_search.setOnClickListener { searchBar() }



        searchProduct?.setOnKeyListener((View.OnKeyListener { v, keyCode, event ->

            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                hideInput(v)
                search(searchProduct.text.toString())
                return@OnKeyListener true
            }

            false
        }))

    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (currentSearch != null) {
            outState.putString(CURRENT_SEARCH_KEY, Gson().toJson(currentSearch))
        }
        outState.putString(CURRENT_SEARCH_TERM, currentSearchTerm)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.containsKey(CURRENT_SEARCH_KEY)) {
            val currentSearchJson = savedInstanceState.getString(CURRENT_SEARCH_KEY)
            currentSearch = Gson().fromJson(currentSearchJson, SearchResult::class.java)
            if (currentSearch != null) {
                setItemValues(currentSearch!!)
            }
        }
        currentSearchTerm = savedInstanceState.getString(CURRENT_SEARCH_TERM, "")
        searchProduct.setText(currentSearchTerm)
    }

    private fun isConnected(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo

        return activeNetwork?.isConnectedOrConnecting == true
    }

    private fun search(productName: String) {
        var loading = Snackbar.make(mainContainer, R.string.loading, Snackbar.LENGTH_INDEFINITE)

        if (isConnected()) {
            loading.show()
            if (productName != "") {
                Api().search(productName, object : Callback<SearchResult> {
                    override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                        loading.dismiss()
                        Snackbar.make(mainContainer, R.string.unknown_error, Snackbar.LENGTH_LONG)
                            .show()
                    }

                    override fun onResponse(
                        call: Call<SearchResult>,
                        response: Response<SearchResult>
                    ) {
                        when (response.code()) {
                            in 200..299 -> {
                                if (response.body()?.results?.isEmpty()!!) {
                                    loading.dismiss()
                                    Toast.makeText(
                                        this@MainActivity,
                                        R.string.no_se_encontro_resultado,
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    loading.dismiss()
                                    var resultado = response.body()
                                    adapter.itemsList = resultado?.results!!
                                    currentSearch = response.body()!!
                                    adapter.notifyDataSetChanged()
                                }
                            }
                            404 -> {
                                loading.dismiss()
                                Toast.makeText(
                                    this@MainActivity,
                                    R.string.resource_not_found,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            400 -> {
                                loading.dismiss()
                                Toast.makeText(
                                    this@MainActivity,
                                    R.string.bad_request,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            in 500..599 -> {
                                loading.dismiss()
                                Toast.makeText(
                                    this@MainActivity,
                                    R.string.server_error,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            else -> {
                                loading.dismiss()
                                Toast.makeText(
                                    this@MainActivity,
                                    R.string.unknown_error,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }

                })
            }
        } else {
            loading.dismiss()
            Snackbar.make(mainContainer, R.string.no_internet, Snackbar.LENGTH_LONG)
                .show()
        }
    }


    private fun setItemValues(body: SearchResult) {
        if (!body.results.isEmpty()) {
            adapter.itemsList = body.results
        } else {
            adapter.itemsList = ArrayList()

            Toast.makeText(
                this@MainActivity,
                R.string.not_found,
                Toast.LENGTH_LONG
            )
                .show()
        }

        adapter.notifyDataSetChanged()
    }

    private fun hideInput(v: View) {
        val inputMethod: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethod.hideSoftInputFromWindow(v.applicationWindowToken, 0)
    }

    private fun searchBar() {
        if (searchProduct.isGone) {
            val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.show)
            searchProduct.startAnimation(animation)
            searchProduct.visibility = VISIBLE

        } else {
            search(searchProduct.text.toString())
            hideInput(btn_search)
        }
    }

    private fun onDetailsClick(item: Result) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.ITEM_PARAM, Gson().toJson(item))
        startActivity(intent)
    }


    val TAG = MainActivity::class.simpleName
        val CURRENT_SEARCH_KEY = "CURRENT_SEARCH_KEY"
        val CURRENT_SEARCH_TERM = "CURRENT_SEARCH_TERM"
    }



