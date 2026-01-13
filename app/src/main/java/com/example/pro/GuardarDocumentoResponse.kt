package com.example.pro

import com.google.gson.annotations.SerializedName

data class GuardarDocumentoResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String?,
    @SerializedName("resumen") val resumen: Resumen?,
    @SerializedName("documentos") val documentos: List<Documento>?
)

data class Resumen(
    @SerializedName("totalArchivos") val totalArchivos: Int,
    @SerializedName("archivosExitosos") val archivosExitosos: Int,
    @SerializedName("archivosErroneos") val archivosErroneos: Int,
    @SerializedName("telefono") val telefono: String?,
    @SerializedName("conversacionId") val conversacionId: Int?
)

data class Documento(
    @SerializedName("nombreArchivo") val nombreArchivo: String?,
    @SerializedName("exito") val exito: Boolean?,
    @SerializedName("mensaje") val mensaje: String?
)
