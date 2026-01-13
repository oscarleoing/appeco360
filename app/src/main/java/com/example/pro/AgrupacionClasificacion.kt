package com.example.pro

import com.google.gson.annotations.SerializedName

data class AgrupacionClasificacion(
    @SerializedName("idAgrupacionClasificacion") val idAgrupacionClasificacion: Int?,
    @SerializedName("idAgrupacion") val idAgrupacion: Int?,
    @SerializedName("idClasificacion") val idClasificacion: Int?,
    @SerializedName("fechaCreacion") val fechaCreacion: String?,
    @SerializedName("inactivo") val inactivo: Boolean?,
    @SerializedName("idAgrupacionNavigation") val idAgrupacionNavigation: AgrupacionNavigation?,
    @SerializedName("idClasificacionNavigation") val idClasificacionNavigation: ClasificacionNavigation?,
    @SerializedName("usuarioAgrupacionClasificacions") val usuarioAgrupacionClasificacions: List<Any>?
)

data class AgrupacionNavigation(
    @SerializedName("idAgrupacion") val idAgrupacion: Int?,
    @SerializedName("agrupacion") val agrupacion: String?,
    @SerializedName("menuMarkitos") val menuMarkitos: Int?,
    @SerializedName("fechaCreacion") val fechaCreacion: String?,
    @SerializedName("inactivo") val inactivo: Boolean?,
    @SerializedName("agrupacionClasificacions") val agrupacionClasificacions: List<Any?>?
)

data class ClasificacionNavigation(
    @SerializedName("idClasificacion") val idClasificacion: Int?,
    @SerializedName("clasificacion") val clasificacion: String?,
    @SerializedName("ordenMenuMarkitos") val ordenMenuMarkitos: Int?,
    @SerializedName("claveAdscripcion") val claveAdscripcion: String?,
    @SerializedName("clavePeticion") val clavePeticion: Int?,
    @SerializedName("fechaCreacion") val fechaCreacion: String?,
    @SerializedName("inactivo") val inactivo: Boolean?,
    @SerializedName("cvePetGpo") val cvePetGpo: Int?,
    @SerializedName("desPetGpo") val desPetGpo: String?,
    @SerializedName("icono") val icono: String?,
    @SerializedName("agrupacionClasificacion") val agrupacionClasificacion: Any?
)
