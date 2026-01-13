package com.example.pro

import com.google.gson.annotations.SerializedName

// API devuelve lista de colonias
typealias ZoneResponse = List<Colonia>

data class Colonia(
    @SerializedName("nomEditFra") val nomEditFra: String,
    @SerializedName("cveFra") val cveFra: String,
    @SerializedName("delFra") val delFra: String,
    @SerializedName("fraCvesta") val fraCvesta: Int?,
    @SerializedName("cpFra") val cpFra: String,
    @SerializedName("delNombre") val delNombre: String,
    @SerializedName("staDescripcion") val staDescripcion: String?,
    @SerializedName("nomFra") val nomFra: String,
    @SerializedName("cpFFra") val cpFFra: String? // opcional
)

// API devuelve lista de calles
typealias CalleResponse = List<Calle>

data class Calle(
    @SerializedName("cveVia") val cveVia: String,
    @SerializedName("desVia") val desVia: String?,
    @SerializedName("nomVia") val nomVia: String,
    @SerializedName("oriVia") val oriVia: String?,
    @SerializedName("tipVia") val tipVia: Int?,
    @SerializedName("carVia") val carVia: String?,
    @SerializedName("coorVia") val coorVia: String?,
    @SerializedName("geoVia") val geoVia: String?,
    @SerializedName("nomAdi") val nomAdi: String?,
    @SerializedName("nomEditVia") val nomEditVia: String,
    @SerializedName("anterior") val anterior: String?,
    @SerializedName("historico") val historico: String?,
    @SerializedName("cveFra") val cveFra: String?,
    @SerializedName("fraccionamiento") val fraccionamiento: String?,
    @SerializedName("aplicaDes") val aplicaDes: String?,
    @SerializedName("aplicaOri") val aplicaOri: String?,
    @SerializedName("aplicaTipo") val aplicaTipo: String?
)

