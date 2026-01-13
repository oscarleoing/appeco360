package com.example.pro

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query



interface GoogleGeocodeService {

    // 🔹 Geocoding simple: búsqueda por dirección
    @GET("geocode/json")
    suspend fun geocode(
        @Query("address") address: String,
        @Query("key") apiKey: String
    ): GoogleGeocodeResponse

    // 🔹 Geocoding con componentes para resultados más precisos
    @GET("geocode/json")
    suspend fun geocodeWithComponents(
        @Query("address") address: String,
        @Query("components") components: String, // ej. "postal_code:2000|country:MX"
        @Query("key") apiKey: String
    ): GoogleGeocodeResponse
}
