package com.example.pro

data class GoogleGeocodeResponse(
    val results: List<Result>,
    val status: String,
    val error_message: String? = null
)

data class Result(
    val formatted_address: String,
    val geometry: Geometry,
    val place_id: String,
    val types: List<String>? = null,
    val address_components: List<AddressComponent>? = null,
    val partial_match: Boolean? = null // ✅ agregado para evitar error
)

data class Geometry(
    val location: Location,
    val location_type: String,
    val viewport: Viewport? = null
)

data class Location(
    val lat: Double,
    val lng: Double
)

data class Viewport(
    val northeast: Location,
    val southwest: Location
)

data class AddressComponent(
    val long_name: String,
    val short_name: String,
    val types: List<String>
)
