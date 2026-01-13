package com.example.pro

import com.google.gson.annotations.SerializedName

data class GuardarDocumentoNuevoRequest(
    @SerializedName("telefono") val telefono: String,
    @SerializedName("idConversacion") val idConversacion: Int,
    @SerializedName("tipoBot") val tipoBot: String,
    @SerializedName("archivos") val archivos: List<Archivo>
)

data class Archivo(
    @SerializedName("base64Contenido") val base64Contenido: String,
    @SerializedName("nombreArchivo") val nombreArchivo: String,
    @SerializedName("tipo") val tipo: String
)
