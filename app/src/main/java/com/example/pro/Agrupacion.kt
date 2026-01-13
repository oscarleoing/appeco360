package com.example.pro

data class Agrupacion(
    val idAgrupacion: Int,
    val agrupacion: String,
    val menuMarkitos: Int,
    val fechaCreacion: String,
    val inactivo: Boolean,
    val ordenMenuMarkitos: Int,
    val agrupacionClasificacions: List<Any> = emptyList()
)
