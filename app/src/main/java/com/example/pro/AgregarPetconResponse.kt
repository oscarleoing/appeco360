package com.example.pro

import com.google.gson.annotations.SerializedName

data class AgregarPetconResponseContainer(
    @SerializedName("guia") val guia: AgregarPetconResponse
)

data class AgregarPetconResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("folioGuia") val folioGuia: String?,
    @SerializedName("folioCiac") val folioCiac: String?,
    @SerializedName("fechaCreacion") val fechaCreacion: String?,  // ISO8601
    @SerializedName("fechaTermino") val fechaTermino: String?,
    @SerializedName("estatus") val estatus: String?,
    @SerializedName("origen") val origen: String?,
    @SerializedName("idc") val idc: Int?,
    @SerializedName("usuario") val usuario: String?,
    @SerializedName("idConversacion") val idConversacion: Int?,
    @SerializedName("ConversacionID") val conversacionID: String?
)
