package com.example.tumercado.entity.searchforqueary

data class Paging(
    val limit: Int,
    val offset: Int,
    val primary_results: Int,
    val total: Int
)