package com.example.pro

data class LoginSistemaResponse(
    val descripcion: String?,
    val cveRes: Int?,
    val perfilUsuario: PerfilUsuario?,
    val token: String?,                       // ✅ STRING
    val rolesUsuarioLista: List<RolUsuario>?  // ✅ ARRAY REAL
)

data class PerfilUsuario(
    val userId: Int?,
    val nombre: String?
)

data class RolUsuario(
    val nombreRol: String?,
    val modulosUsuarioDetallesList: List<Any>?
)
