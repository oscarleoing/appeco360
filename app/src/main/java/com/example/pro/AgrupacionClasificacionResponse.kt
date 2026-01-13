package com.example.pro

import com.google.gson.annotations.SerializedName

data class AgrupacionClasificacionResponse(
    @SerializedName("result") val result: List<AgrupacionClasificacion>?
)
