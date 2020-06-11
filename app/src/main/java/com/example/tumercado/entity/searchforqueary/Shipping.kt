package com.example.tumercado.entity.searchforqueary

data class Shipping(
    val free_shipping: Boolean,
    val logistic_type: String,
    val mode: String,
    val store_pick_up: Boolean,
    val tags: List<Any>
)