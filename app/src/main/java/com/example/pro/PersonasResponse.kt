package com.example.pro

// --- Respuesta de Nombres ---

data class Nombre(
    val idNombre: Int,
    val nombre1: String,
    val fechaCreacion: String,
    val estatus: String
)

// --- Respuesta de Apellidos ---
data class Apellido(
    val idApellido: Int,
    val apellido1: String,
    val fechaCreacion: String,
    val estatus: String
)
