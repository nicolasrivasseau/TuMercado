package com.example.tumercado.entity.searchforid

data class SaleTerm(
    val id: String,
    val name: String,
    val value_id: Any,
    val value_name: String,
    val value_struct: ValueStructX,
    val values: List<ValueX>
)