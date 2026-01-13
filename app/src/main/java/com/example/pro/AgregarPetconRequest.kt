package com.example.pro

import com.google.gson.annotations.SerializedName

data class AgregarPetconRequest(
    @SerializedName("folio") val folio: String?,
    @SerializedName("apaterno") val apaterno: String?,
    @SerializedName("amaterno") val amaterno: String?,
    @SerializedName("nombre") val nombre: String?,
    @SerializedName("sexo") val sexo: String?,
    @SerializedName("cveFrac") val cveFrac: String?,
    @SerializedName("cveCalle") val cveCalle: String?,
    @SerializedName("desCalle") val desCalle: String?,
    @SerializedName("desFracc") val desFracc: String?,
    @SerializedName("numExt") val numExt: String?,
    @SerializedName("numInt") val numInt: String?,
    @SerializedName("telefono") val telefono: String?,
    @SerializedName("cveAds") val cveAds: String?,
    @SerializedName("cveProcedencia") val cveProcedencia: String?,
    @SerializedName("cveCap") val cveCap: String?,
    @SerializedName("cvePet") val cvePet: Int?,

    // 🔥 NUEVOS CAMPOS
    @SerializedName("idAgrupacion") val idAgrupacion: Int?,
    @SerializedName("idClasificacion") val idClasificacion: Int?,

    @SerializedName("observaciones") val observaciones: String?,
    @SerializedName("jerarquiaGestion") val jerarquiaGestion: String?,
    @SerializedName("admin") val admin: String?,
    @SerializedName("gpoPeticion") val gpoPeticion: String?,
    @SerializedName("gpoProcedencia") val gpoProcedencia: String?,
    @SerializedName("latitud") val latitud: Double?,
    @SerializedName("longitud") val longitud: Double?,
    @SerializedName("conversacionID") val conversacionID: String?
)
