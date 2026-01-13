package com.example.pro

import com.cursokotlin.routemapexample.RouteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    // Colonias
    @GET("wssiac/api/Fracc/CatalogoColonias")
    suspend fun getColonias(
        @Query("colonia") colonia: String
    ): Response<ZoneResponse>

    // Calles
    @GET("wssiac/api/Fracc/CatalogoCalles")
    suspend fun getCalles(
        @Query("calle") calle: String
    ): Response<CalleResponse>

    // Rutas
    @GET("/v2/directions/driving-car")
    suspend fun getRoute(
        @Query("api_key") key: String,
        @Query("start", encoded = true) start: String,
        @Query("end", encoded = true) end: String
    ): Response<RouteResponse>

    // --------- NUEVO: Nombres ---------
    @GET("wssiac/api/CatalogosEX/ObtenerCatalogosNombre")
    suspend fun getNombres(
        @Query("nombre") nombre: String
    ): Response<List<Nombre>>

    // --------- NUEVO: Apellidos ---------
    @GET("wssiac/api/CatalogosEX/ObtenerCatalogosApellido")
    suspend fun getApellidos(
        @Query("apellido") apellido: String
    ): Response<List<Apellido>>

    // Nueva API: Agregar Petición con Respuesta (POST)
    @POST("wssiac/api/Reportes/AgregarPetconRespApp")
    suspend fun agregarPeticion(
        @Body request: AgregarPetconRequest
    ): Response<AgregarPetconResponseContainer>

    // ===================== GOOGLE GEOCODING =====================
    @GET("geocode/json")
    suspend fun geocodeGoogle(
        @Query("address") address: String,
        @Query("key") apiKey: String
    ): Response<GoogleGeocodeResponse>

    // ===================== NUEVO: BITÁCORA POR TELÉFONO =====================

    @GET("wssiac/api/Reportes/ObtenerBitcora")
    suspend fun getBitacora(
        @Query("telefono") telefono: String
    ): Response<BitacoraApiResponse>


    // ===================== NUEVO: AGRUPACIONES =====================


    @GET("wssiac/api/Agrupaciones")
    suspend fun getAgrupaciones(): Response<List<Agrupacion>>

    @GET("wssiac/api/AgrupacionesClasificaciones/ObtenerAgrupaciones")
    suspend fun getAgrupacionesClasificaciones(
        @Query("idAgrupacion") idAgrupacion: Int
    ): Response<List<AgrupacionClasificacion>>




    @POST("wssiac/api/Reportes/GuardarDocumento")
    suspend fun guardarDocumentoNuevo(
        @Body request: GuardarDocumentoNuevoRequest
    ): Response<GuardarDocumentoResponse>


    // ===================== NUEVO: LOGIN SISTEMA =====================
    @POST("IDCWSprod/api/LoginSistema/LOGIN")
    suspend fun loginSistema(
        @Body request: LoginSistemaRequest
    ): Response<LoginSistemaResponse>


    // ===================== NUEVO: VALIDAR VERSIÓN =====================
    @GET("wssiac/api/Version/GetVersion")
    suspend fun getVersion(
        @Query("version") version: String,
        @Query("aplicaionIDC") aplicaionIDC: Int,
        @Query("rolIdIDC") rolIdIDC: Int
    ): Response<Boolean>

}
