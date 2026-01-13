package com.example.pro

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var api: ApiService
    private lateinit var loader: FrameLayout

    // ------------------- CONTROL DE VERSION -------------------
    private var versionValida = false

    companion object {
        private const val APP_VERSION = "1.0.25"
        private const val APLICACION_IDC = 363
        private const val ROL_IDC = 48
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ------------------- VERIFICAR SESIÓN -------------------
        val prefs = getSharedPreferences("SESSION", MODE_PRIVATE)
        val logged = prefs.getBoolean("LOGGED", false)
        val token = prefs.getString("TOKEN", null)

        if (logged && !token.isNullOrEmpty()) {
            irAMain()
            return
        }

        setContentView(R.layout.activity_login)

        // ------------------- VIEWS -------------------
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        loader = findViewById(R.id.rootModalLoader)

        // ------------------- RETROFIT -------------------
        api = Retrofit.Builder()
            .baseUrl("https://aplicativos.ags.gob.mx/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        // ------------------- VALIDAR VERSION -------------------
        verificarVersion()

        // ------------------- LOGIN -------------------
        btnLogin.setOnClickListener {

            if (!versionValida) {
                Toast.makeText(
                    this,
                    "La aplicación requiere actualización",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            val usuario = etUsername.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            if (usuario.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            login(usuario, pass)
        }
    }

    // ------------------- MOSTRAR / OCULTAR LOADER -------------------
    private fun showLoader(show: Boolean) {
        loader.visibility = if (show) View.VISIBLE else View.GONE
    }

    // ------------------- VERIFICAR VERSION API -------------------
    private fun verificarVersion() {
        lifecycleScope.launch {
            showLoader(true)
            try {
                val response = api.getVersion(
                    version = APP_VERSION,
                    aplicaionIDC = APLICACION_IDC,
                    rolIdIDC = ROL_IDC
                )

                showLoader(false)

                if (response.isSuccessful) {
                    versionValida = response.body() ?: false

                    if (!versionValida) {
                        mostrarDialogoActualizacion()
                    }
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "No se pudo validar la versión",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } catch (e: Exception) {
                showLoader(false)
                Toast.makeText(
                    this@LoginActivity,
                    "Error al validar versión",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // ------------------- DIALOGO ACTUALIZACIÓN -------------------
    private fun mostrarDialogoActualizacion() {
        AlertDialog.Builder(this)
            .setTitle("Actualización requerida")
            .setMessage(
                "Tu versión de la aplicación está desactualizada.\n" +
                        "Por favor actualiza para continuar."
            )
            .setCancelable(false)
            .setPositiveButton("Cerrar") { _, _ ->
                finish()
            }
            .show()
    }

    // ------------------- LOGIN API -------------------
    private fun login(usuario: String, pass: String) {
        lifecycleScope.launch {
            showLoader(true)
            try {
                val request = LoginSistemaRequest(usuario, pass)
                val response = api.loginSistema(request)

                showLoader(false)

                if (response.isSuccessful) {
                    val body = response.body()

                    if (body?.cveRes == 1 && !body.token.isNullOrEmpty()) {

                        // ------------------- GUARDAR SESIÓN -------------------
                        getSharedPreferences("SESSION", MODE_PRIVATE)
                            .edit()
                            .putBoolean("LOGGED", true)
                            .putInt("USER_ID", body.perfilUsuario?.userId ?: 0)
                            .putString("NOMBRE", body.perfilUsuario?.nombre)
                            .putString("TOKEN", body.token)
                            .apply()

                        irAMain()

                    } else {
                        etPassword.text.clear()
                        Toast.makeText(
                            this@LoginActivity,
                            body?.descripcion ?: "Credenciales incorrectas",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    etPassword.text.clear()
                    Toast.makeText(
                        this@LoginActivity,
                        "Error del servidor",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } catch (e: Exception) {
                showLoader(false)
                etPassword.text.clear()
                Toast.makeText(
                    this@LoginActivity,
                    "Error de conexión",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // ------------------- IR A MAIN -------------------
    private fun irAMain() {
        val intent = Intent(this, MainActivity8::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
