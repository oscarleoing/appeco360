package com.cursokotlin.routemapexample

import com.google.gson.annotations.SerializedName

typealias RouteResponse = List<ColoniaResponse>

data class ColoniaResponse(
    @SerializedName("nomEditFra") val nomEditFra: String,
    @SerializedName("cveFra") val cveFra: String,
    @SerializedName("delFra") val delFra: String,
    @SerializedName("fraCvesta") val fraCvesta: Int?,
    @SerializedName("cpFra") val cpFra: String,
    @SerializedName("delNombre") val delNombre: String,
    @SerializedName("staDescripcion") val staDescripcion: String?,
    @SerializedName("nomFra") val nomFra: String
)
