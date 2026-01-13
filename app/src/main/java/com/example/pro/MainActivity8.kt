package com.example.pro

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer

import android.media.ThumbnailUtils
import android.location.Geocoder

import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat

import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

import java.text.Normalizer

import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator

import android.widget.*


import androidx.lifecycle.lifecycleScope

import com.google.android.gms.maps.model.*

import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest

import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import android.graphics.Color
import android.graphics.Rect
import android.location.Location
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputFilter
import android.text.InputType
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.pro.databinding.ActivityMain8Binding
import com.example.pro.databinding.LayoutModalGrabacionBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient


import android.widget.*
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.TypeFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


import android.widget.*

import androidx.lifecycle.lifecycleScope


import android.widget.*

import androidx.lifecycle.lifecycleScope


import android.widget.*

import kotlinx.coroutines.*

import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query
import android.util.Base64
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.FileOutputStream

import androidx.core.widget.addTextChangedListener

import com.google.android.material.checkbox.MaterialCheckBox


import android.graphics.Typeface
import android.os.Build
import android.provider.Settings
import android.text.style.AbsoluteSizeSpan
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo

import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView

import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson
import kotlin.text.trim

import com.google.android.flexbox.FlexboxLayout
import com.google.android.flexbox.FlexboxLayout.LayoutParams

class MainActivity8 : AppCompatActivity(), OnMapReadyCallback, OnCategoriaSeleccionadaListener {

    data class ReporteVistaPrevia(
        val categoria: String,
        val subcategoria: String,
        val direccion: String,
        val nombre: String,
        val apellidoPaterno: String,
        val apellidoMaterno: String,
        val telefono: String,
        val descripcion: String,
        val imagenes: List<String>,
        val videos: List<String>,
        val audios: List<String>,
        val pdfs: List<String>,
        val gpoPeticion: String?,
        val cvePet: Int?,
        val cveAds: String?,
        val cveFrac: String?,
        val cveCalle: String?,
        val desCalle: String?,
        val desFracc: String?,
        val numExt: String?
    )

    data class BitacoraItem(
        val folio: String?,
        val nombre: String?,
        val peticion: String?,
        val fechaPeticion: String?,
        val staPeticion: String?,
        val staRes: String?,
        val telefono: String?,
        val obsPet: String?,
        val obsResp: String?
    )

    // ======================= VIEWS =======================
    private lateinit var etTelefono: EditText
    private lateinit var btnAmarillo: ImageButton
    private lateinit var rootModalTablaBitacora: FrameLayout
    private lateinit var contenedorFilasBitacora: LinearLayout
    private lateinit var btnCerrarModalBitacora: Button


    // 🔹 Referencias del modal flotante
    private lateinit var modalBitacora: FrameLayout
    private lateinit var tableIncidente: TableLayout
    private lateinit var btnCerrarModalIncidente: Button
    private lateinit var btnCerrarX2: ImageView

    private lateinit var txtFolio: TextView
    private lateinit var txtNombre: TextView
    private lateinit var txtPeticion: TextView
    private lateinit var txtFechaPeticion: TextView
    private lateinit var txtStaPet: TextView
    private lateinit var txtStaRes: TextView
    private lateinit var txtTelefono: TextView
    private lateinit var txtObsPet: TextView
    private lateinit var txtObsResp: TextView

    private var loader: FrameLayout? = null


    var gpoPeticion: String? = null
    var cvePet: Int? = null
    var cveAds: String? = null

    private lateinit var binding: ActivityMain8Binding
    private lateinit var grabacionBinding: LayoutModalGrabacionBinding



    // ----- MAPA -----
    private lateinit var map: GoogleMap

    private var selectedLatLng: LatLng? = null
    private var marker: Marker? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // ----- CATEGORÍAS -----
    private var categoriaSeleccionadaNombre: String? = null
    private var subCategoriaSeleccionadaNombre: String? = null

     var categoriaSeleccionadaNombreBitacora: String? = null
     var subCategoriaSeleccionadaNombreBitacora: String? = null
    private var categoriaSeleccionadaColorSuperior: Int = R.color.white
    private var categoriaSeleccionadaColorInferior: Int = R.color.white

    private var categoriaSeleccionadaColorSuperiorBitacora: Int = R.color.white
    private var subCategoriaSeleccionadaColorSuperior: Int = R.color.white
    private var subCategoriaSeleccionadaColorInferior: Int = R.color.white
    private var categoriaSeleccionadaIcono: Int = android.R.drawable.ic_menu_info_details
    var categoriaSeleccionadaIconoBitacora: Int = android.R.drawable.ic_menu_info_details


    //  private var subcategoriaSeleccionadaIcono2: Int = android.R.drawable.ic_menu_info_details

    // Variables adicionales de categorías
    private lateinit var topButtons: List<LinearLayout>
    private lateinit var scrollsSubcategorias: List<HorizontalScrollView>

    // ----- MULTIMEDIA -----
    private val imagesList = mutableListOf<Bitmap>()
    private val imagePaths = mutableListOf<String>()
    private var videoPath: String? = null
    private val audioPaths = mutableListOf<String>()
    private val pdfPaths = mutableListOf<String>()

    private var currentPhotoPath: String? = null
    private var recorder: MediaRecorder? = null
    private var isRecording = false
    private val handler = Handler()
    private var segundosGrabacion = 0
    private var archivoAudioTemporal: File? = null

    // ----- COLONIAS -----
    private lateinit var searchText: AutoCompleteTextView
    private lateinit var searchAdapter: ArrayAdapter<String>
    private var coloniasList: List<Colonia> = emptyList()
    private val markers = mutableListOf<Marker>()
    private lateinit var placesClient: PlacesClient

    // ----- NOMBRES Y APELLIDOS -----
    private lateinit var adapterNombres: ArrayAdapter<String>
    private lateinit var adapterApellidosPaterno: ArrayAdapter<String>
    private lateinit var adapterApellidosMaterno: ArrayAdapter<String>
    private var nombresList: List<Nombre> = emptyList()
    private var apellidosList: List<Apellido> = emptyList()

    // ----- MODAL -----
    private lateinit var modalContainer: FrameLayout
    private lateinit var tvCategoriaVista: TextView
    private lateinit var tvSubcategoriaVista: TextView
    private lateinit var tvDireccionVista: TextView
    private lateinit var tvNombreVista: TextView
    private lateinit var tvApellidosVista: TextView

    private lateinit var tvNombreCompleVista: TextView
    private lateinit var tvTelefonoVista: TextView
    private lateinit var tvDescripcionVista: TextView
    private lateinit var layoutArchivosVista: LinearLayout
    private lateinit var btnCerrarX: ImageView
    private lateinit var btnEnviarVistaPrevia: Button
    private lateinit var viewColorCategoria: View
    private lateinit var iconCategoriaVista: ImageView

    private lateinit var iconSubcategoriaVista: ImageView

    // 🔹 Nuevo: Dirección seleccionada (de la tarjeta)
    private lateinit var tvDireccion2: TextView


    // ------------------- VARIABLES GLOBALES -------------------
    private var folio: String? = ""
    private var apaterno: String? = null
    private var amaterno: String? = null
    private var nombre: String? = null
    private var sexo: String? = null
    private var cveFrac: String? = null
    private var cveCalle: String? = null
    private var desCalle: String? = null
    private var desFracc: String? = null
    private var numExt: String? = null
    private var numInt: String? = null
    private var telefono: String? = null

    private var cveProcedencia: String? = null
    private var cveCap: String? = null

    private var observaciones: String? = null
    private var jerarquiaGestion: String? = null
    private var admin: String? = null

    private var gpoProcedencia: String? = null
    private var latitud: Double? = 0.0
    private var longitud: Double? = 0.0
    private var conversacionID: String? = null

    ////
    // ---------------------- VARIABLES ----------------------
    private lateinit var apiService: ApiService

    private var selectedColonia: Colonia? = null
    private var selectedCalle: Calle? = null

    private var callesList: List<Calle> = emptyList()

    private var selectedWordLength: Int = 0
    private var selectedWordLengthCalle: Int = 0

    // ---------------------- VARIABLES COLONIA ----------------------
    private var selectedColoniaCveFra: String? = null
    private var selectedColoniaNomFra: String? = null
    private var selectedColoniaCpFFra: String? = null
    private var selectedColoniaNomEditFra: String? = null
    private var selectedColoniaLatLng: LatLng? = null

    // ---------------------- VARIABLES CALLE ----------------------
    private var selectedCalleCveVia: String? = null
    private var selectedCalleNomVia: String? = null
    private var selectedCalleNomEditVia: String? = null
    private var selectedCalleCveFra: String? = null
    private var selectedCalleFraccionamiento: String? = null
    private var selectedCalleLatLng: LatLng? = null
    private var selectedCalleNumero: String? = null // agregado correctamente


    private lateinit var layoutBurbujas: LinearLayout
    private lateinit var burbujaCategoria: LinearLayout
    private lateinit var burbujaSubcategoria: LinearLayout
    private lateinit var imgIconoCategoria: ImageView
    private lateinit var txtCategoria: TextView
    private lateinit var imgIconoSubcategoria: ImageView
    private lateinit var txtSubcategoria: TextView

    private var subCategoriaSeleccionadaIcono: Int = android.R.drawable.ic_menu_info_details
    var subCategoriaSeleccionadaIconoBitacora: Int = android.R.drawable.ic_menu_info_details



    private lateinit var viewColorCategoria2: View

    ////3//
    object VersionConfig {
        const val APP_VERSION = "1.0.25"
        const val APLICACION_IDC = 363
        const val ROL_IDC = 48
    }




    private lateinit var viewColorCategoria3: View

    private lateinit var iconCategoriaVista3: ImageView


    private lateinit var iconSubcategoriaVista3: ImageView

    private lateinit var tvCategoriaVista3: TextView
    private lateinit var tvSeparadorSub3: TextView

    private lateinit var tvSubcategoriaVista3: TextView
    /////
    private var esAnonimo = false


    private var isMapaVisible = false

    // ---------------------- VARIABLES NÚMERO ----------------------
    private var numeroCalleJob: Job? = null

    /////
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private const val TAG = "MainActivity8"
    }

    // ------------------- ON CREATE -------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain8Binding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setSupportActionBar(binding.toolbar)
        // ------------------- MODAL -------------------
        modalContainer = binding.root.findViewById(R.id.rootModalContainer)
        tvCategoriaVista = binding.root.findViewById(R.id.tvCategoriaVista)
        tvSubcategoriaVista = binding.root.findViewById(R.id.tvSubcategoriaVista)
        tvDireccionVista = binding.root.findViewById(R.id.tvDireccionVista)
        tvNombreVista = binding.root.findViewById(R.id.tvNombreVista)
        tvApellidosVista = binding.root.findViewById(R.id.tvApellidosVista)
        tvNombreCompleVista= binding.root.findViewById(R.id.tvNombreCompleVista)
        tvTelefonoVista = binding.root.findViewById(R.id.tvTelefonoVista)
        tvDescripcionVista = binding.root.findViewById(R.id.tvDescripcionVista)
        layoutArchivosVista = binding.root.findViewById(R.id.layoutArchivosVista)
        btnCerrarX = binding.root.findViewById(R.id.btnCerrarX)
        btnEnviarVistaPrevia = binding.root.findViewById(R.id.btnEnviarVistaPrevia)
        viewColorCategoria = binding.root.findViewById(R.id.viewColorCategoria)
        iconCategoriaVista = binding.root.findViewById(R.id.iconCategoriaVista)
        iconSubcategoriaVista= binding.root.findViewById(R.id.iconSubcategoriaVista)

        layoutBurbujas = findViewById(R.id.layoutBurbujas)
        burbujaCategoria = findViewById(R.id.burbujaCategoria)
        burbujaSubcategoria = findViewById(R.id.burbujaSubcategoria)
        imgIconoCategoria = findViewById(R.id.imgIconoCategoria)
        txtCategoria = findViewById(R.id.txtCategoria)
        imgIconoSubcategoria = findViewById(R.id.imgIconoSubcategoria)
        txtSubcategoria = findViewById(R.id.txtSubcategoria)
        viewColorCategoria2 = findViewById(R.id.viewColorCategoria2)




        binding.cbAnonimo.setOnClickListener {


            // 🔄 Toggle
            esAnonimo = !esAnonimo

            if (esAnonimo) {
                // 🔘 PRENDIDO
                binding.cbAnonimo.backgroundTintList =
                    ContextCompat.getColorStateList(
                        this,
                        R.color.coloranonimopre
                    )

                binding.cbAnonimo.setColorFilter(
                    ContextCompat.getColor(this, R.color.black)
                )

            } else {
                // ⚫ APAGADO
                binding.cbAnonimo.backgroundTintList =
                    ContextCompat.getColorStateList(
                        this,
                        R.color.coloranonimoapa
                    )

                binding.cbAnonimo.setColorFilter(
                    ContextCompat.getColor(this, R.color.white)
                )
            }

            // 🔄 Aplicar lógica de anónimo
            setEstadoAnonimo(esAnonimo)
        }



        loader = findViewById(R.id.rootModalLoader)

////////3   ///
        viewColorCategoria3 = findViewById(R.id.viewColorCategoria3)
        iconCategoriaVista3 = binding.root.findViewById(R.id.iconCategoriaVista3)
        iconSubcategoriaVista3= binding.root.findViewById(R.id.iconSubcategoriaVista3)
        tvCategoriaVista3 = binding.root.findViewById(R.id.tvCategoriaVista3)
        tvSubcategoriaVista3 = binding.root.findViewById(R.id.tvSubcategoriaVista3)

        tvSeparadorSub3 = binding.root.findViewById(R.id.tvSeparadorSub3)

        viewColorCategoria3.visibility = View.GONE
        iconCategoriaVista3.visibility = View.GONE
        iconSubcategoriaVista3.visibility = View.GONE
        tvCategoriaVista3.visibility = View.GONE
        tvSubcategoriaVista3.visibility = View.GONE

/////////
       // pestaniaSeleccion.visibility = View.GONE
       // layoutCategoriaColor3.visibility = View.GONE

// Ocultar todo al iniciar
        layoutBurbujas.visibility = View.GONE
        burbujaCategoria.visibility = View.GONE
        burbujaSubcategoria.visibility = View.GONE

// Versión 35
        binding.btnTipoMapa.setOnClickListener {
            toggleMapStyle()
        }





        // ------------------- TOOLBAR + MENU (3 PUNTOS) -------------------

        // ---------------------- INICIALIZAR VIEWS ----------------------
        etTelefono = findViewById(R.id.etTelefono)
        btnAmarillo = findViewById(R.id.btnAmarillo)
        rootModalTablaBitacora = findViewById(R.id.rootModalTablaBitacora)

        btnCerrarModalBitacora = findViewById(R.id.btnCerrarModalBitacora)
// ======================= CONFIGURAR MODAL DE BITÁCORA =======================
        val rootModal = findViewById<FrameLayout>(R.id.rootModalTablaBitacora)
        val btnCerrar = findViewById<Button>(R.id.btnCerrarModalBitacora)
        val btnCerrarIcono = findViewById<ImageView>(R.id.btnCerrarModalTabla)
        val btnBuscar = findViewById<ImageView>(R.id.btnBuscar)
        val etTelefono = findViewById<EditText>(R.id.etTelefono)

// 🔹 Ocultar modal al iniciar
        rootModal.visibility = View.GONE

// 🔹 Cerrar modal con botón principal
        btnCerrar.setOnClickListener { rootModal.visibility = View.GONE }

// 🔹 Cerrar modal con icono "X"
        btnCerrarIcono.setOnClickListener { rootModal.visibility = View.GONE }

// 🔹 Cerrar modal tocando fuera del contenido
        rootModal.setOnClickListener { view ->
            if (view.id == R.id.rootModalTablaBitacora) rootModal.visibility = View.GONE
        }

// ======================= BOTÓN DE BÚSQUEDA =======================
        btnBuscar.setOnClickListener {
            val telefono = etTelefono.text.toString().trim()
            if (telefono.isEmpty()) {
                Toast.makeText(this, "Ingrese un número de teléfono", Toast.LENGTH_SHORT).show()
            } else {
                mostrarBitacoraPorTelefono(telefono)
            }
        }

// ======================= BOTÓN AMARILLO =======================
        val btnAmarillo = findViewById<ImageButton>(R.id.btnAmarillo)

// 🔹 Ocultar botón al iniciar
        btnAmarillo.visibility = View.GONE

// 🔹 Acción al tocar el botón amarillo → abre modal y muestra la bitácora
        btnAmarillo.setOnClickListener {
            val telefono = etTelefono.text.toString().trim()
            if (telefono.length == 10) {
                rootModal.visibility = View.VISIBLE
                mostrarBitacoraPorTelefono(telefono)
            } else {
                Toast.makeText(this, "Ingrese un número válido", Toast.LENGTH_SHORT).show()
            }
        }

// ======================= ESCUCHAR CAMBIOS DEL TELÉFONO =======================
        etTelefono.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val telefono = s.toString().replace("-", "").trim()

                // Si tiene 10 dígitos → verificar si tiene bitácora
                if (telefono.length == 10) {
                    verificarBitacora(telefono, btnAmarillo)
                } else {
                    btnAmarillo.visibility = View.GONE
                }
            }
        })


        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validarMostrarAnonimo()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.etTelefono2.addTextChangedListener(watcher)
        binding.etNombre.addTextChangedListener(watcher)
        binding.etApellidoPaterno.addTextChangedListener(watcher)
        binding.etApellidoMaterno.addTextChangedListener(watcher)


        tvDireccion2 = binding.root.findViewById(R.id.tvDireccion2)

        btnCerrarX.setOnClickListener { modalContainer.visibility = View.GONE }

        btnEnviarVistaPrevia.setOnClickListener {
            Toast.makeText(this, "Reporte enviado ✅", Toast.LENGTH_SHORT).show()
            modalContainer.visibility = View.GONE
            limpiarFormulario()
        }

        // ------------------- FRAGMENTO -------------------
        val fragment = MainFragment4()
        fragment.listener = this
        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedorMain4, fragment)
            .commit()

        grabacionBinding = LayoutModalGrabacionBinding.inflate(layoutInflater)
        grabacionBinding.modalBackground.setBackgroundColor(0x88000000.toInt())
        grabacionBinding.modalContent.setBackgroundResource(R.drawable.bg_modal)
        grabacionBinding.modalContent.elevation = 12f
        mostrarLayoutGrabandoAudio(false)

        try {
            if (!Places.isInitialized()) {
                Places.initialize(applicationContext, getString(R.string.google_maps_key))
            }
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

            // Inicializar mapa
            val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)

            // ---- Botones de acción ----
            binding.btnEnviar.setOnClickListener {
                if (selectedLatLng != null) ocultarMapaYBotones()
                else Toast.makeText(this, "Selecciona una ubicación en el mapa", Toast.LENGTH_SHORT)
                    .show()
            }
            binding.btnUbicacion.setOnClickListener {

                verificarYSolicitarPermisos()
                moveToCurrentLocation() }

            binding.btnIrCasa.setOnClickListener {

                verificarYSolicitarPermisos()
                moveToCurrentLocation()
                binding.tvEtiquetaDireccion.visibility = View.GONE

            }

            binding.btnMostrarMapa.setOnClickListener { mostrarMapaYBotones() }

            // ---- Multimedia ----
            binding.layoutFoto.setOnClickListener { mostrarDialogoSeleccion() }
            grabacionBinding.btnStartRecording.setOnClickListener {
                if (isRecording) detenerGrabacion() else pedirPermisoYGrabar()
            }
            grabacionBinding.btnCancelarGrabacion.setOnClickListener { cancelarGrabacion() }
            grabacionBinding.modalBackground.setOnClickListener {
                if (!isRecording) mostrarLayoutGrabandoAudio(false)
            }

            apiService =
                getRetrofit("https://aplicativos.ags.gob.mx/").create(ApiService::class.java)


            /////

            verificarVersion()
            ////

            binding.btnSubmit.setOnClickListener {
                // Inicializar los AutoCompleteTextView
                val etNombre = findViewById<AutoCompleteTextView>(R.id.etNombre)
                val etApellidoPaterno = findViewById<AutoCompleteTextView>(R.id.etApellidoPaterno)
                val etApellidoMaterno = findViewById<AutoCompleteTextView>(R.id.etApellidoMaterno)


                // ❌ Si algún campo tiene error → NO PERMITIR CONTINUAR
                if (etNombre.error != null ||
                    etApellidoPaterno.error != null ||
                    etApellidoMaterno.error != null) {

                    Toast.makeText(
                        this,
                        "Corrige los campos marcados antes de continuar",
                        Toast.LENGTH_LONG
                    ).show()

                    return@setOnClickListener
                }

                // Solo llamamos a la función, no mostramos el modal aquí
                binding.btnEnviarVistaPrevia.visibility = View.VISIBLE
                generarReporteConSubcategoria(
                    categoriaSeleccionada = categoriaSeleccionadaNombre,
                    subCategoriaSeleccionada = subCategoriaSeleccionadaNombre,
                    direccion = tvDireccion2.text.toString(),
                    nombre = binding.etNombre.text.toString().trim(),
                    apellidoPaterno = binding.etApellidoPaterno.text.toString().trim(),
                    apellidoMaterno = binding.etApellidoMaterno.text.toString().trim(),
                    telefono = binding.etTelefono.text.toString(), //
                    descripcion = binding.etDescripcion.text.toString(),
                    imagenes = imagePaths,
                    videos = listOfNotNull(videoPath),
                    audios = audioPaths,
                    pdfs = pdfPaths
                )
            }

            // ---------------------- BOTON PARA MOSTRAR BITACORA ----------------------
            btnAmarillo.setOnClickListener {
                val telefono2 = etTelefono.text.toString().trim()
                if (telefono2.isNotEmpty()) {
                    mostrarBitacoraPorTelefono(telefono2)
                } else {
                    Toast.makeText(this, "Ingresa un teléfono", Toast.LENGTH_SHORT).show()
                }
            }



            // ---------------------- BOTON CERRAR MODAL ----------------------
            btnCerrarModalBitacora.setOnClickListener {
                rootModalTablaBitacora.visibility = View.GONE
            }

            binding.btnCerrarModalBitacora.setOnClickListener {
                binding.rootModalTablaBitacora.visibility = View.GONE
            }

            binding.btnCerrarModalTabla.setOnClickListener {
                binding.rootModalTablaBitacora.visibility = View.GONE
            }

            binding.btnEnviarVistaPrevia.setOnClickListener {
                // Llamamos a la función que envía la petición a la API
                enviarPeticionYMostrarGuia()
            }

            binding.btnCerrarX.setOnClickListener {
                binding.rootModalContainer.visibility = View.GONE
            }

            // Inicializar los AutoCompleteTextView
            val etNombre = findViewById<AutoCompleteTextView>(R.id.etNombre)
            val etApellidoPaterno = findViewById<AutoCompleteTextView>(R.id.etApellidoPaterno)
            val etApellidoMaterno = findViewById<AutoCompleteTextView>(R.id.etApellidoMaterno)


            // Aplicar mayúsculas automáticas
            setAllCaps(etNombre)
            setAllCaps(etApellidoPaterno)
            setAllCaps(etApellidoMaterno)
            verificarYSolicitarPermisos()
            actualizarGaleria()
            // Asignar validación a cada campo
            soloLetrasConEnie(etNombre)
            soloLetrasConEnie(etApellidoPaterno)
            soloLetrasConEnie(etApellidoMaterno)



        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    // --- Solo entra si el mapa está visible ---
                    if (isMapaVisible) {

                        if (selectedLatLng != null) {
                            // ✔ Ya seleccionó una ubicación
                            ocultarMapaYBotones()
                        } else {
                            // ❌ No ha seleccionado nada
                            Toast.makeText(
                                this@MainActivity8,
                                "Selecciona una ubicación en el mapa",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        // --- Comportamiento normal del botón back ---
                        isEnabled = false
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
            })

            // ---- Ocultar teclado al perder foco ----
            hideKeyboardOnFocusLost(
                binding.etNombre,
                binding.etTelefono,
                binding.etTelefono2,
                binding.etLada,
                binding.etDescripcion,
                binding.etSearchCalle,
                binding.etNumeroCalle,
                binding.etNombre,
                binding.etApellidoPaterno,
                binding.etApellidoMaterno,
                binding.etSearchColonia
            )




            binding.root.setOnTouchListener { _, _ ->
                currentFocus?.let { view -> hideKeyboard(view) }
                false
            }

            val focusListener = View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    actualizarVisibilidadResultados()
                }
            }





            binding.etSearchCalle.onFocusChangeListener = focusListener
            binding.etSearchColonia.onFocusChangeListener = focusListener
            binding.etNumeroCalle.onFocusChangeListener = focusListener

            binding.root.viewTreeObserver.addOnGlobalFocusChangeListener { _, _ ->
                actualizarVisibilidadResultados()
            }


            binding.etSearchCalle.setOnItemClickListener { _, _, _, _ ->
                hideKeyboard(binding.etSearchCalle)
            }
            binding.etSearchColonia.setOnItemClickListener { _, _, _, _ ->
                hideKeyboard(binding.etSearchColonia)
            }

            // Versión 3
            binding.etNumeroCalle.filters = arrayOf(
                InputFilter.LengthFilter(6),
                InputFilter { source, _, _, _, _, _ ->
                    if (source.matches(Regex("[0-9]*"))) source else ""
                }
            )



            binding.etNombre.setOnItemClickListener { _, _, _, _ ->
                hideKeyboard(binding.etNombre)
            }
            binding.etApellidoPaterno.setOnItemClickListener { _, _, _, _ ->
                hideKeyboard(binding.etApellidoPaterno)
            }

            binding.etApellidoMaterno.setOnItemClickListener { _, _, _, _ ->
                hideKeyboard(binding.etApellidoMaterno)
            }

            // ======================= ACTUALIZAR TELEFONO UNIFICADO =======================
            fun actualizarTelefonoFinal() {
                val lada = binding.etLada.text.toString()
                val tel = binding.etTelefono2.text.toString()
                binding.etTelefono.setText("$lada-$tel")
            }



            binding.etLada.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s == null) return

                    // Limitar a máximo 3 caracteres
                    if (s.length > 3) {
                        s.delete(3, s.length)
                    }

                    // Cuando llega a 3 caracteres, pasar el focus
                    if (s.length == 3) {
                        binding.etTelefono2.requestFocus()
                    }

                    actualizarTelefonoFinal()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })


            // 🔹 Hint SIN negritas y con tamaño reducido
            val hint = SpannableString("LADA")
            hint.setSpan(AbsoluteSizeSpan(15, true), 0, hint.length, 0)
            hint.setSpan(StyleSpan(Typeface.NORMAL), 0, hint.length, 0)   // 👈 Forzar hint normal
            binding.etLada.hint = hint

// 🔹 Texto del EditText SÍ en negritas
            binding.etLada.setTypeface(binding.etLada.typeface, Typeface.BOLD)


// 🔹 Teléfono 2 también en negritas como lo tenías
            binding.etTelefono2.setTypeface(binding.etTelefono2.typeface, Typeface.BOLD)

            binding.etTelefono2.addTextChangedListener(object : TextWatcher {






            private var isFormatting = false
                private var prev = ""

                override fun afterTextChanged(s: Editable?) {
                    if (isFormatting) return
                    if (s == null) return

                    val txt = s.toString()
                    if (txt == prev) return

                    isFormatting = true

                    // Solo números
                    val digits = txt.replace(Regex("[^\\d]"), "")

                    // Máximo 7 dígitos
                    val limited = digits.take(7)

                    // Formato 3-4
                    val formatted = when {
                        limited.length <= 3 -> limited
                        else -> limited.substring(0, 3) + "-" + limited.substring(3)
                    }

                    prev = formatted
                    s.replace(0, s.length, formatted)

                    isFormatting = false

                    actualizarTelefonoFinal()
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })


            ocultarMapaYBotones()

            // ---- Google Places API ----
            if (!Places.isInitialized()) {
                Places.initialize(applicationContext, getString(R.string.google_maps_key))
            }
            placesClient = Places.createClient(this)
            searchText = binding.etSearchCalle
            //setupAutoCompleteSearch()
            searchText = binding.etSearchColonia
            //setupAutoCompleteSearch()

           // setupAutoCompleteNombre()
            //setupAutoCompleteApellidoPaterno()
            //setupAutoCompleteApellidoMaterno()

            setupAutoCompleteColonia()
            setupAutoCompleteCalle()
            setupNumeroCalleBusqueda()


// ------------------- CLICK DEL BOTÓN ENVIAR -------------------
            binding.btnEnviarVistaPrevia.setOnClickListener {
                enviarPeticionYMostrarGuia()
            }


        } catch (e: Exception) {
            Log.e(TAG, "Error en onCreate: ${e.message}", e)
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }



    }


    ///
    fun soloLetrasConEnie(editText: AutoCompleteTextView) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val texto = s.toString()

                // Regex que permite letras + acentos + espacios + Ñ/ñ
                val regex = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$".toRegex()

                // ❌ Si contiene caracteres inválidos, marcar error
                if (texto.isNotEmpty() && !regex.matches(texto)) {
                    editText.error = "Solo letras y espacios (se permite Ñ y acentos)"
                    return
                }

                // ✔ Si el contenido es válido, limpiar el error
                editText.error = null
            }
        })
    }

    private fun actualizarVisibilidadResultados() {

        when {
            binding.etNumeroCalle.hasFocus() -> {
                // Número → ocultar TODO
                binding.scrollResultadosCalle.visibility = View.GONE
                binding.scrollResultadosColonia.visibility = View.GONE
            }

            binding.etSearchCalle.hasFocus() -> {
                // Calle → ocultar colonia
                binding.scrollResultadosColonia.visibility = View.GONE
            }

            binding.etSearchColonia.hasFocus() -> {
                // Colonia → ocultar calle
                binding.scrollResultadosCalle.visibility = View.GONE
            }
        }
    }


    // Función para aplicar mayúsculas a un EditText/AutoCompleteTextView
    fun setAllCaps(editText: AutoCompleteTextView) {
        val filter = InputFilter { source, _, _, _, _, _ ->
            source.toString().uppercase()
        }
        editText.filters = arrayOf(filter)
    }

    private fun mostrarNumeroGuiaEnModal(numeroGuia: String) {
        val modalRoot = findViewById<FrameLayout>(R.id.rootModalContainer)
        val layoutGuia = findViewById<LinearLayout>(R.id.layoutGuia)
        val tvNumeroGuia = findViewById<TextView>(R.id.tvNumeroGuia)
        val btnCerrarGuia = findViewById<Button>(R.id.btnCerrarGuia)
        val btnCerrarX = findViewById<ImageView>(R.id.btnCerrarX)
        val btnNuevaReporte = findViewById<Button>(R.id.btnNuevaReporte)

        // 🔹 Ocultar botón X
        btnCerrarX.visibility = View.GONE


// 🔹 Ocultar los demás campos
        val camposOcultables = listOf(
          //  R.id.layoutArchivosVista,
          //  R.id.tvDireccionVista,
          //  R.id.tvDescripcionVista,
            R.id.btnEnviarVistaPrevia
        )


        // Guardar visibilidades originales
        val visibilidadesOriginales = mutableMapOf<Int, Int>()
        camposOcultables.forEach { id ->
            findViewById<View>(id)?.let {
                visibilidadesOriginales[id] = it.visibility
                it.visibility = View.GONE
            }
        }

        // Mostrar el layout de la guía con animación
        layoutGuia.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate().alpha(1f).setDuration(400).start()
        }

        // Mostrar número de guía
        tvNumeroGuia.apply {
            text = numeroGuia.ifEmpty { "Sin número de guía" }
            visibility = View.VISIBLE
        }


        // Mostrar y configurar botón de cerrar (CERRAR GUÍA)
        btnCerrarGuia.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                if (layoutGuia.visibility == View.VISIBLE) {
                   // limpiarFormulario()
                    limpiarRegistro()
                    limpiarModal(modalRoot, layoutGuia, visibilidadesOriginales)

                }
            }
        }
        btnNuevaReporte.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                if (layoutGuia.visibility == View.VISIBLE) {

                    // 🔹 Limpiar formulario general
                    limpiarFormulario()
                    limpiarModal(modalRoot, layoutGuia, visibilidadesOriginales)

                    // 🔹 Resetear TextView de dirección principal
                    val tvDireccion2 = binding.root.findViewById<TextView>(R.id.tvDireccion2)
                    tvDireccion2.text = "Selecciona una ubicación"

                    // 🔹 Resetear solo el TextView tvEtiquetaDireccion
                    val tvEtiquetaDireccion = binding.root.findViewById<TextView>(R.id.tvEtiquetaDireccion)
                    tvEtiquetaDireccion.text = "Dirección seleccionada"  // restaurar texto original
                    binding.tvEtiquetaDireccion.visibility = View.GONE

                    // 🔹 Reiniciar fragmento
                    val fragment = MainFragment4()
                    fragment.listener = this@MainActivity8
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.contenedorMain4, fragment)
                        .commit()
                }
            }
        }





        // 🔹 Listener del botón X (CERRAR MODAL COMPLETO)
       /// btnCerrarX?.setOnClickListener {
       //     if (modalRoot.visibility == View.VISIBLE) {
          //      limpiarFormulario()
         //       limpiarModal(modalRoot, layoutGuia, visibilidadesOriginales)

          //  }
      //  }

        // Mostrar el modal si estaba oculto
        modalRoot.visibility = View.VISIBLE
    }

    /**
     * Limpia completamente el contenido y estado del modal.
     */
    private fun limpiarModal(
        modalRoot: FrameLayout,
        layoutGuia: LinearLayout,
        visibilidadesOriginales: Map<Int, Int>
    ) {
        // Ocultar layout de la guía
        layoutGuia.visibility = View.GONE

        // Restaurar visibilidades originales
        visibilidadesOriginales.forEach { (id, visibility) ->
            findViewById<View>(id)?.visibility = visibility
        }

        // Animación suave de cierre
        modalRoot.animate()
            .alpha(0f)
            .setDuration(300)
            .withEndAction {
                modalRoot.visibility = View.GONE
                modalRoot.alpha = 1f
            }
            .start()
    }

    private var idAgrupacionSelect: Int? = null
    private var idClasificacionSelect: Int? = null


    private fun generarReporteConSubcategoria(
        categoriaSeleccionada: String?,
        subCategoriaSeleccionada: String?,
        direccion: String,
        nombre: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        telefono: String,
        descripcion: String,
        imagenes: List<String>,
        videos: List<String>,
        audios: List<String>,
        pdfs: List<String>
    ) {

        // 🔹 Normalizador
        fun normalizarTexto(texto: String?): String {
            return Normalizer.normalize(texto ?: "", Normalizer.Form.NFD)
                .replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
                .uppercase()
                .trim()
        }

        // ✔ VALIDACIÓN INICIAL DE CATEGORÍA Y SUBCATEGORÍA (antes del map)
        if (categoriaSeleccionada.isNullOrEmpty()) {
            Toast.makeText(this, "Campo vacío: Categoría", Toast.LENGTH_SHORT).show()
            return
        }

        if (subCategoriaSeleccionada.isNullOrEmpty()) {
            Toast.makeText(this, "Campo vacío: Subcategoría", Toast.LENGTH_SHORT).show()
            return
        }
// Si el nombre es "ANONIMO", llenar automáticamente Nombre y Apellido Paterno
        val nombreFinal = if (nombre.equals("ANÓNIMO", ignoreCase = true)) "ANÓNIMO" else nombre
        val apellidoPaternoFinal = if (nombre.equals("ANÓNIMO", ignoreCase = true)) "ANÓNIMO" else apellidoPaterno

// ✔ Validación de campos obligatorios adicionales
        val camposObligatorios = mapOf(
            "Dirección" to if (direccion == "Selecciona una ubicación") "" else direccion,
            "Teléfono" to telefono,
            "Nombre" to nombreFinal,
            "Apellido Paterno" to apellidoPaternoFinal,
            "Descripción" to descripcion
        )


        val telefonoLimpio = telefono
            .replace("-", "")
            .trim()

        if (!telefonoLimpio.matches(Regex("^\\d{7,}$"))) {
            Toast.makeText(this, "El teléfono debe tener mínimo 7 dígitos numéricos", Toast.LENGTH_SHORT).show()
            binding.etTelefono2.requestFocus()
            return
        }


        val campoFaltante = camposObligatorios.entries.find { it.value.isNullOrEmpty() }
        if (campoFaltante != null) {
            Toast.makeText(this, "Campo vacío: ${campoFaltante.key}", Toast.LENGTH_SHORT).show()
            return
        }




        // ✔ 2. Mapeo de categorías
        val categoriasMap = mapOf(
            "ALUMBRADO" to 1,
            "CALLES" to 2,
            "MIAA" to 3,
            "APOYOS" to 4,
            "PARQUES Y JARDINES" to 5,
            "LIMPIA" to 6,
            "VIGILANCIA" to 7,
            "CONVIVENCIA" to 8,
            "CIUDAD" to 9
        )


        btnCerrarX.visibility = View.VISIBLE
        binding.layoutProgresoUpload.visibility = View.GONE

        val categoriaNormalizada = normalizarTexto(categoriaSeleccionada)
        val subCategoriaNormalizada = normalizarTexto(subCategoriaSeleccionada)

        // ❗ Validar si la categoría existe en el map
        if (!categoriasMap.containsKey(categoriaNormalizada)) {
            Toast.makeText(this, "La categoría no coincide con ninguna agrupación válida", Toast.LENGTH_SHORT).show()
            return
        }

        val idAgrupacionSeleccionado = categoriasMap[categoriaNormalizada] ?: 0

        // ✔ 3. Llamada al API
        lifecycleScope.launch {
            try {
                val response = apiService.getAgrupacionesClasificaciones(idAgrupacionSeleccionado)

                if (response.isSuccessful) {

                    val listaSubCategorias = response.body() ?: emptyList()

                    // 🔹 Comparación normalizada sin acentos
                    val subCategoria = listaSubCategorias.find {
                        normalizarTexto(it.idClasificacionNavigation?.clasificacion) == subCategoriaNormalizada
                    }

                    // ❗ SI NO EXISTE SUBCATEGORÍA → ALERTA
                    if (subCategoria == null) {
                        Toast.makeText(
                            this@MainActivity8,
                            "La subcategoría vacía",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@launch
                    }

                    // ✔ Asignar datos solo si existe la subcategoría
                    gpoPeticion = subCategoria.idClasificacionNavigation?.cvePetGpo.toString()
                    cvePet = subCategoria.idClasificacionNavigation?.clavePeticion
                    cveAds = subCategoria.idClasificacionNavigation?.claveAdscripcion
                    idAgrupacionSelect = subCategoria.idAgrupacion
                    idClasificacionSelect = subCategoria.idClasificacionNavigation?.idClasificacion

                    // 4. Crear objeto Reporte
                    val reporte = ReporteVistaPrevia(
                        categoria = categoriaSeleccionada ?: "Sin categoría",
                        subcategoria = subCategoriaSeleccionada ?: "Sin subcategoría",
                        direccion = direccion,
                        nombre = nombre,
                        apellidoPaterno = apellidoPaterno,
                        apellidoMaterno = apellidoMaterno,
                        telefono = telefono,
                        descripcion = descripcion,
                        imagenes = imagePaths.toList(),
                        videos = videoPaths.toList(),
                        audios = audioPaths.toList(),
                        pdfs = pdfPaths.toList(),
                        gpoPeticion = gpoPeticion,
                        cvePet = cvePet,
                        cveAds = cveAds,
                        cveFrac = selectedColoniaCveFra,
                        cveCalle = selectedCalleCveVia,
                        desCalle = selecteddesCalle,
                        desFracc = selecteddesFracc,
                        numExt = selectedCalleNumero?.trim(),
                    )

                    // 🔹 Imprimir JSON en consola
                    val jsonReporte = Gson().toJson(reporte)
                    Log.d("REPORTE_JSON", jsonReporte)

                    // 5. Mostrar vista previa
                    mostrarVistaPrevia(reporte)

                } else {
                    Log.e("API", "Error al obtener subcategorías: ${response.code()}")
                }

            } catch (e: Exception) {
                Log.e("API", "Excepción al llamar a la API", e)
            }
        }
    }


    private fun enviarPeticionYMostrarGuia() {

        val modalLoader = findViewById<FrameLayout>(R.id.rootModalLoader)

        try {
            // ===================== MOSTRAR LOADER =====================
            modalLoader.visibility = View.VISIBLE

            // ===================== VALIDAR CAMPOS =====================
            val nombre = binding.etNombre.text.toString().trim()
            val apaterno = binding.etApellidoPaterno.text.toString().trim()
            val amaterno = binding.etApellidoMaterno.text.toString().trim()
            val numExt = (selectedCalleNumero ?: "").trim()
            val telefono = binding.etTelefono.text.toString().replace("-", "")



            // ===================== SANITIZAR DATOS NULL =====================
            fun String?.orEmptyString() = this ?: ""
            fun Int?.orZero() = this ?: 0
            fun Double?.orZero() = this ?: 0.0

            // ===================== DATOS =====================
            val request = try {
                AgregarPetconRequest(
                    folio = "",
                    apaterno = apaterno.orEmptyString(),
                    amaterno = amaterno.orEmptyString(),
                    nombre = nombre.orEmptyString(),
                    sexo = "",
                    cveFrac = selectedColoniaCveFra?.toString() ?: "99997",
                    cveCalle = selectedCalleCveVia?.toString() ?: "99997",
                    desCalle = selecteddesCalle.orEmptyString()?:"NO ESPECIFICADO",
                    desFracc = selecteddesFracc.orEmptyString()?:"NO ESPECIFICADO",
                    numExt = selectedCalleNumero?.trim(),
                    numInt = "",
                    telefono = telefono.orEmptyString(),
                    cveAds = cveAds.orEmptyString(),
                    cveProcedencia = "100",
                    cveCap = "91",
                    cvePet = cvePet.orZero(),
                    idAgrupacion = idAgrupacionSelect.orZero(),
                    idClasificacion = idClasificacionSelect.orZero(),
                    observaciones = binding.etDescripcion.text.toString().orEmptyString(),
                    jerarquiaGestion = "0",
                    admin = "0",
                    gpoPeticion = gpoPeticion.orEmptyString(),
                    gpoProcedencia = "3",
                    latitud = selectedLatLng?.latitude.orZero(),
                    longitud = selectedLatLng?.longitude.orZero(),
                    conversacionID = "0"
                )
            } catch (e: Exception) {
                Log.e("❌ERROR_REQUEST", "No se pudo generar el request: ${e.message}")
                Toast.makeText(this, "Error al preparar datos", Toast.LENGTH_LONG).show()
                modalLoader.visibility = View.GONE
                return
            }

            val gson = GsonBuilder().setPrettyPrinting().create()
            Log.d("📦 JSON_ENVIADO", gson.toJson(request))

            // ===================== RETROFIT =====================
            val apiService = Retrofit.Builder()
                .baseUrl("https://aplicativos.ags.gob.mx/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)

            lifecycleScope.launch {
                try {
                    val response = apiService.agregarPeticion(request)

                    if (!response.isSuccessful) {
                        val errorMsg = response.errorBody()?.string() ?: "Error desconocido"
                        Log.e("⚠️ API_ERROR", "Código ${response.code()}: $errorMsg")
                        Toast.makeText(this@MainActivity8, "Error al enviar: $errorMsg", Toast.LENGTH_LONG).show()
                        modalLoader.visibility = View.GONE
                        return@launch
                    }

                    val resultado = response.body()
                    if (resultado == null) {
                        Toast.makeText(this@MainActivity8, "Respuesta vacía de la API", Toast.LENGTH_LONG).show()
                        modalLoader.visibility = View.GONE
                        return@launch
                    }

                    // ===================== FOLIO GUÍA =====================
                    val folioGuia = resultado.guia?.folioGuia ?: "SIN_FOLIO"
                    val folioFormateado = formatearFolioGuia(folioGuia)
                    mostrarNumeroGuiaEnModal(folioFormateado)

                    // ===================== ENVÍO DE ARCHIVOS =====================
                    val idConv = resultado.guia?.idConversacion ?: 0
                    val totalArchivos = imagePaths.size + videoPaths.size + audioPaths.size + pdfPaths.size

                    if (totalArchivos > 0) {
                        enviarArchivos(telefono, idConv, "Ciudadano")
                    } else {
                        Toast.makeText(this@MainActivity8, "Petición enviada correctamente", Toast.LENGTH_LONG).show()
                    }

                } catch (e: Exception) {
                    Log.e("❌EXCEPCION_API", "Error: ${e.message}", e)
                    Toast.makeText(this@MainActivity8, "Error al conectar: ${e.message}", Toast.LENGTH_LONG).show()

                } finally {
                    // 🔥 OCULTAR LOADER SIEMPRE
                    modalLoader.visibility = View.GONE
                }
            }

        } catch (e: Exception) {
            Log.e("❌ERROR_LOCAL", "Error general: ${e.message}", e)
            Toast.makeText(this, "Error inesperado: ${e.message}", Toast.LENGTH_LONG).show()
            modalLoader.visibility = View.GONE
        }
    }




///////

    // 🔥 LISTAS PARA CONTROLAR ITEMS (necesarias para tus layouts item_audio, item_video, etc.)
    private val items = mutableListOf<String>()
    private val itemsSeleccionados = mutableListOf<String>()
    private val itemsPreview = mutableListOf<String>()
    private val itemsAdjuntos = mutableListOf<String>()


    private fun limpiarMultimedia() {

        try {
            // 🔥 1. BORRAR ARCHIVOS FÍSICOS
            val todas = (imagePaths + videoPaths + audioPaths + pdfPaths)

            todas.forEach { path ->
                try {
                    val file = File(path)
                    if (file.exists()) file.delete()
                } catch (_: Exception) {}
            }

            // 🔥 2. LIMPIAR LISTAS MULTIMEDIA
            imagePaths.clear()
            videoPaths.clear()
            audioPaths.clear()
            pdfPaths.clear()
            imagesList.clear()

            // 🔥✨ 3. LIMPIAR ITEMS (para tus pantallas de items)
            items.clear()
            itemsSeleccionados.clear()
            itemsPreview.clear()
            itemsAdjuntos.clear()

            // 🔥 4. LIMPIAR VARIABLES GLOBALES
            currentPhotoPath = null
            videoPath = null
            archivoAudioTemporal = null

            imagePaths.forEachIndexed { i, path ->
                val itemView = layoutInflater.inflate(R.layout.item_imagen, binding.imageContainer, false)

                val imageView = itemView.findViewById<ImageView>(R.id.imageView)
                val btnEliminar = itemView.findViewById<ImageButton>(R.id.btnEliminarImagen)

                imageView.setImageBitmap(BitmapFactory.decodeFile(path))

                btnEliminar.setOnClickListener {
                    eliminarImagen(path, itemView)
                }

                binding.imageContainer.addView(itemView)
            }

            videoPaths.forEachIndexed { i, path ->
                val itemView = layoutInflater.inflate(R.layout.item_video, binding.imageContainer, false)

                val thumbnail = itemView.findViewById<ImageView>(R.id.videoThumbnail)
                val btnEliminar = itemView.findViewById<ImageButton>(R.id.btnEliminarVideo)

                val thumb = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND)
                thumbnail.setImageBitmap(thumb)

                btnEliminar.setOnClickListener {
                    eliminarVideo(path, itemView)
                }

                binding.imageContainer.addView(itemView)
            }






            // 🔥 5. LIMPIAR UI COMPLETA
            runOnUiThread {
                binding.imageContainer.removeAllViews()
                binding.audioContainer.removeAllViews()
                binding.pdfContainer.removeAllViews()

                binding.tvGalleryLabel.visibility = View.GONE
                binding.horizontalScrollView.visibility = View.GONE

                binding.tvAudioLabel.visibility = View.GONE
                binding.audioContainer.visibility = View.GONE

                binding.tvPdfLabel.visibility = View.GONE
                binding.pdfContainer.visibility = View.GONE

                actualizarGaleria() // refresca tu vista
            }

            Log.d("🧹 LIMPIAR_MULTIMEDIA", "Todo limpio correctamente")

        } catch (e: Exception) {
            Log.e("❌ ERROR_LIMPIEZA", "Error al limpiar multimedia: ${e.message}")
        }
    }


    /////

    private fun borrarArchivo(path: String?) {

        if (path == null) return

        try {
            val file = File(path)

            // 🔥 1. BORRAR ARCHIVO FÍSICO
            if (file.exists()) file.delete()

            // 🔥 2. QUITARLO DE TODAS LAS LISTAS
            imagePaths.remove(path)
            videoPaths.remove(path)
            audioPaths.remove(path)
            pdfPaths.remove(path)
            imagesList.clear()

            // 🔥✨ 3. LIMPIAR ITEMS DE PANTALLAS
            items.clear()
            itemsSeleccionados.clear()
            itemsPreview.clear()
            itemsAdjuntos.clear()

            // 🔥 4. RESETEAR VARIABLES SI COINCIDE
            if (archivoAudioTemporal?.path == path) archivoAudioTemporal = null
            if (currentPhotoPath == path) currentPhotoPath = null
            if (videoPath == path) videoPath = null


            // 🔥 5. LIMPIAR UI
            runOnUiThread {
                binding.imageContainer.removeAllViews()
                binding.audioContainer.removeAllViews()
                binding.pdfContainer.removeAllViews()

                binding.tvGalleryLabel.visibility = View.GONE
                binding.horizontalScrollView.visibility = View.GONE

                binding.tvAudioLabel.visibility = View.GONE
                binding.audioContainer.visibility = View.GONE

                binding.tvPdfLabel.visibility = View.GONE
                binding.pdfContainer.visibility = View.GONE

                actualizarGaleria()
            }

            Log.d("🗑 BORRADO", "Archivo eliminado: $path")

        } catch (e: Exception) {
            Log.e("❌ ERROR BORRAR", "Error al borrar archivo: ${e.message}")
        }
    }


    ////


    ///elimnar

    private fun eliminarImagen(path: String, view: View) {

        // Borrar archivo físico
        try {
            val file = File(path)
            if (file.exists()) file.delete()
        } catch (_: Exception) {}

        // Quitar de la lista
        imagePaths.remove(path)

        // Quitar vista del contenedor
        binding.imageContainer.removeView(view)

        // Si ya no quedan imágenes
        if (imagePaths.isEmpty() && videoPaths.isEmpty()) {
            binding.tvGalleryLabel.visibility = View.GONE
            binding.horizontalScrollView.visibility = View.GONE
        }

        actualizarGaleria()
    }



    private fun eliminarVideo(path: String, view: View) {
        try {
            val file = File(path)
            if (file.exists()) file.delete()
        } catch (_: Exception) {}

        // Eliminar de la lista
        videoPaths.remove(path)

        // Quitar vista del contenedor
        binding.imageContainer.removeView(view)

        // Ocultar labels si ya no hay contenido
        if (videoPaths.isEmpty() && imagePaths.isEmpty()) {
            binding.tvGalleryLabel.visibility = View.GONE
            binding.horizontalScrollView.visibility = View.GONE
        }
    }




    private fun eliminarAudio(path: String, view: View) {

        try {
            val file = File(path)
            if (file.exists()) file.delete()
        } catch (_: Exception) {}

        audioPaths.remove(path)

        binding.audioContainer.removeView(view)

        if (audioPaths.isEmpty()) {
            binding.tvAudioLabel.visibility = View.GONE
            binding.audioContainer.visibility = View.GONE
        }
    }

    private fun eliminarPdf(path: String, view: View) {

        try {
            val file = File(path)
            if (file.exists()) file.delete()
        } catch (_: Exception) {}

        pdfPaths.remove(path)

        binding.pdfContainer.removeView(view)

        if (pdfPaths.isEmpty()) {
            binding.tvPdfLabel.visibility = View.GONE
            binding.pdfContainer.visibility = View.GONE
        }
    }


    /////

    ////

    private fun formatearFolioGuia(folio: String?): String {
        if (folio.isNullOrBlank()) return ""

        val parte1 = folio.take(5)
        val parte2 = folio.drop(5).take(6)
        val parte3 = folio.drop(11).take(4)

        return listOf(parte1, parte2, parte3)
            .filter { it.isNotEmpty() }
            .joinToString("-")
    }


    ///


    // ------------------- MOSTRAR VISTA PREVIA -------------------
    private fun mostrarVistaPrevia(reporte: ReporteVistaPrevia) {
        modalContainer.visibility = View.VISIBLE

        tvCategoriaVista.text = " " + (categoriaSeleccionadaNombre ?: reporte.categoria)
        viewColorCategoria.setBackgroundColor(
            ContextCompat.getColor(
                this,
                categoriaSeleccionadaColorSuperior
            )
        )
        iconCategoriaVista.setImageResource(categoriaSeleccionadaIcono)
        tvSubcategoriaVista.text =
            " " + (subCategoriaSeleccionadaNombre ?: reporte.subcategoria)+"  "
        iconSubcategoriaVista.setImageResource(subCategoriaSeleccionadaIcono)
        tvDireccionVista.text = "" + reporte.direccion
        tvNombreVista.text = "Nombre: " + reporte.nombre
        tvApellidosVista.text = "Apellidos: ${reporte.apellidoPaterno} ${reporte.apellidoMaterno}"
        tvNombreCompleVista.text = "   ${reporte.nombre} ${reporte.apellidoPaterno} ${reporte.apellidoMaterno}"
        tvTelefonoVista.text = "   "+ reporte.telefono
        tvDescripcionVista.text = "   " + reporte.descripcion

        layoutArchivosVista.removeAllViews()
        reporte.imagenes.forEach { path ->
            val imageView = ImageView(this)
            imageView.setImageBitmap(BitmapFactory.decodeFile(path))
            imageView.layoutParams =
                LinearLayout.LayoutParams(150, 150).apply { setMargins(8, 8, 8, 8) }
            layoutArchivosVista.addView(imageView)
        }
        reporte.videos.forEach {
            val imageView = ImageView(this)
            imageView.setImageResource(android.R.drawable.ic_media_play)
            layoutArchivosVista.addView(imageView)
        }
        reporte.audios.forEach {
            val imageView = ImageView(this)
            imageView.setImageResource(android.R.drawable.ic_btn_speak_now)
            layoutArchivosVista.addView(imageView)
        }
        reporte.pdfs.forEach {
            val imageView = ImageView(this)
            imageView.setImageResource(android.R.drawable.ic_menu_save)
            layoutArchivosVista.addView(imageView)
        }
    }


    // ------------------- LIMPIAR FORMULARIO -------------------
    private fun limpiarFormulario() {
        binding.etNombre.text.clear()
        binding.etApellidoMaterno.text.clear()
        binding.etApellidoPaterno.text.clear()
        binding.etTelefono.text.clear()
        binding.etDescripcion.text.clear()
        binding.etSearchCalle.text.clear()
        binding.etSearchColonia.text.clear()
        binding.imageContainer.removeAllViews()
        binding.horizontalScrollView.visibility = View.GONE
        binding.audioContainer.removeAllViews()
        binding.tvAudioLabel.visibility = View.GONE
        binding.audioContainer.visibility = View.GONE
        binding.pdfContainer.removeAllViews()
        binding.tvPdfLabel.visibility = View.GONE
        binding.pdfContainer.removeAllViews()
        binding.etTelefono2.text.clear()
        limpiarMultimedia()
        viewColorCategoria3.visibility = View.GONE
        iconCategoriaVista3.visibility = View.GONE
        iconSubcategoriaVista3.visibility = View.GONE
        tvCategoriaVista3.visibility = View.GONE
        tvSubcategoriaVista3.visibility = View.GONE
        tvSeparadorSub3.visibility = View.GONE

        selectedCalleCveFraRespi= null
        selectedColoniaCveFraRespi= null


          salirDeAnonimo()



        binding.iconUbicacion.setImageResource(R.drawable.ic_location)

        // Estado original del XML
        binding.iconUbicacion.contentDescription = "Ubicación"
        binding.iconUbicacion.imageTintList =
            ContextCompat.getColorStateList(this, android.R.color.black)
    }



    private fun limpiarRegistro() {
        binding.etNombre.text.clear()
        binding.etApellidoMaterno.text.clear()
        binding.etApellidoPaterno.text.clear()
        binding.etTelefono.text.clear()
        binding.etDescripcion.text.clear()
        binding.etSearchCalle.text.clear()
        binding.etSearchColonia.text.clear()
        binding.imageContainer.removeAllViews()
        binding.horizontalScrollView.visibility = View.GONE
        binding.audioContainer.removeAllViews()
        binding.tvAudioLabel.visibility = View.GONE
        binding.audioContainer.visibility = View.GONE
        binding.pdfContainer.removeAllViews()
        binding.tvPdfLabel.visibility = View.GONE
        binding.pdfContainer.removeAllViews()
        binding.etTelefono2.text.clear()
        limpiarMultimedia()
        tvSeparadorSub3.visibility = View.GONE


    }

    private lateinit var pestaniaSeleccion: LinearLayout
    private lateinit var txtPestania: TextView



    // ------------------- LISTENER DE CATEGORÍAS -------------------
    override fun onCategoriaSeleccionada(categoria: CategoriaReporteUI) {
        categoriaSeleccionadaNombre = categoria.nombre
        categoriaSeleccionadaColorSuperior = categoria.colorSuperior
        categoriaSeleccionadaColorInferior = categoria.colorInferior
        categoriaSeleccionadaIcono = categoria.icono

        val colorSuperior = ContextCompat.getColor(this, categoria.colorSuperior)
        // val colorInferior = ContextCompat.getColor(this, categoria.colorInferior)
        // binding.tvEtiquetaDireccion.setBackgroundColor(colorSuperior)
        //  binding.franjaIcono.setBackgroundColor(colorInferior)


        tvCategoriaVista3.text = "" + "${categoria.nombre}"+""

        viewColorCategoria3.setBackgroundColor(
            ContextCompat.getColor(
                this,
                categoriaSeleccionadaColorSuperior
            )
        )
        iconCategoriaVista3.setImageResource(categoriaSeleccionadaIcono)
        viewColorCategoria3.visibility = View.VISIBLE
        iconCategoriaVista3.visibility = View.VISIBLE
        iconSubcategoriaVista3.visibility = View.GONE
        tvCategoriaVista3.visibility = View.VISIBLE
        tvSubcategoriaVista3.visibility = View.GONE

        subCategoriaSeleccionadaNombre = null

        tvSeparadorSub3.visibility = View.GONE

        binding.iconUbicacion.setImageResource(R.drawable.ic_location)

        // Estado original del XML
        binding.iconUbicacion.contentDescription = "Ubicación"
        binding.iconUbicacion.imageTintList =
            ContextCompat.getColorStateList(this, android.R.color.black)

    }

    override fun onSubCategoriaSeleccionada(subCategoria: SubCategoriaReporteUI) {

        // 🔹 Guardar selección
        subCategoriaSeleccionadaNombre = subCategoria.nombre
        subCategoriaSeleccionadaColorSuperior = subCategoria.colorSuperior
        subCategoriaSeleccionadaColorInferior = subCategoria.colorInferior
        subCategoriaSeleccionadaIcono = subCategoria.icono

        // 🔹 Texto visible
        tvSubcategoriaVista3.text = " ${subCategoria.nombre}"

        // 🔹 Ícono de la subcategoría (ya existente)
        iconSubcategoriaVista3.setImageResource(subCategoria.icono)
        iconSubcategoriaVista3.visibility = View.VISIBLE

        val iconoSegunSubcategoria =
            obtenerIconoPorSubcategoria(subCategoriaSeleccionadaNombre)


        binding.iconUbicacion.setImageResource(iconoSegunSubcategoria)
        binding.iconUbicacion.clearColorFilter()

        binding.iconUbicacion.imageTintList = null
        binding.iconUbicacion.clearColorFilter()

        // 🔹 Visibilidad
        tvSubcategoriaVista3.visibility = View.VISIBLE
        tvSeparadorSub3.visibility = View.VISIBLE
    }


    private fun obtenerIconoPorSubcategoria(nombre: String?): Int {
        return when (nombre) {

            // 🔹 ALUMBRADO
            "Luminarias" -> R.drawable.luminaria_puntero_r
            "Espacios Comunes" -> R.drawable.espacios_comunes_puntero_r
            "Mejoras" -> R.drawable.mejoras_puntero_r
            "Apagón" -> R.drawable.apagon_puntero_r
            "Arbotantes" -> R.drawable.arbotantes_puntero_r
            "Postes" -> R.drawable.postes_puntero_r

            // 🔹 CALLES
            "Baches" -> R.drawable.baches_puntero_r
            "Banquetas" -> R.drawable.banquetas_puntero_r
            "Topes" -> R.drawable.topespuntero
            "Rampas" -> R.drawable.rampas_puntero_r
            "Mantenimiento de Señalamientos" -> R.drawable.mantenimiento_de_senialamientos_puntero_r
            "Mantenimiento" -> R.drawable.mantenimiento_de_calles_puntero_r
            "Limpieza" -> R.drawable.limpieza_puntero_r
            "Escombro" -> R.drawable.escombros_puntero_r
            "Rehabilitación de Fachadas" -> R.drawable.rehabilitacion_de_fachadas_puntero_r

            // 🔹 MIAA
            "Drenaje" -> R.drawable.drenaje_puntero_r
            "Fugas de Agua" -> R.drawable.fuga_de_agua_puntero_r
            "Obra Inconclusa" -> R.drawable.obras_inconclusas_puntero_r
            "Falta de Agua" -> R.drawable.falta_de_agua_puntero_r
            "Alcantarillas" -> R.drawable.alcantarillas_puntero_r
            "Instalación" -> R.drawable.instalacion_puntero_r

            // 🔹 APOYOS
            "Económicos" -> R.drawable.recurso_3
            "Ortopédicos" -> R.drawable.ortopedicos_puntero_r
            "Pañales" -> R.drawable.paniales_puntero_r
            "Alimentos" -> R.drawable.alimentos_puntero_r
            "Asesoría" -> R.drawable.asesoria_puntero_r
            "Servicios Médicos" -> R.drawable.servicios_medicos_puntero_r

            // 🔹 PARQUES Y JARDINES
            "Árbol de tu Casa" -> R.drawable.arbol_de_tu_casa_puntero_r
            "Poda" -> R.drawable.poda_puntero_r
            "Áreas Verdes" -> R.drawable.areas_verdes_puntero_r
            "Rehabilitación" -> R.drawable.rehabilitacion_puntero_r
            "Desmalezado" -> R.drawable.desmalezado_puntero_r
            "Equipamiento" -> R.drawable.equipamiento_puntero_r
            "Camellones" -> R.drawable.camellones_puntero_r
            "Recolección" -> R.drawable.recoleccion_de_ramas_puntero_r

            // 🔹 LIMPIA
            "Muebles" -> R.drawable.muebles_puntero_r
            "Contenedores" -> R.drawable.contenedor_puntero_r
            "Basura" -> R.drawable.basura_puntero_r
            "Recolección de Residuos" -> R.drawable.recoleccion_puntero_r
            "Limpieza de Espacios" -> R.drawable.limpieza_areas_puntero_r

            // 🔹 VIGILANCIA
            "Vigilancia" -> R.drawable.vigilancia_puntero_r
            "Vehículos Abandonados" -> R.drawable.vehiculos_abandonados_puntero_r
            "Apoyo Vial" -> R.drawable.apoyo_vial_puntero_r
            "Mejoras Viales" -> R.drawable.mejoras_viales_puntero_r

            // 🔹 CONVIVENCIA
            "Ruido" -> R.drawable.ruido_puntero_r
            "Mercados" -> R.drawable.mercados_puntero_r
            "Permisos" -> R.drawable.permisos_puntero_r

            // 🔹 CIUDAD
            "Problemas Vecinales" -> R.drawable.problemas_vecinales_puntero_r
            "Obra" -> R.drawable.obras_puntero__puntero_r
            "Aclaración" -> R.drawable.aclaracion_puntero_r
            "Uso de Suelo" -> R.drawable.uso_de_suelo_puntero_r
            "Escombro Vía Pública" -> R.drawable.escombro_via_publica__puntero_r
            "Fraccionamiento" -> R.drawable.fraccionamiento_puntero_r
            "Fincas en riesgo" -> R.drawable.fincas_en_riesgo_puntero_r

            // 🔹 DEFAULT / RESET
            else -> R.drawable.ic_location
        }
    }


    private var  subnombre: String? = ""
////
    private fun hideKeyboard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun hideKeyboardOnFocusLost(vararg views: View) {
        views.forEach { view ->
            view.setOnFocusChangeListener { v, hasFocus -> if (!hasFocus) hideKeyboard(v) }
        }
    }


    // Versión 32

    private var isSatellite = false


    // ---------------- MAPA ----------------
    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap
        map.setInfoWindowAdapter(null)

        val initialPosition = LatLng(21.884635798282755, -102.28301879306879)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(initialPosition, 13f))
        // 🌍 MAPA INICIA CON TU ESTILO
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        setCustomMapStyle(map)

        map.setOnMapClickListener { latLng ->
            map.clear()

            obtenerDireccionDesglosada(latLng) { calle, numero, colonia, cp ->


                selectedColonia = null
                selectedColoniaCveFra = null
                selectedColoniaLatLng = null
                selecteddesFracc = null
                selectedCalleNumero = null
                selectedCalle = null
                selectedCalleCveVia = null
                selectedCalleNomVia = null
                selectedCalleNomEditVia = null
                selectedCalleCveFra = null
                selectedCalleFraccionamiento = null
                selectedCalleLatLng = null
                selecteddesCalle = null

                selectedCalleCveViaRepi = null
                restaurarAnchoAutoComplete()

                binding.etSearchCalle.text.clear()
                binding.etSearchColonia.text.clear()
                binding.etNumeroCalle.text.clear()
                binding.root.clearFocus()


                val titulo = if (calle != null) {
                    if (numero != null) "$calle $numero" else calle
                } else {
                    colonia ?: "Sin información"
                }

                // 👉 Evitar repetir colonia si ya está en el título
                val snippet =
                    if (titulo.contains(colonia ?: "", ignoreCase = true)) {
                        "CP: ${cp ?: "N/D"}"
                    } else {
                        "${colonia ?: "N/D"}"
                    }

                marker = map.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(titulo)
                        .snippet(snippet)
                )

                marker?.showInfoWindow()

                selectedLatLng = latLng
                updateDireccionBar(latLng)

                selectedColoniaCveFra = "99997"
                selectedCalleCveVia = "99997"

                selecteddesCalle = calle
                selecteddesFracc= colonia
                selectedCalleNumero = numero


            }
        }

        enableMyLocation()
    }
    fun setCustomMapStyle(map: GoogleMap) {
        try {
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this,
                    R.raw.map_style

                )
            )
            if (!success) {
                Log.e("MapsActivity", "Failed to apply map style.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("MapsActivity", "Map style not found.", e)
        }
    }

    // Versión 34
    fun toggleMapStyle() {

        if (!::map.isInitialized) return

        if (!isSatellite) {
            // 🛰️ CAMBIAR A SATÉLITE
            map.mapType = GoogleMap.MAP_TYPE_SATELLITE
            isSatellite = true
        } else {
            // 🌍 VOLVER A TU ESTILO
            map.mapType = GoogleMap.MAP_TYPE_NORMAL
            setCustomMapStyle(map)
            isSatellite = false
        }
    }

    private fun obtenerDireccionDesglosada(
        latLng: LatLng,
        callback: (String?, String?, String?, String?) -> Unit
    ) {
        val geocoder = Geocoder(this, Locale.getDefault())

        try {
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]

                val calle = address.thoroughfare                 // Calle
                val numero = address.subThoroughfare             // Número exterior
                val colonia = address.subLocality                // Colonia
                val cp = address.postalCode                      // CP

                callback(calle, numero, colonia, cp)
            } else {
                callback(null, null, null, null)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            callback(null, null, null, null)
        }
    }


    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun moveToCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                val currentLatLng = LatLng(it.latitude, it.longitude)

                val (calle, numero, colonia, cp, direccionCompleta) = obtenerDatosDireccion(currentLatLng)

// Crear o actualizar marker SIN repetición
                if (marker == null) {
                    marker = map.addMarker(
                        MarkerOptions()
                            .position(currentLatLng)
                            .title("$calle #$numero")   // SOLO esto arriba
                            .snippet("$colonia, CP $cp")             // SOLO colonia abajo
                    )
                } else {
                    marker?.position = currentLatLng
                    marker?.title = "$calle #$numero"
                    marker?.snippet = "$colonia, CP $cp"     // 🔥 Actualizado limpio
                }

                // Limpiar selección
                selectedColonia = null
                selectedColoniaCveFra = null
                selectedColoniaLatLng = null
                selecteddesFracc = null
                selectedCalleNumero = null
                selectedCalle = null
                selectedCalleCveVia = null
                selectedCalleNomVia = null
                selectedCalleNomEditVia = null
                selectedCalleCveFra = null
                selectedCalleFraccionamiento = null
                selectedCalleLatLng = null
                selecteddesCalle = null
                selectedCalleCveViaRepi = null

                restaurarAnchoAutoComplete()

                binding.etSearchCalle.text.clear()
                binding.etSearchColonia.text.clear()
                binding.etNumeroCalle.text.clear()
                binding.root.clearFocus()

                selectedColoniaCveFra = "99997"
                selectedCalleCveVia = "99997"

                selectedLatLng = currentLatLng

                selecteddesCalle = calle
                selecteddesFracc= colonia
                selectedCalleNumero = numero


                // --- 🔥 Obtener y actualizar dirección ---
                val direccionActual = updateDireccionBar(currentLatLng)


                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16f))


                marker?.showInfoWindow()   // <-- mostrar dirección actualizada

            } ?: run {
                Toast.makeText(this, "No se pudo obtener ubicación", Toast.LENGTH_SHORT).show()
            }
        }

    }




    data class Quintuple<A, B, C, D, E>(
        val first: A,
        val second: B,
        val third: C,
        val fourth: D,
        val fifth: E
    )


    private fun obtenerDatosDireccion(latLng: LatLng): Quintuple<String, String, String, String, String> {
        return try {
            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]

                val calle = address.thoroughfare ?: ""
                val numero = address.subThoroughfare ?: ""
                val colonia = address.subLocality ?: ""
                val cp = address.postalCode ?: ""
                val direccionCompleta = address.getAddressLine(0) ?: ""

                hideKeyboard(searchText)
                searchText.clearFocus()

                Quintuple(calle, numero, colonia, cp, direccionCompleta)
            } else {
                Quintuple("", "", "", "", "Dirección no disponible")
            }

        } catch (e: Exception) {
            Quintuple("", "", "", "", "Error obteniendo dirección")
        }
    }



    private fun updateDireccionBar(latLng: LatLng): String {
        return try {
            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                val direccion = address.getAddressLine(0)

                binding.tvDireccion.text = direccion
                binding.tvDireccion2.text = direccion

                hideKeyboard(searchText)
                searchText.clearFocus()

                direccion   // <-- 🔥 RETORNO
            } else {
                binding.tvDireccion.text = "Dirección no disponible"
                "Dirección no disponible"
            }

        } catch (e: Exception) {
            binding.tvDireccion.text = "Error obteniendo dirección"
            "Error obteniendo dirección"
        }
    }



    // ---------------- ANIMACIONES ----------------
    //private var isMapaVisible = true

    private fun ocultarMapaYBotones() {
        isMapaVisible = false
        val interpolator = AccelerateDecelerateInterpolator()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map)
        val mapView = mapFragment?.view

        mapView?.let { map ->
            map.animate()
                .alpha(0f)
                .translationY(50f)
                .setInterpolator(interpolator)
                .setDuration(100)
                .withEndAction {
                    map.visibility = View.GONE

                    // Transición del formulario (fade-in desde abajo)
                    binding.layoutPantalla2.alpha = 0f
                    binding.layoutPantalla2.translationY = 50f
                    binding.layoutPantalla2.visibility = View.VISIBLE
                    binding.layoutPantalla2.animate()
                        .alpha(1f)
                        .translationY(0f)
                        .setInterpolator(interpolator)
                        .setDuration(300)
                        .start()
                }.start()
        }

        val autocomplete = findViewById<FrameLayout>(R.id.autocompleteFragment)
        autocomplete?.let { autoView ->
            autoView.animate()
                .alpha(0f)
                .translationY(50f)
                .setInterpolator(interpolator)
                .setDuration(100)
                .withEndAction { autoView.visibility = View.GONE }
                .start()
        }

        binding.layoutMap.visibility = View.GONE
        binding.btnTipoMapa.visibility = View.GONE
        binding.btnUbicacion.visibility = View.GONE
        binding.btnMostrarMapa.visibility = View.VISIBLE
        binding.btnEnviar.visibility = View.GONE
        // binding.etSearchMap.visibility = View.GONE

        // Hacer visible la barra de búsqueda de colonias
        binding.etSearchColonia.visibility = View.GONE

// Hacer visible la barra de búsqueda de calles
        binding.etSearchCalle.visibility = View.GONE

// Hacer visible la barra para número de calle
     //   binding.etNumeroCalle.visibility = View.GONE


    }

    private fun mostrarMapaYBotones() {




        isMapaVisible = true
        val interpolator = AccelerateDecelerateInterpolator()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map)
        val mapView = mapFragment?.view

        // Transición inversa: formulario → mapa
        binding.layoutPantalla2.animate()
            .alpha(0f)
            .translationY(50f)
            .setInterpolator(interpolator)
            .setDuration(100)
            .withEndAction { binding.layoutPantalla2.visibility = View.GONE }
            .start()

        mapView?.let { map ->
            map.visibility = View.VISIBLE
            map.alpha = 0f
            map.translationY = 50f
            map.animate()
                .translationY(0f)
                .alpha(1f)
                .setInterpolator(interpolator)
                .setDuration(300)
                .start()
        }



        binding.layoutMap.visibility = View.VISIBLE

        // Hacer visible la barra de búsqueda de colonias
        binding.etSearchColonia.visibility = View.VISIBLE

// Hacer visible la barra de búsqueda de calles
        binding.etSearchCalle.visibility = View.VISIBLE

// Hacer visible la barra para número de calle
       // binding.etNumeroCalle.visibility = View.VISIBLE

        binding.btnUbicacion.visibility = View.VISIBLE
        binding.btnTipoMapa.visibility = View.VISIBLE
        binding.btnMostrarMapa.visibility = View.GONE
        binding.btnEnviar.visibility = View.VISIBLE
        binding.autocompleteFragment.visibility = View.GONE
        binding.tvEtiquetaDireccion.visibility = View.GONE
        map.mapType = GoogleMap.MAP_TYPE_NORMAL
        setCustomMapStyle(map)
        isSatellite = false



    }



    // val autocomplete = findViewById<FrameLayout>(R.id.autocompleteFragment)
    //  autocomplete?.let { autoView ->
    //      autoView.visibility = View.VISIBLE
    //       autoView.alpha = 0f
    //       autoView.translationY = 50f
    //       autoView.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(600).start()
    //  }
    //binding.autocompleteFragment.visibility = View.GONE
    // ---------------- MULTIMEDIA ----------------
// ===================== VARIABLES GLOBALES DE MULTIMEDIA =====================

    private val videoPaths = mutableListOf<String>()

    private fun mostrarDialogoSeleccion() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            view.clearFocus()
        }

        val opciones = arrayOf(
            "Tomar foto",
            "Elegir de galería",
            "Grabar video",
            "Grabar audio",
            "Agregar PDF"
        )

        AlertDialog.Builder(this)
            .setTitle("Selecciona una opción")
            .setItems(opciones) { _, which ->

                val accion: () -> Unit = when (which) {
                    0 -> { -> tomarFoto() }
                    1 -> { -> seleccionarDeGaleria() }
                    2 -> { -> grabarVideo() }
                    3 -> { -> mostrarLayoutGrabandoAudio(true) }
                    4 -> { -> seleccionarPdf() }
                    else -> { -> {} }
                }

                // Verifica permisos antes de ejecutar
                verificarYSolicitarPermisos(accion)
            }
            .show()
    }

    private fun pedirPermisoYGrabar() {
        val permiso = Manifest.permission.RECORD_AUDIO
        if (ActivityCompat.checkSelfPermission(
                this,
                permiso
            ) != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(permiso), 200)
        } else {
            iniciarGrabacion()
        }
    }


    private fun iniciarGrabacion() {
        val fileName =
            "AUDIO_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.3gp"
        val file = File(cacheDir, fileName)
        archivoAudioTemporal = file

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(file.absolutePath)
            prepare()
            start()
        }

        isRecording = true
        segundosGrabacion = 0
        grabacionBinding.tvRecordingTimer.text = "00:00"
        grabacionBinding.btnStartRecording.text = "Guardar"
        handler.post(runnableContador())
    }

    private fun detenerGrabacion() {
        grabacionBinding.tvRecordingTimer.text = "00:00"
        if (!isRecording) return
        isRecording = false
        try {
            recorder?.stop()
        } catch (e: RuntimeException) {
            e.printStackTrace()
        }
        recorder?.release()
        recorder = null
        handler.removeCallbacksAndMessages(null)

        archivoAudioTemporal?.let {
            audioPaths.add(it.absolutePath)
            archivoAudioTemporal = null
        }
        mostrarLayoutGrabandoAudio(false)
        grabacionBinding.btnStartRecording.text = "Grabar"
        actualizarGaleria()
    }

    private fun cancelarGrabacion() {
        grabacionBinding.tvRecordingTimer.text = "00:00"
        if (isRecording) {
            try {
                recorder?.stop()
            } catch (e: RuntimeException) {
                e.printStackTrace()
            }
        }
        recorder?.release()
        recorder = null
        isRecording = false
        handler.removeCallbacksAndMessages(null)

        archivoAudioTemporal?.let {
            if (it.exists()) it.delete()
            archivoAudioTemporal = null
        }
        mostrarLayoutGrabandoAudio(false)
        grabacionBinding.btnStartRecording.text = "Grabar"
    }

    private fun runnableContador(): Runnable = object : Runnable {
        override fun run() {
            segundosGrabacion++
            val min = segundosGrabacion / 60
            val seg = segundosGrabacion % 60
            grabacionBinding.tvRecordingTimer.text = String.format("%02d:%02d", min, seg)
            handler.postDelayed(this, 1000)
        }
    }

    private fun mostrarLayoutGrabandoAudio(mostrar: Boolean) {
        if (mostrar) {
            if (grabacionBinding.root.parent == null) {
                addContentView(
                    grabacionBinding.root,
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                    )
                )
            }
            grabacionBinding.root.visibility = View.VISIBLE
        } else {
            grabacionBinding.root.visibility = View.GONE
        }
    }



    // ===================== TOMAR FOTO =====================
    private fun tomarFoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile: File? = try {
            crearArchivoImagen()
        } catch (ex: IOException) {
            null
        }

        photoFile?.also {
            val photoURI = FileProvider.getUriForFile(this, "${packageName}.fileprovider", it)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            tomarFotoLauncher.launch(intent)
        }
    }

    private fun crearArchivoImagen(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File = cacheDir
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }

    private val tomarFotoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && currentPhotoPath != null) {
                val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
                agregarImagen(bitmap, currentPhotoPath!!)
            }
        }

    // ===================== SELECCIONAR GALERÍA =====================
    private fun seleccionarDeGaleria() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        seleccionarImagenLauncher.launch(intent)
    }

    private val seleccionarImagenLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri: Uri? = result.data?.data
                uri?.let {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)
                    val copiedFile = copiarImagenACache(it)
                    copiedFile?.let { file -> agregarImagen(bitmap, file.absolutePath) }
                }
            }
        }

    private fun copiarImagenACache(uri: Uri): File? {
        return try {
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val file = File(cacheDir, "IMG_${timeStamp}.jpg")
            file.outputStream().use { outputStream -> inputStream.copyTo(outputStream) }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // ===================== AGREGAR Y COMPRIMIR IMAGEN =====================
    private fun agregarImagen(bitmap: Bitmap, path: String) {
        val bitmapReducido = comprimirImagen(bitmap)
        imagesList.add(bitmapReducido)

        // Guardar imagen comprimida en la misma ruta
        val compressedFile = File(path)
        try {
            FileOutputStream(compressedFile).use { fos ->
                bitmapReducido.compress(Bitmap.CompressFormat.JPEG, 80, fos)
            }
            imagePaths.add(path)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("❌ COMPRESION", "Error al guardar imagen comprimida: ${e.message}")
        }

        actualizarGaleria()
    }

    private fun comprimirImagen(bitmap: Bitmap): Bitmap {
        val maxWidth = 1280
        val maxHeight = 720
        val width = bitmap.width
        val height = bitmap.height

        if (width <= maxWidth && height <= maxHeight) return bitmap

        val ratio = minOf(maxWidth.toFloat() / width, maxHeight.toFloat() / height)
        val newWidth = (width * ratio).toInt()
        val newHeight = (height * ratio).toInt()

        Log.d("📏 COMPRESION", "Reduciendo de ${width}x${height} → ${newWidth}x${newHeight}")
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

    private fun enviarArchivos(telefono: String, idConversacion: Int, tipoBot: String) {

        val modalLoader = findViewById<FrameLayout>(R.id.rootModalLoader)

        lifecycleScope.launch(Dispatchers.IO) {

            val rutas = (imagePaths + videoPaths + audioPaths + pdfPaths).toMutableList()

            if (rutas.isEmpty()) {
                withContext(Dispatchers.Main) {
                    modalLoader.visibility = View.GONE
                    Toast.makeText(
                        this@MainActivity8,
                        "No hay archivos para enviar",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                return@launch
            }

            // Mostrar loader
            withContext(Dispatchers.Main) {
                modalLoader.visibility = View.VISIBLE
            }

            val gson = GsonBuilder().setPrettyPrinting().create()
            val apiService = Retrofit.Builder()
                .baseUrl("https://aplicativos.ags.gob.mx/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService::class.java)

            for (path in rutas) {
                try {

                    val file = File(path)

                    if (!file.exists() || file.length() == 0L) {
                        Log.e("⚠️ ARCHIVO_INVALIDO", "Archivo no válido: $path")
                        continue
                    }

                    // Codificar en Base64
                    val base64 = Base64.encodeToString(file.readBytes(), Base64.NO_WRAP)

                    val archivo = Archivo(
                        base64Contenido = base64,
                        nombreArchivo = file.name,
                        tipo = when (file.extension.lowercase()) {
                            "jpg", "jpeg", "png" -> "image/jpeg"
                            "mp4" -> "video/mp4"
                            "3gp", "amr", "m4a" -> "audio/3gpp"
                            "pdf" -> "application/pdf"
                            else -> "application/octet-stream"
                        }
                    )

                    val request = GuardarDocumentoNuevoRequest(
                        telefono = telefono,
                        idConversacion = idConversacion,
                        tipoBot = tipoBot,
                        archivos = listOf(archivo)
                    )

                    val response = apiService.guardarDocumentoNuevo(request)

                    // ======== 🔥 BORRAR ARCHIVO DESPUÉS DE SUBIRLO =========
                    if (response.isSuccessful) {
                        Log.d("📤 ARCHIVO_ENVIADO", "Se envió correctamente: $path")
                        borrarArchivo(path)   // ← AQUÍ LO ESTÁS LLAMANDO
                    } else {
                        Log.e("⚠️ ERROR_SUBIDA", "Fallo al subir ${file.name}")
                    }

                } catch (e: Exception) {
                    Log.e("❌ERROR_ARCHIVO", "Error enviando $path: ${e.message}")
                }
            }

            // Ocultar modal
            withContext(Dispatchers.Main) {
                limpiarMultimedia()
                modalLoader.visibility = View.GONE
                Toast.makeText(this@MainActivity8, "Archivos enviados", Toast.LENGTH_SHORT).show()
            }


        }
    }



    // ===================== VIDEO, PDF Y GALERÍA =====================
    private fun grabarVideo() {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        val videoFile = crearArchivoVideo()
        videoFile?.also {
            val videoURI = FileProvider.getUriForFile(this, "${packageName}.fileprovider", it)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI)
            grabarVideoLauncher.launch(intent)
        }
    }

    private fun crearArchivoVideo(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val file = File(cacheDir, "VID_${timeStamp}.mp4")
        videoPath = file.absolutePath
        return file
    }

    private val grabarVideoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && videoPath != null) {

                videoPaths.add(videoPath!!)   // ✅ AGREGA EL VIDEO A LA LISTA

                actualizarGaleria()
            } else {
                videoPath = null
            }
        }

    private fun seleccionarPdf() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
        }
        seleccionarPdfLauncher.launch(intent)
    }

    private val seleccionarPdfLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri: Uri? = result.data?.data
                uri?.let {
                    val copiedFile = copiarPdfACache(it)
                    copiedFile?.let { file ->
                        pdfPaths.add(file.absolutePath)
                        actualizarGaleria()
                    }
                }
            }
        }

    private fun copiarPdfACache(uri: Uri): File? {
        return try {
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val file = File(cacheDir, "PDF_${timeStamp}.pdf")
            file.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // ===================== ACTUALIZAR GALERÍA =====================
    private val verImagenLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            for ((index, path) in imagePaths.withIndex()) {
                val bitmap = BitmapFactory.decodeFile(path)
                if (bitmap != null) {
                    imagesList[index] = bitmap
                }
            }
            actualizarGaleria()
        }
    }


    private fun actualizarGaleria() {
        binding.imageContainer.removeAllViews()
        binding.audioContainer.removeAllViews()
        binding.pdfContainer.removeAllViews()

        val tieneImagenesOVideo = imagePaths.isNotEmpty() || videoPaths.isNotEmpty()
        val tieneAudios = audioPaths.isNotEmpty()
        val tienePdfs = pdfPaths.isNotEmpty()

        binding.tvGalleryLabel.visibility = if (tieneImagenesOVideo) View.VISIBLE else View.GONE
        binding.horizontalScrollView.visibility = if (tieneImagenesOVideo) View.VISIBLE else View.GONE
        binding.tvAudioLabel.visibility = if (tieneAudios) View.VISIBLE else View.GONE
        binding.audioContainer.visibility = if (tieneAudios) View.VISIBLE else View.GONE
        binding.tvPdfLabel.visibility = if (tienePdfs) View.VISIBLE else View.GONE
        binding.pdfContainer.visibility = if (tienePdfs) View.VISIBLE else View.GONE

        // --- IMÁGENES ---
        val iterator = imagePaths.iterator()
        val bitmapsIterator = imagesList.iterator()
        while (iterator.hasNext() && bitmapsIterator.hasNext()) {
            val path = iterator.next()
            val bitmap = bitmapsIterator.next()
            val itemView = LayoutInflater.from(this)
                .inflate(R.layout.item_imagen, binding.imageContainer, false)
            val imageView: ImageView = itemView.findViewById(R.id.imageView)
            val btnEliminar: ImageButton = itemView.findViewById(R.id.btnEliminarImagen)

            imageView.setImageBitmap(bitmap)

            btnEliminar.setOnClickListener {
                eliminarImagen(path, itemView)
            }

            imageView.setOnClickListener {
                val intent = Intent(this, VerImagenActivity::class.java)
                intent.putExtra("imagePath", path)
                verImagenLauncher.launch(intent)
            }

            binding.imageContainer.addView(itemView)
        }

        // --- VIDEOS ---
        for (path in videoPaths.toList()) {  // toList() evita ConcurrentModification
            val itemView = LayoutInflater.from(this)
                .inflate(R.layout.item_video, binding.imageContainer, false)
            val thumbnail: ImageView = itemView.findViewById(R.id.videoThumbnail)
            val btnEliminar: ImageButton = itemView.findViewById(R.id.btnEliminarVideo)

            val thumb = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND)
            thumbnail.setImageBitmap(thumb)

            thumbnail.setOnClickListener {
                val intent = Intent(this, VerVideoActivity::class.java)
                intent.putExtra("videoPath", path)
                startActivity(intent)
            }

            btnEliminar.setOnClickListener {
                eliminarVideo(path, itemView)
            }

            binding.imageContainer.addView(itemView)
        }


        // --- AUDIOS ---
        audioPaths.forEach { audioPath ->
            val itemView = LayoutInflater.from(this)
                .inflate(R.layout.item_audio, binding.audioContainer, false)
            val btnPlay = itemView.findViewById<ImageButton>(R.id.btnPlayAudio)
            val btnEliminar = itemView.findViewById<ImageButton>(R.id.btnDeleteAudio)
            val seekBar = itemView.findViewById<SeekBar>(R.id.audioSeekBar)
            val tvDuracion = itemView.findViewById<TextView>(R.id.tvAudioDuration)

            val playerTemp = MediaPlayer()
            playerTemp.setDataSource(audioPath)
            playerTemp.prepare()
            val duracionSeg = playerTemp.duration / 1000
            tvDuracion.text = String.format("%02d:%02d", duracionSeg / 60, duracionSeg % 60)
            playerTemp.release()

            var currentPlayer: MediaPlayer? = null
            var isSeeking = false

            btnPlay.setOnClickListener {
                if (currentPlayer == null) {
                    currentPlayer = MediaPlayer().apply {
                        setDataSource(audioPath)
                        prepare()
                        start()
                        setOnCompletionListener {
                            btnPlay.setImageResource(android.R.drawable.ic_media_play)
                            currentPlayer?.release()
                            currentPlayer = null
                        }
                    }
                    btnPlay.setImageResource(android.R.drawable.ic_media_pause)
                    seekBar.max = currentPlayer!!.duration

                    val updateSeek = object : Runnable {
                        override fun run() {
                            if (!isSeeking) seekBar.progress = currentPlayer?.currentPosition ?: 0
                            if (currentPlayer?.isPlaying == true) handler.postDelayed(this, 500)
                        }
                    }
                    handler.post(updateSeek)

                    seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                            if (fromUser) currentPlayer?.seekTo(progress)
                        }
                        override fun onStartTrackingTouch(seekBar: SeekBar?) { isSeeking = true }
                        override fun onStopTrackingTouch(seekBar: SeekBar?) { isSeeking = false }
                    })
                } else {
                    currentPlayer?.stop()
                    currentPlayer?.release()
                    currentPlayer = null
                    btnPlay.setImageResource(android.R.drawable.ic_media_play)
                }
            }

            btnEliminar.setOnClickListener {
                currentPlayer?.release()
                currentPlayer = null
                eliminarAudio(audioPath, itemView)
            }

            binding.audioContainer.addView(itemView)
        }

        // --- PDFS ---
        pdfPaths.forEach { pdfPath ->
            val itemView = LayoutInflater.from(this)
                .inflate(R.layout.item_pdf, binding.pdfContainer, false)
            val tvPdfName = itemView.findViewById<TextView>(R.id.tvPdfName)
            val btnEliminar = itemView.findViewById<ImageButton>(R.id.btnEliminarPdf)
            val ivIcon = itemView.findViewById<ImageView>(R.id.pdfIcon)

            val file = File(pdfPath)
            tvPdfName.text = file.name
            ivIcon.setImageResource(android.R.drawable.ic_menu_save)

            itemView.setOnClickListener {
                val intent = Intent(this, PdfViewerActivity::class.java)
                intent.putExtra("pdfPath", pdfPath)
                startActivity(intent)
            }

            btnEliminar.setOnClickListener {
                eliminarPdf(pdfPath, itemView)
            }

            binding.pdfContainer.addView(itemView)
        }
    }

    private var permisosPendientes = mutableListOf<String>()
    private val PERMISSION_REQUEST_CODE = 100
    private var permisoEnProgreso = false
    private var accionPendiente: (() -> Unit)? = null


    private fun verificarYSolicitarPermisos(accion: (() -> Unit)? = null) {

        val permisosNecesarios = mutableListOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
            permisosNecesarios.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            permisosNecesarios.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            permisosNecesarios.add(Manifest.permission.READ_MEDIA_IMAGES)
            permisosNecesarios.add(Manifest.permission.READ_MEDIA_VIDEO)
            permisosNecesarios.add(Manifest.permission.READ_MEDIA_AUDIO)
        }

        // Filtrar no concedidos
        permisosPendientes = permisosNecesarios.filter {
            ActivityCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }.toMutableList()

        accionPendiente = accion

        if (permisosPendientes.isNotEmpty()) {
            pedirSiguientePermiso()
        } else {
            accion?.invoke()
        }
    }


    private fun pedirSiguientePermiso() {
        if (permisoEnProgreso) return

        if (permisosPendientes.isNotEmpty()) {

            permisoEnProgreso = true
            val permiso = permisosPendientes.removeAt(0)

            // ❌ Ya NO se revisa aquí si marcó "No volver a preguntar"
            // Así el sistema muestra primero su diálogo nativo SIEMPRE

            ActivityCompat.requestPermissions(
                this,
                arrayOf(permiso),
                PERMISSION_REQUEST_CODE
            )

        } else {
            permisoEnProgreso = false
            accionPendiente?.invoke()
            accionPendiente = null
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permisoEnProgreso = false

        if (requestCode == PERMISSION_REQUEST_CODE) {

            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pedirSiguientePermiso()
            } else {

                val permiso = permissions[0]

                // ✔ Aquí SÍ debe mostrarse tu dialogo, porque YA pasó el cuadro nativo
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permiso)) {
                    mostrarDialogoIrAjustes()
                    return
                }

                Toast.makeText(this, "Permiso requerido para la acción", Toast.LENGTH_SHORT).show()

                permisosPendientes.clear()
                accionPendiente = null
            }
        }
    }


    private fun mostrarDialogoIrAjustes() {
        AlertDialog.Builder(this)
            .setTitle("Permisos requeridos")
            .setMessage("Debes habilitar los permisos manualmente para continuar.")
            .setPositiveButton("Abrir ajustes") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }


// ============================================================
// 🧭 MÉTODOS DE UBICACIÓN PRECISA (COLONIA / CALLE / NÚMERO)
// ============================================================


    private var selectedCalleCveViaRepi: Calle? = null
    private var selecteddesCalle : String?= null
    private var selecteddesFracc: String? = null

    private var selectedCalleCveFraRespi: String? = null
    private var selectedColoniaCveFraRespi: String? = null


    private var seleccionManualColonia = false


    private fun fetchColonias(query: String) {

        if (query.length <= 1) {
            coloniasList = emptyList()
            mostrarResultadosColonia(coloniasList)
            binding.scrollResultadosColonia.visibility = View.GONE
            return
        }

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getColonias(query)

                if (response.isSuccessful) {

                    val listaOriginal = response.body() ?: emptyList()

                    // 🔹 ORDENAR POR CALLE SELECCIONADA (SI EXISTE)
                    val listaOrdenada = if (!selectedCalleCveFra.isNullOrBlank()) {

                        val mismaCalle = listaOriginal.filter {
                            it.cveFra == selectedCalleCveFra
                        }

                        val otras = listaOriginal.filter {
                            it.cveFra != selectedCalleCveFra
                        }

                        mismaCalle + otras
                    } else {
                        listaOriginal
                    }

                    coloniasList = listaOrdenada

                } else {
                    coloniasList = emptyList()
                }

            } catch (e: Exception) {
                coloniasList = emptyList()
                e.printStackTrace()
            }

            withContext(Dispatchers.Main) {
                // 1️⃣ Pintar resultados
                mostrarResultadosColonia(coloniasList)

                // 2️⃣ Limitar altura
                limitarAlturaResultado(binding.scrollResultadosColonia)
            }
        }
    }


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {

        if (ev.action == MotionEvent.ACTION_DOWN) {

            val focusedView = currentFocus

            // 🔹 CONTROL POR FOCO ENTRE INPUTS
            when {
                binding.etSearchCalle.hasFocus() -> {
                    binding.scrollResultadosColonia.visibility = View.GONE
                }

                binding.etSearchColonia.hasFocus() -> {
                    binding.scrollResultadosCalle.visibility = View.GONE
                }
            }

            // 🔹 Si el foco está en número de calle, ocultar todo
            if (binding.etNumeroCalle.hasFocus()) {
                binding.scrollResultadosCalle.visibility = View.GONE
                binding.scrollResultadosColonia.visibility = View.GONE
            } else {

                // ================= CALLE =================
                if (binding.scrollResultadosCalle.visibility == View.VISIBLE) {

                    val rectInputCalle = Rect()
                    val rectListCalle = Rect()

                    binding.etSearchCalle.getGlobalVisibleRect(rectInputCalle)
                    binding.scrollResultadosCalle.getGlobalVisibleRect(rectListCalle)

                    if (!rectInputCalle.contains(ev.rawX.toInt(), ev.rawY.toInt()) &&
                        !rectListCalle.contains(ev.rawX.toInt(), ev.rawY.toInt())
                    ) {
                        binding.scrollResultadosCalle.visibility = View.GONE
                    }
                }

                // ================= COLONIA =================
                if (binding.scrollResultadosColonia.visibility == View.VISIBLE) {

                    val rectInputColonia = Rect()
                    val rectListColonia = Rect()

                    binding.etSearchColonia.getGlobalVisibleRect(rectInputColonia)
                    binding.scrollResultadosColonia.getGlobalVisibleRect(rectListColonia)

                    if (!rectInputColonia.contains(ev.rawX.toInt(), ev.rawY.toInt()) &&
                        !rectListColonia.contains(ev.rawX.toInt(), ev.rawY.toInt())
                    ) {
                        binding.scrollResultadosColonia.visibility = View.GONE
                    }
                }
            }

            // 🔹 OCULTAR TECLADO AL TOCAR FUERA DEL EDITTEXT
            if (focusedView is EditText) {
                val rect = Rect()
                focusedView.getGlobalVisibleRect(rect)

                if (!rect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    focusedView.clearFocus()
                    hideKeyboard(focusedView)
                }
            }
        }

        return super.dispatchTouchEvent(ev)
    }





    private fun mostrarResultadosColonia(lista: List<Colonia>) {

        val container = binding.layoutResultadosColonia
        container.removeAllViews()

        if (lista.isNullOrEmpty()) {
            container.visibility = View.GONE
            binding.scrollResultadosColonia.visibility = View.GONE
            return
        }

        container.visibility = View.VISIBLE
        binding.scrollResultadosColonia.visibility = View.VISIBLE

        binding.scrollResultadosColonia.post {
            binding.scrollResultadosColonia.scrollTo(0, 0)
        }

        lista.forEach { colonia ->

            val item = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(24, 16, 24, 16)
            }

            val tvNombre = TextView(this).apply {
                text = colonia.nomEditFra
                setTypeface(typeface, Typeface.BOLD)
                setTextColor(Color.BLACK)
                textSize = 16f
            }



            item.addView(tvNombre)

            // ✅ SOLO agregar fraccionamiento si existe y NO es "NO ESPECIFICADA"
            if (
                !colonia.delNombre.isNullOrBlank() &&
                !colonia.delNombre.equals("NO ESPECIFICADA", ignoreCase = true)
            ) {
                val tvFracc = TextView(this).apply {
                    text = colonia.delNombre
                    setTextColor(Color.DKGRAY)
                    textSize = 14f
                }
                item.addView(tvFracc)
            }



            item.setOnClickListener {

                seleccionManualColonia = true

                binding.etSearchColonia.setText(colonia.nomEditFra)
                binding.etSearchColonia.setSelection(colonia.nomEditFra.length)

                selectedColonia = colonia
                selectedColoniaCveFra = colonia.cveFra

                container.visibility = View.GONE
                binding.scrollResultadosColonia.visibility = View.GONE
                binding.scrollResultadosCalle.visibility = View.GONE

                // 🔹 Reubicar mapa según contexto
                if (selectedCalleCveViaRepi != null) {
                    if (!binding.etNumeroCalle.text.isNullOrBlank()) {
                        buscarUbicacionNumero(
                            selectedCalleCveViaRepi!!,
                            binding.etNumeroCalle.text.toString(),
                            colonia
                        )
                    } else {
                        if (selectedCalleCveFraRespi == colonia?.cveFra) {


                            buscarUbicacionCalle(selectedCalleCveViaRepi!!, colonia)
                        }

                       else  if (selectedCalleCveFraRespi != colonia?.cveFra) {

                            binding.scrollResultadosColonia.visibility = View.GONE
                            binding.scrollResultadosCalle.visibility = View.GONE
                            selectedColoniaCveFra = null
                            selecteddesFracc =  null
                            selectedCalle =  null
                            selectedCalleCveVia =  null
                            selectedCalleNomVia =  null
                            selectedCalleNomEditVia =  null
                            selectedCalleCveFra =  null
                            selectedCalleFraccionamiento =  null
                            selectedCalleLatLng =  null
                            selectedLatLng =  null
                            selecteddesCalle =   null
                            selectedCalleCveFraRespi =  null

                            binding.etSearchCalle.setText("")
                            buscarUbicacionColonia(colonia)
                        }
                    }
                } else {

                    buscarUbicacionColonia(colonia)
                }
            }

            container.addView(item)
        }
    }



    private fun fetchCalles(query: String) {

        // 👉 Si tiene 2 caracteres o menos
        if (query.length <= 1) {
            callesList = emptyList()
            mostrarResultadosCalle(callesList)
            binding.scrollResultadosCalle.visibility = View.GONE
            return
        }

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.getCalles(query)

                if (response.isSuccessful) {

                    val listaOriginal = response.body() ?: emptyList()

                    // 🔹 ORDENAR POR FRACCIONAMIENTO SELECCIONADO (SI EXISTE)
                    val listaOrdenada = if (!selectedColoniaCveFra.isNullOrBlank()) {

                        val mismaFracc = listaOriginal.filter {
                            it.cveFra == selectedColoniaCveFra
                        }

                        val otras = listaOriginal.filter {
                            it.cveFra != selectedColoniaCveFra
                        }

                        mismaFracc + otras
                    } else {
                        listaOriginal
                    }

                    callesList = listaOrdenada

                } else {
                    callesList = emptyList()
                }

            } catch (e: Exception) {
                callesList = emptyList()
                e.printStackTrace()
            }

            withContext(Dispatchers.Main) {
                // 1️⃣ Pintar resultados
                mostrarResultadosCalle(callesList)

                // 2️⃣ AHORA sí limitar altura
                limitarAlturaResultado(binding.scrollResultadosCalle)
            }
        }
    }





    private var seleccionManualCalle = false



    private fun mostrarResultadosCalle(lista: List<Calle>) {

        val container = binding.layoutResultadosCalle
        container.removeAllViews()

        if (lista.isNullOrEmpty()) {
            container.visibility = View.GONE
            binding.scrollResultadosCalle.visibility = View.GONE
            return
        }

        container.visibility = View.VISIBLE
        binding.scrollResultadosCalle.visibility = View.VISIBLE

        binding.scrollResultadosCalle.post {
            binding.scrollResultadosCalle.scrollTo(0, 0)
        }

        lista.forEach { calle ->

            val item = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(24, 16, 24, 16)
            }

            val tvCalle = TextView(this).apply {
                text = calle.nomEditVia
                setTypeface(typeface, Typeface.BOLD)
                setTextColor(Color.BLACK)
                textSize = 17f
            }

            item.addView(tvCalle)

            // ✅ SOLO agregar fraccionamiento si existe y NO es "NO ESPECIFICADA"
            val fracc = calle.fraccionamiento?.trim()

            if (
                !fracc.isNullOrEmpty() &&
                !fracc.equals("NO ESPECIFICADA", ignoreCase = true)
            ) {
                val tvFracc = TextView(this).apply {
                    text = fracc
                    setTextColor(Color.DKGRAY)
                    textSize = 14f
                }
                item.addView(tvFracc)
            }


            item.setOnClickListener {

                seleccionManualCalle = true

                // Escribe SOLO la calle
                binding.etSearchCalle.setText(calle.nomEditVia)
                binding.etSearchCalle.setSelection(calle.nomEditVia.length)

                selectedCalleCveViaRepi = calle

                // 🔹 OCULTAR RESULTADOS
                container.visibility = View.GONE
                binding.scrollResultadosColonia.visibility = View.GONE
                binding.scrollResultadosCalle.visibility = View.GONE

                if (selectedColoniaCveFraRespi == calle?.cveFra) {


                    buscarUbicacionCalle(calle, selectedColonia)
                }

                else  if (selectedColoniaCveFraRespi != calle?.cveFra) {

                    binding.scrollResultadosColonia.visibility = View.GONE
                    binding.scrollResultadosCalle.visibility = View.GONE
                    selectedColonia = null
                    selectedColoniaCveFra = null
                    selectedColoniaLatLng = null
                    selectedLatLng = null
                    selecteddesFracc = null
                    //  selectedCalleCveFraRespi = colonia.cveFra
                    selectedColoniaCveFraRespi = null

                    binding.etSearchColonia.setText("")
                    buscarUbicacionCalle(calle, selectedColonia)
                }


            }

            container.addView(item)
        }
    }


    // ===================== BUSCAR UBICACIÓN COLONIA =====================
    private fun buscarUbicacionColonia(colonia: Colonia) {
        binding.scrollResultadosColonia.visibility = View.GONE
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val geocoder = Geocoder(this@MainActivity8, Locale("es", "MX"))

                val fullQuery = buildString {
                    append(colonia.nomFra)
                    if (!colonia.cpFra.isNullOrEmpty()) append(", Código postal ${colonia.cpFra}"   )
                    append(", Aguascalientes, México")
                }

                Log.d("DEBUG_COLONIA", "📍 Query COLONIA: $fullQuery")

                val addresses = geocoder.getFromLocationName(fullQuery, 10) ?: emptyList()
                addresses.forEachIndexed { i, addr ->
                    Log.d("DEBUG_COLONIA_ALL", "[$i] ➜ $addr")
                }

                val filtered = addresses.filter {
                    val localidadMatch = it.locality?.contains("Aguascalientes", true) == true
                    val nombreMatch = it.subLocality?.contains(colonia.nomFra, true) == true
                    val cpMatch = colonia.cpFra?.let { cp ->
                        it.postalCode?.contains(cp, true) == true
                    } ?: true
                    localidadMatch && (nombreMatch || cpMatch)
                }

                val bestAddress = filtered.firstOrNull() ?: addresses.firstOrNull()

                withContext(Dispatchers.Main) {
                    if (bestAddress != null) {
                        val latLng = LatLng(bestAddress.latitude, bestAddress.longitude)
                        marker?.remove()

                        marker = map.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .title(colonia.nomEditFra)
                                .snippet(
                                    if (!colonia.cpFra.isNullOrEmpty())
                                        "Código Postal ${colonia.cpFra}"
                                    else
                                        "Colonia ${colonia.delNombre}"
                                )
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        )

                        // ✅ Crear variable con dirección completa
                        val direccionFinal = buildString {
                            append(colonia.nomEditFra)
                            if (!colonia.cpFra.isNullOrEmpty()) {
                                append(", Código Postal ${colonia.cpFra}")
                            } else {
                                append(", Colonia ${colonia.delNombre}")
                            }
                            append(", Aguascalientes, México")
                        }

                        // ✅ Llamar a la función para formatear dirección
                        val direccionFormateada = formatearDireccionColonia(direccionFinal)

                        // ✅ Asignar texto formateado al TextView
                        binding.tvDireccion2.text = direccionFormateada

                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                        marker?.showInfoWindow()

                        selectedColonia = colonia
                        selectedColoniaCveFra = colonia.cveFra
                        selectedColoniaLatLng = latLng
                        selectedLatLng = latLng
                        selecteddesFracc = colonia.nomEditFra
                      //  selectedCalleCveFraRespi = colonia.cveFra
                        selectedColoniaCveFraRespi = colonia.cveFra


                        // 🔹 Pasar foco automáticamente al campo número
                        binding.etSearchCalle.requestFocus()
                        binding.scrollResultadosColonia.visibility = View.GONE
                        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.showSoftInput(binding.etSearchCalle, InputMethodManager.SHOW_IMPLICIT)
                    } else {
                        showError("No se encontró una ubicación exacta para la colonia.")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    showError("Error al buscar la ubicación de la colonia.")
                }
            }
        }
    }


    // ---------------------- RETROFIT GOOGLE MAPS ----------------------
    private fun getGoogleRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/")
            .client(
                OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Servicio de Google Geocoding API
    private val googleService by lazy {
        getGoogleRetrofit().create(GoogleGeocodeService::class.java)
    }

    private fun buscarUbicacionCalle(calle: Calle, colonia: Colonia? = null) {
        binding.scrollResultadosCalle.visibility = View.GONE
        cambiarAnchoAutoComplete()


        val apiKey = getString(R.string.google_maps_key)

        // ✅ Limpiar nombre de calle ANTES de usarlo
        val nomCalleLimpio = calle.nomEditVia
            ?.substringBefore(",")
            ?.replace("º", "o")
            ?.trim()
            ?: calle.nomEditVia

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Construir query limpio para Google Maps
                val streetQuery = buildString {
                    append(nomCalleLimpio)
                    // ✅ Agregado: incluir código postal junto con el fraccionamiento
                    if (!colonia?.nomFra.isNullOrEmpty()) append(", Código postal ${colonia?.cpFra}, ${colonia?.nomEditFra}")
                    else if (!calle.fraccionamiento.isNullOrEmpty()) append(", ${calle.fraccionamiento}")
                    append(", Aguascalientes, México")
                }

                Log.d("DEBUG_CALLE", "📍 Query CALLE: $streetQuery")

                val components = buildString {
                    append("route:$nomCalleLimpio")
                    colonia?.cpFra?.let { append("|postal_code:$it") }
                    append("|country:MX")
                }

                val response = googleService.geocodeWithComponents(streetQuery, components, apiKey)

                val resultLocation = if (response.status == "OK" && response.results.isNotEmpty()) {
                    val location = response.results[0].geometry.location
                    LatLng(location.lat, location.lng) to response.results[0].formatted_address
                } else {
                    val geocoder = Geocoder(this@MainActivity8, Locale("es", "MX"))
                    val addresses = geocoder.getFromLocationName(streetQuery, 10) ?: emptyList()
                    addresses.firstOrNull()
                        ?.let { LatLng(it.latitude, it.longitude) to it.getAddressLine(0) }
                }

                withContext(Dispatchers.Main) {
                    if (resultLocation != null) {
                        val (latLng, direccion) = resultLocation
                        marker?.remove()

                        val descripcion = buildString {
                            if (!colonia?.nomFra.isNullOrEmpty()) append(", ${colonia?.nomEditFra}" )
                            else if (!calle.fraccionamiento.isNullOrEmpty()) append(", ${calle.fraccionamiento}")
                        }

                        binding.etNumeroCalle.requestFocus()
                        binding.scrollResultadosCalle.visibility = View.GONE
                        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.showSoftInput(binding.etNumeroCalle, InputMethodManager.SHOW_IMPLICIT)

                        selectedColoniaCveFra = colonia?.cveFra
                        selecteddesFracc = colonia?.nomEditFra
                        selectedCalle = calle
                        selectedCalleCveVia = calle.cveVia
                        selectedCalleNomVia = calle.nomVia
                        selectedCalleNomEditVia = nomCalleLimpio
                        selectedCalleCveFra = calle.cveFra
                        selectedCalleFraccionamiento = calle.fraccionamiento
                        selectedCalleLatLng = latLng
                        selectedLatLng = latLng
                        selecteddesCalle =  calle.nomEditVia
                        selectedCalleCveFraRespi = calle.cveFra

                        // ✅ Mostrar dirección formateada en el TextView
                       // binding.tvDireccion2.text = formatearDireccionCalle(streetQuery)
                        binding.tvDireccion2.text = streetQuery

                        marker = map.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .title(nomCalleLimpio)
                                .snippet(descripcion)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        )

                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
                        marker?.showInfoWindow()
                    } else {
                        showError("No se encontró ubicación para: $streetQuery")
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    showError("Error al buscar la ubicación de la calle con Google API.")
                }
            }
        }
    }


    // ===================== BUSQUEDA PRECISA NÚMERO =====================
    private fun buscarUbicacionNumero(calle: Calle, numero: String, colonia: Colonia? = null) {
        binding.scrollResultadosCalle.visibility = View.GONE
        cambiarAnchoAutoComplete()


        val apiKey = getString(R.string.google_maps_key)

        // ✅ Limpiar nombre de la calle antes de usarlo
        val nomCalleLimpio = calle.nomEditVia
            ?.substringBefore(",")
            ?.replace("º", "o")
            ?.trim()
            ?: calle.nomEditVia

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val streetQuery = buildString {
                    append("$nomCalleLimpio #${numero.trim()}")
                    if (!colonia?.nomFra.isNullOrEmpty()) append(", Código postal ${colonia?.cpFra}, ${colonia?.nomEditFra}")
                    else if (!calle.fraccionamiento.isNullOrEmpty()) append(", ${calle.fraccionamiento}")
                    append(", Aguascalientes, México")
                }



                Log.d("DEBUG_NUMERO", "📍 Query NUMERO: $streetQuery")

                val components = buildString {
                    append("street_address:$nomCalleLimpio #${numero.trim()}")
                    colonia?.cpFFra?.let { append("|postal_code:$it") }
                    append("|country:MX")
                }

                val response = googleService.geocodeWithComponents(streetQuery, components, apiKey)

                val resultLocation = if (response.status == "OK" && response.results.isNotEmpty()) {
                    val location = response.results[0].geometry.location
                    LatLng(location.lat, location.lng) to response.results[0].formatted_address
                } else {
                    val geocoder = Geocoder(this@MainActivity8, Locale("es", "MX"))
                    val addresses = geocoder.getFromLocationName(streetQuery, 10) ?: emptyList()
                    addresses.firstOrNull()
                        ?.let { LatLng(it.latitude, it.longitude) to it.getAddressLine(0) }
                }

                withContext(Dispatchers.Main) {
                    if (resultLocation != null) {
                        val (latLng, direccion) = resultLocation
                        marker?.remove()

                        val descripcion = buildString {
                            if (!colonia?.nomFra.isNullOrEmpty()) append(", ${colonia?.nomEditFra}" )
                            else if (!calle.fraccionamiento.isNullOrEmpty()) append(", ${calle.fraccionamiento}")
                        }


                        selectedCalleNumero = numero.trim()
                        selectedColoniaCveFra = colonia?.cveFra
                        selecteddesFracc = colonia?.nomEditFra
                        selectedCalle = calle
                        selectedCalleCveVia = calle.cveVia
                        selectedCalleNomVia = calle.nomVia
                        selectedCalleNomEditVia = nomCalleLimpio
                        selectedCalleCveFra = calle.cveFra
                        selectedCalleFraccionamiento = calle.fraccionamiento
                        selectedCalleLatLng = latLng
                        selectedLatLng = latLng
                        selecteddesCalle =  calle.nomEditVia

                        // ✅ Mostrar dirección formateada en el TextView
                      // binding.tvDireccion2.text = formatearDireccionCalle(streetQuery)
                        binding.tvDireccion2.text = streetQuery


                        marker = map.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .title("$nomCalleLimpio #$numero")
                                .snippet(descripcion)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        )

                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f))
                        marker?.showInfoWindow()
                    } else {
                        showError("No se encontró ubicación para: $streetQuery")
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    showError("Error al buscar la ubicación del número con Google API.")
                }
            }
        }
    }


    private fun cambiarAnchoAutoComplete() {
        val autoComplete = binding.etSearchCalle
        val params = autoComplete.layoutParams as ConstraintLayout.LayoutParams

        params.width = 0
        params.endToEnd = ConstraintLayout.LayoutParams.UNSET
        params.endToStart = binding.etNumeroCalle.id

        autoComplete.layoutParams = params
        binding.scrollResultadosColonia.visibility = View.GONE
        binding.scrollResultadosCalle.visibility = View.GONE
        binding.etNumeroCalle.visibility = View.VISIBLE
    }


    private fun restaurarAnchoAutoComplete() {
        val autoComplete = binding.etSearchCalle
        val params = autoComplete.layoutParams as ConstraintLayout.LayoutParams

        params.width = 0
        params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        params.endToStart = ConstraintLayout.LayoutParams.UNSET

        autoComplete.layoutParams = params
        binding.etNumeroCalle.visibility = View.GONE
    }


    // ===================== FUNCIÓN PARA FORMATEAR DIRECCIÓN =====================
    private fun formatearDireccionColonia(direccion: String): String {
        // Limpieza básica de texto
        val limpia = direccion
            .replace("\\s+".toRegex(), " ")      // Quita espacios múltiples
            .replace(",\\s*,".toRegex(), ",")    // Evita comas duplicadas
            .replace(" ,".toRegex(), ",")        // Quita espacio antes de coma
            .trim()

        // Extraer posibles partes
        val nombreColonia = Regex("(?i)colonia\\s+([\\w\\sáéíóúñ.]+)").find(limpia)?.groupValues?.get(1)
            ?: limpia.substringBefore(",").trim()

        val codigoPostal = Regex("(?i)(\\d{5})").find(limpia)?.groupValues?.get(1)

        // Construir dirección formateada en líneas separadas
        val direccionFormateada = buildString {
            append("${nombreColonia.trim()}\n")              // 🔹 Línea 1
            if (!codigoPostal.isNullOrEmpty()) {
                append("Código Postal $codigoPostal\n")              // 🔹 Línea 2
            }
            append("Aguascalientes, México")                         // 🔹 Línea 3
        }

        // Capitalizar correctamente cada palabra
        return direccionFormateada.lowercase().split(" ").joinToString(" ") { palabra ->
            palabra.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
    }


////////

    // ===================== FUNCIÓN PARA FORMATEAR DIRECCIÓN =====================
    private fun formatearDireccionCalle(
        calle: String? = null,
        numero: String? = null,
        fraccionamiento: String? = null,
        colonia: String? = null,
        direccionCruda: String? = null
    ): String {
        // 🔹 Detectar código postal si existiera en la dirección cruda
        val codigoPostal = direccionCruda?.let {
            Regex("(?i)(\\d{5})").find(it)?.groupValues?.get(1)
        }

        // 🔹 Construir dirección multilínea segura
        val direccionFormateada = buildString {
            // 1️⃣ Calle + número
            if (!calle.isNullOrBlank()) {
                append(calle.trim())
                if (!numero.isNullOrBlank()) append(" #${numero.trim()}")
                append("\n")
            }

            // 2️⃣ Fraccionamiento


            // 3️⃣ Colonia
            if (!colonia.isNullOrBlank()) {
                append("${colonia.trim()}\n")
            }

            if (!fraccionamiento.isNullOrBlank()) {
                append("${fraccionamiento.trim()}\n")
            }

            // 4️⃣ Código postal (si se detecta)
            if (!codigoPostal.isNullOrEmpty()) {
                append("Código Postal $codigoPostal\n")
            }

            // 5️⃣ Ciudad y país
            append("Aguascalientes, México")
        }

        // 🔹 Limpieza y capitalización
        return direccionFormateada
            .replace("\\s+".toRegex(), " ") // quitar espacios múltiples
            .replace(" ,".toRegex(), ",")
            .trim()
            .lowercase()
            .split(" ")
            .joinToString(" ") { palabra ->
                palabra.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase() else it.toString()
                }
            }
    }

// ============================================================
// 🧰 CONFIGURAR AUTOCOMPLETE Y BOTONES
// ============================================================

    private fun setupAutoCompleteColonia() {

        val autoText = binding.etSearchColonia
        setupClearButton(autoText, true)

        autoText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {

                if (seleccionManualColonia) {
                    seleccionManualColonia = false
                    return
                }

                selectedWordLength = text?.length ?: 0
                updateClearButtonVisibility(autoText)

                if (text.isNullOrBlank() || text.length < 1) {
                    binding.scrollResultadosColonia.visibility = View.GONE
                    binding.scrollResultadosCalle.visibility = View.GONE
                    return
                }

                fetchColonias(text.toString())
            }
        })
    }


    private fun setupAutoCompleteCalle() {

        val autoText = binding.etSearchCalle
        setupClearButton(autoText, false)

        autoText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(
                text: CharSequence?, start: Int, before: Int, count: Int
            ) {

                // 🚫 Evita re-búsqueda al seleccionar
                if (seleccionManualCalle) {
                    seleccionManualCalle = false
                    return
                }

                selectedWordLengthCalle = text?.length ?: 0
                updateClearButtonVisibility(autoText)

                if (text.isNullOrBlank() || text.length < 1) {
                    binding.scrollResultadosColonia.visibility = View.GONE
                    binding.scrollResultadosCalle.visibility = View.GONE
                    return
                }

                fetchCalles(text.toString())
            }
        })
    }

    private fun setupNumeroCalleBusqueda() {
        val etNumero = binding.etNumeroCalle
        setupClearButtonGenerico(etNumero)



        var searchJob: Job? = null

        etNumero.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                searchJob?.cancel()
                val numeroTexto = s?.toString()?.trim().orEmpty()
                if (numeroTexto.isNotEmpty() && selectedCalle != null) {
                    searchJob = lifecycleScope.launch {
                        delay(300)
                        buscarUbicacionNumero(selectedCalle!!, numeroTexto, selectedColonia)
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateClearButtonVisibility(etNumero)
            }
        })
    }

// ============================================================
// 🔧 BOTONES LIMPIAR Y UTILIDADES
// ============================================================

    @SuppressLint("ClickableViewAccessibility")
    private fun setupClearButton(autoCompleteTextView: AutoCompleteTextView, isColonia: Boolean) {

        // ✅ Oculta la X al iniciar
        updateClearButtonVisibility(autoCompleteTextView)

        // La X aparece solo cuando se escribe
        autoCompleteTextView.addTextChangedListener {
            updateClearButtonVisibility(autoCompleteTextView)
        }

        autoCompleteTextView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableRight = 2
                autoCompleteTextView.compoundDrawables[drawableRight]?.let { drawable ->
                    if (event.rawX >= (autoCompleteTextView.right - drawable.bounds.width())) {

                        autoCompleteTextView.text.clear()
                        updateClearButtonVisibility(autoCompleteTextView)

                        if (isColonia) {
                            marker?.remove()
                            marker = null
                            selectedWordLength = 0
                            coloniasList = emptyList()

                            selectedColonia = null
                            selectedColoniaCveFra = null
                            selectedColoniaLatLng = null
                            selectedLatLng = null
                            selecteddesFracc = null
                            selectedColoniaCveFraRespi = null
                            binding.scrollResultadosColonia.visibility = View.GONE
                            binding.scrollResultadosCalle.visibility = View.GONE

                            binding.tvDireccion2.text = ""

                            binding.etSearchColonia.requestFocus()
                            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.showSoftInput(binding.etSearchColonia, InputMethodManager.SHOW_IMPLICIT)

                            if (!binding.etSearchCalle.text.isNullOrBlank()) {
                                setupAutoCompleteCalle()
                                val texto = binding.etSearchCalle.text.toString()
                                val index = callesList.indexOfFirst { it.nomEditVia == texto }

                                if (index != -1) {
                                    val selected = callesList[index]
                                    binding.etSearchCalle.setText(selected.nomEditVia)
                                    selectedCalleCveViaRepi = selected



                                    if (!binding.etNumeroCalle.text.isNullOrBlank()) {
                                        val numeroCalle = binding.etNumeroCalle.text.toString()
                                        Log.d("DEBUG_UBICACION", "Entró en el if, número de calle: $numeroCalle")
                                        // Aquí llamas a la función correcta con número
                                        binding.scrollResultadosColonia.visibility = View.GONE
                                        binding.scrollResultadosCalle.visibility = View.GONE
                                        buscarUbicacionNumero(selected, numeroCalle, selectedColonia)
                                    } else {
                                        Log.d("DEBUG_UBICACION", "Entró en el else, el campo está vacío")
                                        // Si no hay número, buscas por la calle completa
                                        binding.scrollResultadosColonia.visibility = View.GONE
                                        binding.scrollResultadosCalle.visibility = View.GONE
                                        buscarUbicacionCalle(selected, selectedColonia)
                                    }


                                }
                            }

                        } else if (autoCompleteTextView.id == R.id.etSearchCalle) {

                            selectedCalleNumero = null
                            selectedCalle = null
                            selectedCalleCveVia = null
                            selectedCalleNomVia = null
                            selectedCalleNomEditVia = null
                            selectedCalleCveFra = null
                            selectedCalleFraccionamiento = null
                            selectedCalleLatLng = null
                            selectedLatLng = null
                            selecteddesCalle = null
                            selectedCalleCveFraRespi = null
                            binding.scrollResultadosColonia.visibility = View.GONE
                            binding.scrollResultadosCalle.visibility = View.GONE


                            marker?.remove()
                            marker = null

                            selectedWordLengthCalle = 0
                            selectedCalleCveViaRepi = null
                            callesList = emptyList()

                            restaurarAnchoAutoComplete()

                            binding.tvDireccion2.text = ""
                            binding.etNumeroCalle.text.clear()

                            binding.etSearchCalle.requestFocus()
                            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.showSoftInput(binding.etSearchCalle, InputMethodManager.SHOW_IMPLICIT)

                            if (!binding.etSearchColonia.text.isNullOrBlank()) {
                                setupAutoCompleteColonia()
                                val texto = binding.etSearchColonia.text.toString()
                                val index = coloniasList.indexOfFirst { it.nomEditFra == texto }

                                if (index != -1) {
                                    val selected = coloniasList[index]
                                    binding.scrollResultadosColonia.visibility = View.GONE
                                    binding.scrollResultadosCalle.visibility = View.GONE
                                    buscarUbicacionColonia(selected)
                                }
                            }
                        }

                        return@setOnTouchListener true
                    }
                }
            }
            false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupClearButtonGenerico(editText: EditText) {

        // ✅ Ocultar X al inicio
        updateClearButtonVisibility(editText)

        editText.addTextChangedListener {
            updateClearButtonVisibility(editText)
        }

        editText.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableRight = 2
                editText.compoundDrawables[drawableRight]?.let { drawable ->
                    if (event.rawX >= (editText.right - drawable.bounds.width())) {

                        editText.text.clear()
                        updateClearButtonVisibility(editText)

                        // ✅ Nuevo: comportamiento especial para etNumeroCalle
                        if (editText.id == R.id.etNumeroCalle) {

                            // Limpieza de datos asociados
                            marker?.remove()
                            marker = null
                            selectedCalleNumero = null

                            // Limpia la dirección visible
                            binding.tvDireccion2.text = ""

                            // 🔹 Opcional: restaurar datos si hay calle y colonia seleccionadas
                            if (!binding.etSearchCalle.text.isNullOrBlank() &&
                                !binding.etSearchColonia.text.isNullOrBlank()) {

                                val textoCalle = binding.etSearchCalle.text.toString()
                                val textoColonia = binding.etSearchColonia.text.toString()

                                val calleSeleccionada = callesList.firstOrNull { it.nomEditVia == textoCalle }
                                val coloniaSeleccionada = coloniasList.firstOrNull { it.nomEditFra == textoColonia }

                                if (calleSeleccionada != null && coloniaSeleccionada != null) {
                                    // ✅ Recalcula posición base sin número
                                    binding.scrollResultadosColonia.visibility = View.GONE
                                    binding.scrollResultadosCalle.visibility = View.GONE
                                    buscarUbicacionCalle(calleSeleccionada, coloniaSeleccionada)
                                }
                                else {
                                    Log.d("DEBUG_UBICACION", "Entró en el else, el campo está vacío")
                                    // Si no hay número, buscas por la calle completa
                                    binding.scrollResultadosColonia.visibility = View.GONE
                                    binding.scrollResultadosCalle.visibility = View.GONE
                                    buscarUbicacionCalle(selectedCalle!!, selectedColonia)
                                }

                            }

                            // 🔹 Vuelve a enfocar el campo
                            editText.requestFocus()
                            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
                        }

                        return@setOnTouchListener true
                    }
                }
            }
            false
        }
    }

    private fun updateClearButtonVisibility(view: TextView) {
        val text = view.text.toString()
        val leftDrawable = view.compoundDrawables[0]
        val rightDrawable = if (text.isNotEmpty()) {
            ContextCompat.getDrawable(this, R.drawable.ic_close)
        } else null
        view.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, rightDrawable, null)
    }

    // ---------------------- UTILIDADES ----------------------
    private fun showError(message: String) {
        runOnUiThread { Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }
    }

    private fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    private fun mostrarMensaje(texto: String) {
        runOnUiThread {
            Toast.makeText(this, texto, Toast.LENGTH_SHORT).show()
        }
    }


    // ---------------------- VARIABLES ----------------------
    private lateinit var nombreWatcher: TextWatcher
    private lateinit var apellidoPaternoWatcher: TextWatcher
    private lateinit var apellidoMaternoWatcher: TextWatcher

    private var listaNombres: MutableList<String> = mutableListOf()
    private var isSelectingSuggestion = false // Evitar duplicaciones

    // ---------------------- CONFIGURACIÓN DEL AUTOCOMPLETE NOMBRE ----------------------
    private fun setupAutoCompleteNombre() {
        val autoText = binding.etNombre
        autoText.threshold = 1

        nombreWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    val texto = it.toString()

                    // 🔹 No mostrar listado si el texto termina con un espacio
                    if (texto.endsWith(" ")) {
                        autoText.dismissDropDown()
                        return
                    }

                    val words = texto.split(" ")
                    val lastWord = words.lastOrNull() ?: ""
                    if (lastWord.isNotEmpty()) {
                        fetchNombreSuggestions(lastWord) { suggestions ->
                            val updatedSuggestions = suggestions.map { suggestion ->
                                val prefix = words.dropLast(1).joinToString(" ")
                                if (prefix.isNotEmpty()) "$prefix $suggestion" else suggestion
                            }

                            val adapter = ArrayAdapter(
                                this@MainActivity8,
                                android.R.layout.simple_dropdown_item_1line,
                                updatedSuggestions
                            )
                            autoText.setAdapter(adapter)

                            // Mostrar dropdown solo si el campo tiene foco y hay sugerencias
                            if (autoText.hasFocus() && suggestions.isNotEmpty()) {
                                autoText.showDropDown()
                            } else {
                                autoText.dismissDropDown()
                            }
                        }
                    } else {
                        autoText.dismissDropDown()
                    }
                }
            }
        }

        autoText.addTextChangedListener(nombreWatcher)

        autoText.setOnItemClickListener { _, _, _, _ ->
            val texto = autoText.text.toString()
            val palabras = texto.trim().split(" ")
            if (palabras.size == 1) {
                autoText.setText("$texto ")

            } else {

                autoText.setSelection(autoText.text.length)
                autoText.dismissDropDown()
                ocultarTeclado(autoText)
                binding.etApellidoPaterno.requestFocus()
            }
            autoText.setSelection(autoText.text.length)
            autoText.dismissDropDown()
        }

        autoText.setOnEditorActionListener { _, _, _ ->
            val texto = autoText.text.toString()
            val palabras = texto.trim().split(" ")
            if (palabras.size == 1) {
                autoText.setText("$texto ")
            } else {

                ocultarTeclado(autoText)
                binding.etApellidoPaterno.requestFocus()
            }
            autoText.setSelection(autoText.text.length)
            autoText.dismissDropDown()
            true
        }

        // Ocultar listado si pierde foco
        autoText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                autoText.dismissDropDown()
            }
        }
    }




    private var blockDropDownPaterno = false
    private var blockDropDownMaterno = false

    private lateinit var adapterApellidoPaterno: ArrayAdapter<String>

    private fun setupAutoCompleteApellidoPaterno() {
        val autoText = binding.etApellidoPaterno
        autoText.threshold = 1

        // ✅ Usar un solo adaptador mutable
        adapterApellidoPaterno = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, mutableListOf())
        autoText.setAdapter(adapterApellidoPaterno)

        autoText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s?.toString() ?: ""
                if (query.isNotBlank()) {
                    fetchApellidoSuggestions(query) { suggestions ->
                        runOnUiThread {
                            adapterApellidoPaterno.clear()
                            adapterApellidoPaterno.addAll(suggestions)
                            adapterApellidoPaterno.notifyDataSetChanged()
                            // Mostrar dropdown solo si tiene foco
                            if (autoText.hasFocus() && suggestions.isNotEmpty()) {
                                autoText.showDropDown()
                            }
                        }
                    }
                }
            }
        })

        autoText.setOnItemClickListener { _, _, _, _ ->
            ocultarTeclado(autoText)
            autoText.dismissDropDown()
            binding.etApellidoMaterno.requestFocus()
        }

        autoText.setOnEditorActionListener { _, _, _ ->
            ocultarTeclado(autoText)
            autoText.dismissDropDown()
            binding.etApellidoMaterno.requestFocus()
            true
        }
    }


    private lateinit var adapterApellidoMaterno: ArrayAdapter<String>

    private fun setupAutoCompleteApellidoMaterno() {
        val autoText = binding.etApellidoMaterno
        autoText.threshold = 1

        // ✅ Usar un solo adaptador mutable
        adapterApellidoMaterno = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, mutableListOf())
        autoText.setAdapter(adapterApellidoMaterno)

        autoText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s?.toString() ?: ""
                if (query.isNotBlank()) {
                    fetchApellidoSuggestions(query) { suggestions ->
                        runOnUiThread {
                            adapterApellidoMaterno.clear()
                            adapterApellidoMaterno.addAll(suggestions)
                            adapterApellidoMaterno.notifyDataSetChanged()
                            // Mostrar dropdown solo si tiene foco
                            if (autoText.hasFocus() && suggestions.isNotEmpty()) {
                                autoText.showDropDown()
                            }
                        }
                    }
                }
            }
        })

        autoText.setOnItemClickListener { _, _, _, _ ->
            ocultarTeclado(autoText)
            autoText.dismissDropDown()
            binding.etDescripcion.requestFocus()
        }

        autoText.setOnEditorActionListener { _, _, _ ->
            ocultarTeclado(autoText)
            autoText.dismissDropDown()
            binding.etDescripcion.requestFocus()
            true
        }
    }





    // ---------------------- FUNCIONES DE LLAMADA A API ----------------------
    private fun fetchNombreSuggestions(query: String, callback: (List<String>) -> Unit) {
        val url = "https://aplicativos.ags.gob.mx/wssiac/api/CatalogosEX/ObtenerCatalogosNombre?nombre=$query"

        val request = okhttp3.Request.Builder().url(url).build()
        val client = okhttp3.OkHttpClient()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread { callback(emptyList()) }
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                response.body?.string()?.let { body ->
                    try {
                        val jsonArray = org.json.JSONArray(body)
                        val list = mutableListOf<String>()
                        for (i in 0 until jsonArray.length()) {
                            val obj = jsonArray.getJSONObject(i)
                            list.add(obj.getString("nombre1"))
                        }

                        // 🔹 Reordenar: primero los que comienzan con el texto escrito
                        val orderedList = list.sortedWith(
                            compareByDescending<String> { it.startsWith(query, ignoreCase = true) }
                                .thenBy { it }
                        )

                        runOnUiThread { callback(orderedList) }
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                        runOnUiThread { callback(emptyList()) }
                    }
                } ?: runOnUiThread { callback(emptyList()) }
            }
        })
    }

    private fun fetchApellidoSuggestions(query: String, callback: (List<String>) -> Unit) {
        val url = "https://aplicativos.ags.gob.mx/wssiac/api/CatalogosEX/ObtenerCatalogosApellido?apellido=$query"

        val request = okhttp3.Request.Builder().url(url).build()
        val client = okhttp3.OkHttpClient()
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
                runOnUiThread { callback(emptyList()) }
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                response.body?.string()?.let { body ->
                    try {
                        val jsonArray = org.json.JSONArray(body)
                        val list = mutableListOf<String>()
                        for (i in 0 until jsonArray.length()) {
                            val obj = jsonArray.getJSONObject(i)
                            list.add(obj.getString("apellido1"))
                        }

                        // 🔹 Reordenar: primero los que comienzan con el texto escrito
                        val orderedList = list.sortedWith(
                            compareByDescending<String> { it.startsWith(query, ignoreCase = true) }
                                .thenBy { it }
                        )

                        runOnUiThread { callback(orderedList) }
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                        runOnUiThread { callback(emptyList()) }
                    }
                } ?: runOnUiThread { callback(emptyList()) }
            }
        })
    }


    // ---------------------- OCULTAR TECLADO ----------------------
    private fun ocultarTeclado(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun mostrarBitacoraPorTelefono(telefono: String) {

        // 🔹 OCULTAR TECLADO
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            view.clearFocus()
        }

        val rootModal = findViewById<FrameLayout>(R.id.rootModalTablaBitacora)
        val contenedor = findViewById<LinearLayout>(R.id.layoutTabla)
        val modalLoader = findViewById<FrameLayout>(R.id.rootModalLoader)   // ⬅️ AÑADIR

        contenedor.removeAllViews()

        // 🔥 MOSTRAR LOADER ANTES DE LLAMAR LA API
        modalLoader.visibility = View.VISIBLE   // ⬅️ AÑADIR



        val telefonoLimpio = telefono.replace(Regex("[^0-9]"), "")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getBitacora(telefonoLimpio)

                withContext(Dispatchers.Main) {
                    // 🔥 OCULTAR LOADER CUANDO TERMINA LA PETICIÓN
                    modalLoader.visibility = View.GONE   // ⬅️ AÑADIR AQUI

                    if (response.isSuccessful) {
                        val body = response.body()
                        val lista = body?.data ?: emptyList()

                        rootModal.visibility = View.VISIBLE   // 🔹 MOSTRAR MODAL PRINCIPAL

                        if (lista.isEmpty()) {
                            Toast.makeText(
                                this@MainActivity8,
                                "No se encontraron registros",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            lista.forEach { item ->
                                agregarFilaBitacora(item)
                            }
                        }

                        val tvTotalRegistros = findViewById<TextView>(R.id.tvTotalRegistros)
                        val total = body?.total ?: lista.size
                        tvTotalRegistros.text = "Total: $total registros"

                    } else {
                        Toast.makeText(
                            this@MainActivity8,
                            "Error al cargar bitácora",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    modalLoader.visibility = View.GONE   // ⬅️ TAMBIÉN AQUI
                    Toast.makeText(
                        this@MainActivity8,
                        "Error de conexión: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }



    private fun normalizarClave(texto: String?): String {
        if (texto.isNullOrBlank()) return ""

        return Normalizer.normalize(texto, Normalizer.Form.NFD)
            .replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")
            .trim()
            .lowercase(Locale.getDefault())
            .replace(Regex("\\s+"), " ")
    }

    private fun normalizarTextoBitacora(texto: String?): String {
        if (texto.isNullOrBlank()) return ""

        val sinAcentos = Normalizer.normalize(texto, Normalizer.Form.NFD)
            .replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")

        return sinAcentos
            .trim()
            .lowercase(Locale.getDefault())
            .replace(Regex("\\s+"), " ")
            .split(" ")
            .joinToString(" ") { palabra ->
                palabra.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                }
            }
    }


    data class CategoriaReporteUI2(
        val nombre: String,
        val colorSuperior: Int,
        val colorInferior: Int,
        val icono: Int
    )

    data class SubCategoriaReporteUI2(
        val nombre: String,
        val icono: Int
    )
    private fun obtenerTablaCategorias(): List<CategoriaReporteUI2> {
        return listOf(
            CategoriaReporteUI2("Alumbrado", R.color.color_alumbrado_superior, R.color.color_alumbrado_inferior, R.drawable.encalumbrado),
            CategoriaReporteUI2("Calles", R.color.color_calles_superior, R.color.color_calles_inferior, R.drawable.calles_sn),
            CategoriaReporteUI2("Miaa", R.color.color_miaa_superior, R.color.color_miaa_inferior, R.drawable.aguaenc),
            CategoriaReporteUI2("Apoyos", R.color.color_apoyos_superior, R.color.color_apoyos_inferior, R.drawable.encapoyos),
            CategoriaReporteUI2("Parques y Jardines", R.color.color_parques_y_jardines_superior, R.color.color_parques_y_jardines_inferior, R.drawable.encparques_y_jardines),
            CategoriaReporteUI2("Limpia", R.color.color_limpia_superior, R.color.color_limpia_inferior, R.drawable.enclimpieza),
            CategoriaReporteUI2("Vigilancia", R.color.color_vigilancia_superior, R.color.color_vigilancia_inferior, R.drawable.encvigilancia),
            CategoriaReporteUI2("Convivencia", R.color.color_convivencia_superior, R.color.color_convivencia_inferior, R.drawable.enccovivencia),
            CategoriaReporteUI2("Ciudad", R.color.color_ciudad_superior, R.color.color_ciudad_inferior, R.drawable.encciudad)
        )
    }


    private fun obtenerSubCategoriasPorCategoria(
        nombreCategoria: String?
    ): List<SubCategoriaReporteUI2> {

        return when (normalizarClave(nombreCategoria)) {

            "alumbrado" -> listOf(
                SubCategoriaReporteUI2("Luminarias", R.drawable.luminaria),
                SubCategoriaReporteUI2("Espacios Comunes", R.drawable.espacios_comunes),
                SubCategoriaReporteUI2("Mejoras", R.drawable.mejoras),
                SubCategoriaReporteUI2("Apagón", R.drawable.apagon),
                SubCategoriaReporteUI2("Arbotantes", R.drawable.arbotantes),
                SubCategoriaReporteUI2("Postes", R.drawable.postes)
            )

            "calles" -> listOf(
                SubCategoriaReporteUI2("Baches", R.drawable.baches),
                SubCategoriaReporteUI2("Banquetas", R.drawable.banquetas),
                SubCategoriaReporteUI2("Topes", R.drawable.topes),
                SubCategoriaReporteUI2("Rampas", R.drawable.rampas),
                SubCategoriaReporteUI2("Mantenimiento de Señalamientos", R.drawable.mantenimiento_de_senialamientos),
                SubCategoriaReporteUI2("Mantenimiento", R.drawable.mantenimiento_de_calles),
                SubCategoriaReporteUI2("Limpieza", R.drawable.limpieza),
                SubCategoriaReporteUI2("Escombro", R.drawable.escombros),
                SubCategoriaReporteUI2("Rehabilitación de Fachadas", R.drawable.rehabilitaciondefachadas)
            )

            "miaa" -> listOf(
                SubCategoriaReporteUI2("Drenaje", R.drawable.drenaje),
                SubCategoriaReporteUI2("Fugas de Agua", R.drawable.fuga_de_agua),
                SubCategoriaReporteUI2("Obra Inconclusa", R.drawable.obras_inconclusas),
                SubCategoriaReporteUI2("Falta de Agua", R.drawable.falta_de_agua),
                SubCategoriaReporteUI2("Alcantarillas", R.drawable.alcantarillas),
                SubCategoriaReporteUI2("Instalación", R.drawable.instalacion)
            )

            "apoyos" -> listOf(
                SubCategoriaReporteUI2("Económico", R.drawable.recurso_1),
                SubCategoriaReporteUI2("Ortopédicos", R.drawable.ortopedicos),
                SubCategoriaReporteUI2("Pañales", R.drawable.paniales),
                SubCategoriaReporteUI2("Alimentos", R.drawable.alimentos),
                SubCategoriaReporteUI2("Asesoría", R.drawable.asesoria),
                SubCategoriaReporteUI2("Servicios Médicos", R.drawable.servicios_medicos)
            )

            "parques y jardines" -> listOf(
                SubCategoriaReporteUI2("Árbol de tu Casa", R.drawable.arbol_de_tu_casa),
                SubCategoriaReporteUI2("Poda", R.drawable.poda),
                SubCategoriaReporteUI2("Áreas Verdes", R.drawable.areas_verdes),
                SubCategoriaReporteUI2("Rehabilitación", R.drawable.rehabilitacion),
                SubCategoriaReporteUI2("Desmalezado", R.drawable.desmalezado),
                SubCategoriaReporteUI2("Equipamiento", R.drawable.equipamiento),
                SubCategoriaReporteUI2("Camellones", R.drawable.camellones),
                SubCategoriaReporteUI2("Recolección", R.drawable.recoleccion_de_ramas_puntero_r)
            )

            "limpia" -> listOf(
                SubCategoriaReporteUI2("Muebles", R.drawable.muebles),
                SubCategoriaReporteUI2("Contenedores", R.drawable.contenedor),
                SubCategoriaReporteUI2("Basura", R.drawable.basura),
                SubCategoriaReporteUI2("Recolección de Residuos", R.drawable.recoleccion),
                SubCategoriaReporteUI2("Limpieza de Espacios", R.drawable.limpieza_areas)
            )

            "vigilancia" -> listOf(
                SubCategoriaReporteUI2("Vigilancia", R.drawable.vigilancia),
                SubCategoriaReporteUI2("Vehículos Abandonados", R.drawable.vehiculos_abandonados),
                SubCategoriaReporteUI2("Apoyo Vial", R.drawable.apoyo_vial),
                SubCategoriaReporteUI2("Mejoras Viales", R.drawable.mejoras_viales)
            )

            "convivencia" -> listOf(
                SubCategoriaReporteUI2("Ruido", R.drawable.ruido),
                SubCategoriaReporteUI2("Mercados", R.drawable.mercados),
                SubCategoriaReporteUI2("Permisos", R.drawable.permisos)
            )

            "ciudad" -> listOf(
                SubCategoriaReporteUI2("Problemas Vecinales", R.drawable.problemas_vecinales),
                SubCategoriaReporteUI2("Obra", R.drawable.obras),
                SubCategoriaReporteUI2("Aclaración", R.drawable.aclaracion),
                SubCategoriaReporteUI2("Uso de Suelo", R.drawable.uso_de_suelo),
                SubCategoriaReporteUI2("Escombro Vía Pública", R.drawable.escombro_via_publica),
                SubCategoriaReporteUI2("Fraccionamiento", R.drawable.fraccionamiento),
                SubCategoriaReporteUI2("Fincas en riesgo", R.drawable.fincas_en_riesgo)
            )

            else -> emptyList()
        }
    }

    private fun resolverCategoriaBitacora(
        nombreCategoria: String?
    ): CategoriaReporteUI2 {

        val clave = normalizarClave(nombreCategoria)

        return obtenerTablaCategorias()
            .firstOrNull { normalizarClave(it.nombre) == clave }
            ?: CategoriaReporteUI2(
                nombre = normalizarTextoBitacora(nombreCategoria),
                colorSuperior = android.R.color.darker_gray,
                colorInferior = android.R.color.darker_gray,
                icono = android.R.drawable.ic_menu_info_details
            )
    }

    private fun resolverSubCategoriaBitacora(
        categoria: String?,
        subCategoria: String?
    ): SubCategoriaReporteUI2 {

        val claveSub = normalizarClave(subCategoria)

        return obtenerSubCategoriasPorCategoria(categoria)
            .firstOrNull { normalizarClave(it.nombre) == claveSub }
            ?: SubCategoriaReporteUI2(
                nombre = normalizarTextoBitacora(subCategoria),
                icono = android.R.drawable.ic_menu_info_details
            )
    }


    private fun agregarFilaBitacora(item: BitacoraResponse) {

///////////


            val inflater = LayoutInflater.from(this)
            val filaView = inflater.inflate(R.layout.item_fila_bitacora, null)

            val tvCategoria = filaView.findViewById<TextView>(R.id.tvCategoriaVistaBitacora)
            val tvSubcategoria = filaView.findViewById<TextView>(R.id.tvSubcategoriaVistaBitacora)
            val iconCategoria = filaView.findViewById<ImageView>(R.id.iconCategoriaVistaBitacora)
            val iconSubcategoria = filaView.findViewById<ImageView>(R.id.iconSubcategoriaVistaBitacora)
            val viewColorCategoria = filaView.findViewById<View>(R.id.viewColorCategoriaBitacora)

            // ✅ USAR LOS CAMPOS REALES
            val categoriaUI2 = resolverCategoriaBitacora(item.agrupacion)
            val subCategoriaUI2 = resolverSubCategoriaBitacora(
                item.agrupacion,
                item.expr1
            )

            tvCategoria.text = categoriaUI2.nombre
            iconCategoria.setImageResource(categoriaUI2.icono)

            viewColorCategoria.setBackgroundColor(
                ContextCompat.getColor(this, categoriaUI2.colorSuperior)
            )

            tvSubcategoria.text ="  "+ subCategoriaUI2.nombre
            iconSubcategoria.setImageResource(subCategoriaUI2.icono)



9
//////////





        //////////




////////////



        val txtFolioGuia = filaView.findViewById<TextView>(R.id.txtFolioGuia)
        val txtNombreCompleto = filaView.findViewById<TextView>(R.id.txtNombreCompletoFila)
       // val txtPeticion = filaView.findViewById<TextView>(R.id.txtPeticionFila)
        val icExpand = filaView.findViewById<ImageView>(R.id.icExpand)
        val layoutDetalle = filaView.findViewById<LinearLayout>(R.id.layoutDetalle)
        val cardBitacora = filaView.findViewById<MaterialCardView>(R.id.cardBitacora)




        // 🔹 Nombre completo
        val nombreMostrar = item.nombreCompleto
            ?: "${item.nombre} ${item.aPaterno} ${item.aMaterno}"

        // ===========================
        // ENCABEZADO
        // ===========================
        txtFolioGuia.text = "Folio Guía: ${formatearFolioGuia(item.folioGuia)}"
        txtNombreCompleto.text = "Nombre: $nombreMostrar"
        // ===========================
        // ENCABEZADO
        // ===========================
        txtFolioGuia.text = "Folio Guía: ${formatearFolioGuia(item.folioGuia)}"
        txtNombreCompleto.text = "Nombre: $nombreMostrar"





        // ===========================
        // DETALLE
        // ===========================
      //  filaView.findViewById<TextView>(R.id.txtPeticionFila).text =
         //   "Petición: ${capitalizarTexto(item.peticion)}"

        val fh = formatearFecha(item.fechaPeticion)

        filaView.findViewById<TextView>(R.id.txtFechaFila).text = "${fh.fecha}"
        filaView.findViewById<TextView>(R.id.txtHoraFila).text = "${fh.hora}"


        filaView.findViewById<TextView>(R.id.txtEstatusFila).text =
            "Estatus: ${capitalizarTexto(formatearEstatus(item.staPeticion, item.staRes))}"

        filaView.findViewById<TextView>(R.id.txtObsFila).text =
            " ${capitalizarTexto(item.obsPet)}"


        val respuesta = item.obsResp?.takeIf { it.isNotBlank() }?.let { capitalizarTexto(it) }
            ?: "Sin respuesta"

        filaView.findViewById<TextView>(R.id.txtResFila).text = " $respuesta"


        filaView.findViewById<TextView>(R.id.txtFolioFila).text =
            "Folio: ${item.folio ?: ""}"

        // 🔥 Dirección formateada + capitalizada
        filaView.findViewById<TextView>(R.id.txtDireccionCompleta).text =
            "${capitalizarTexto(formatearDireccion(item.desCalle, item.desFrac, item.numExt?.trim()))}"


        filaView.findViewById<TextView>(R.id.txtDesCalle).text =
            "Calle: ${capitalizarTexto(item.desCalle)}"

        filaView.findViewById<TextView>(R.id.txtDesFrac).text =
            "Fraccionamiento: ${capitalizarTexto(item.desFrac)}"

        filaView.findViewById<TextView>(R.id.txtNumExt).text =
            "Número Exterior: ${item.numExt ?: ""}"

        filaView.findViewById<TextView>(R.id.txtNumInt).text =
            "Número Interior: ${item.numInt ?: ""}"

        filaView.findViewById<TextView>(R.id.txtIdConversacion).text =
            "ID Conversación: ${item.idConversacion ?: ""}"

        val tvTotalRegistros = findViewById<TextView>(R.id.tvTotalRegistros)



        val estatus = formatearEstatus(item.staPeticion, item.staRes)
        val color = obtenerColorEstatus(estatus)

        val txtEstatus = filaView.findViewById<TextView>(R.id.txtEstatusFila)

// 🔥 Cambiar color sin perder forma redondeada
        txtEstatus.background.mutate().setTint(color)

        txtEstatus.setTextColor(Color.WHITE)




        // ===========================
        // EXPANDIR / COLAPSAR
        // ===========================
        icExpand.setOnClickListener {
            if (layoutDetalle.visibility == View.GONE) {

                layoutDetalle.visibility = View.VISIBLE
                icExpand.rotation = 180f

                val scrollView = findViewById<ScrollView>(R.id.scrollTablaBitacora)

                // 🔥 Scroll exacto a la posición del card
                scrollView?.post {
                    scrollView.smoothScrollTo(0, filaView.top)
                }

            } else {
                layoutDetalle.visibility = View.GONE
                icExpand.rotation = 0f
            }
        }


        // 🔥 Mostrar siempre abierto por defecto
        layoutDetalle.visibility = View.VISIBLE
        icExpand.rotation = 180f
        icExpand.visibility = View.GONE   // (Opcional: si no quieres mostrar el botón)



        // ===========================
        // SELECCIÓN VISUAL
        // ===========================
        cardBitacora.setOnClickListener {

            // 🔹 Marcar selección visual
            cardBitacora.strokeColor = Color.parseColor("#FFC107")
            cardBitacora.strokeWidth = 3

            // =============================
            // ✔️ Guardar datos seleccionados
            // =============================
            binding.tvDireccion2.text =
                formatearDireccion(item.desCalle, item.desFrac, item.numExt?.trim())



            selectedColoniaCveFra = item.cveFrac?: ""
            selectedCalleCveVia = item.cveCalle ?: ""
            selecteddesCalle = item.desCalle ?: ""
            selecteddesFracc = item.desFrac ?: ""
            selectedCalleNumero = (item.numExt ?: "").trim()

            selectedLatLng = LatLng(
                item.coordLatitud ?: 0.0,
                item.coordLongitud ?: 0.0
            )

           selectedColoniaCveFra = item.cveFrac
               selectedCalleCveVia =item.desFrac







            // =============================
            // ✔️ OCULTAR MODAL AUTOMÁTICAMENTE
            // =============================
            val rootModal = findViewById<FrameLayout>(R.id.rootModalTablaBitacora)
            binding.tvEtiquetaDireccion.visibility = View.GONE
            rootModal.visibility = View.GONE

        }


        val contenedor = findViewById<LinearLayout>(R.id.layoutTabla)
        contenedor.addView(filaView)
    }


    private fun verificarBitacora(telefono: String, btnAmarillo: ImageButton) {

        val modalLoader = findViewById<FrameLayout>(R.id.rootModalLoader)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etApellidoPaterno = findViewById<EditText>(R.id.etApellidoPaterno)
        val etApellidoMaterno = findViewById<EditText>(R.id.etApellidoMaterno)

        lifecycleScope.launch {

            // 🔹 Ocultar el teclado si hay un campo enfocado
            currentFocus?.let { view ->
                ocultarTeclado(view)
            }

            // 🔹 Mostrar modal (loader)
            modalLoader.visibility = View.VISIBLE

            try {
                val response = apiService.getBitacora(telefono)

                if (response.isSuccessful && response.body() != null) {

                    val data = response.body()
                    val registros = data?.data

                    if (!registros.isNullOrEmpty()) {

                        btnAmarillo.visibility = View.VISIBLE

                        val registro = registros.first()

                        // 🔹 Si el valor viene NULL, será ""
                        val nombreSolo = registro.nombre ?: ""
                        val ap = registro.aPaterno ?: ""
                        val am = registro.aMaterno ?: ""

                        etNombre.setText(nombreSolo)
                        etApellidoPaterno.setText(ap)
                        etApellidoMaterno.setText(am)
                        binding.etDescripcion.requestFocus()

                    } else {
                        // 🔹 No hay registros → limpiar campos
                        btnAmarillo.visibility = View.GONE
                        etNombre.setText("")
                        etApellidoPaterno.setText("")
                        etApellidoMaterno.setText("")
                        binding.etNombre.requestFocus()
                    }

                } else {
                    // 🔹 Respuesta no válida → limpiar
                    btnAmarillo.visibility = View.GONE
                    etNombre.setText("")
                    etApellidoPaterno.setText("")
                    etApellidoMaterno.setText("")
                }

            } catch (e: Exception) {
                e.printStackTrace()

                // 🔹 Error → limpiar
                btnAmarillo.visibility = View.GONE
                etNombre.setText("")
                etApellidoPaterno.setText("")
                etApellidoMaterno.setText("")

            } finally {
                // 🔹 Ocultar modal SIEMPRE
                modalLoader.visibility = View.GONE
            }
        }
    }

    data class FechaHora(
        val fecha: String,
        val hora: String
    )

    private fun formatearFecha(fecha: String?): FechaHora {
        if (fecha.isNullOrBlank()) return FechaHora("", "")

        return try {
            val entrada = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val date = entrada.parse(fecha)!!

            val formatoFecha = SimpleDateFormat("dd MMMM yyyy", Locale("es", "MX"))
            val formatoHora = SimpleDateFormat("h:mm a", Locale("es", "MX"))

            val fechaFormateada = formatoFecha.format(date)

            val horaRaw = formatoHora.format(date)
            val horaLimpia = horaRaw
                .replace("a. m.", "AM")
                .replace("p. m.", "PM")
                .replace("a.m.", "AM")
                .replace("p.m.", "PM")

            FechaHora(fechaFormateada, horaLimpia)

        } catch (e: Exception) {
            FechaHora("", "")
        }
    }



    private fun formatearEstatus(pet: String?, res: String?): String {
        return when {
            pet == "A" && res == "+" -> "Atendido"
            pet == "A" && res == "-" -> "Negativo"
            pet == "N" && res.isNullOrBlank() -> "Pendiente"
            pet == "A" && res == "P" -> "Pendiente"
            pet == "R" && res.isNullOrBlank() -> "Canalizar"
            else -> pet ?: ""
        }
    }

    private fun obtenerColorEstatus(estatus: String): Int {
        return when (estatus) {
            "Atendido" -> Color.parseColor("#2E7D32")   // verde
            "Negativo", "Pendiente", "Canalizar" -> Color.parseColor("#C62828") // rojo
            else -> Color.DKGRAY
        }
    }


    private fun formatearDireccion(calle: String?, frac: String?, numExt: String?): String {
        val c = calle ?: ""
        val f = frac ?: ""
        val n = numExt ?: ""

        return buildString {
            if (c.isNotEmpty()) append(c)
            if (n.isNotEmpty()) append(" #$n")
            if (f.isNotEmpty()) append(", $f")
        }.ifEmpty { "" }
    }

    private fun capitalizarTexto(texto: String?): String {
        if (texto.isNullOrBlank()) return ""
        return texto.lowercase()
            .split(" ")
            .joinToString(" ") { palabra ->
                palabra.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
            }
    }


    fun setNext(from: EditText, to: EditText?) {
        from.setOnEditorActionListener { _, actionId, _ ->

            when (actionId) {

                EditorInfo.IME_ACTION_NEXT -> {
                    to?.let { next ->
                        next.isFocusableInTouchMode = true
                        next.requestFocus()

                        next.postDelayed({
                            showKeyboardForce(next)
                        }, 100)
                    }
                    true
                }

                EditorInfo.IME_ACTION_DONE -> {
                    from.clearFocus()
                    hideKeyboard(from)
                    true
                }

                else -> false
            }
        }
    }


    private fun limitarAlturaResultado(scrollView: NestedScrollView, porcentajePantalla: Float = 0.4f) {

        val displayMetrics = resources.displayMetrics
        val alturaPantalla = displayMetrics.heightPixels
        val alturaMaxima = (alturaPantalla * porcentajePantalla).toInt()

        scrollView.post {
            val contenido = scrollView.getChildAt(0)
            if (contenido == null) return@post

            val alturaContenido = contenido.measuredHeight

            val params = scrollView.layoutParams
            params.height =
                if (alturaContenido > alturaMaxima) {
                    alturaMaxima       // 🔒 Limita y activa scroll
                } else {
                    ViewGroup.LayoutParams.WRAP_CONTENT // ✅ Se adapta sin espacio
                }

            scrollView.layoutParams = params
        }
    }



    private fun showKeyboardForce(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun setEstadoAnonimo(esAnonimo: Boolean) {

        if (esAnonimo) {
            // 🔒 MODO ANÓNIMO

            // Nombre en negritas
            val textoAnonimo = SpannableString("ANÓNIMO")
            textoAnonimo.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                textoAnonimo.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            binding.etNombre.setText(textoAnonimo)

            // Teléfono muestra ANÓNIMO como hint
            binding.etTelefono2.setText("")
            binding.etTelefono2.hint = "ANÓNIMO"

            binding.etApellidoPaterno.text = null
            binding.etApellidoMaterno.text = null
            binding.etTelefono.setText("44991010")
        } else {
            // 🔓 MODO NORMAL

            binding.etNombre.text = null

            // Restaurar hint original
            binding.etTelefono2.hint = "Teléfono"

            binding.etApellidoPaterno.text = null
            binding.etApellidoMaterno.text = null
            binding.etTelefono2.text = null
            binding.etTelefono.text = null
        }

        listOf(
            binding.etLada,
            binding.etTelefono2,
            binding.etNombre,
            binding.etApellidoPaterno,
            binding.etApellidoMaterno
        ).forEach { editText ->
            editText.isEnabled = !esAnonimo
            editText.alpha = if (esAnonimo) 0.6f else 1f
        }
    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    private fun showLoader(show: Boolean) {
        loader?.visibility = if (show) View.VISIBLE else View.GONE


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_salir -> {
                cerrarSesion()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun validarMostrarAnonimo() {

        val telefono = binding.etTelefono2.text.toString().trim()
        val nombre = binding.etNombre.text.toString().trim()
        val apellidoP = binding.etApellidoPaterno.text.toString().trim()
        val apellidoM = binding.etApellidoMaterno.text.toString().trim()

        val tieneTextoNoPermitido =
            telefono.isNotEmpty() ||
                    apellidoP.isNotEmpty() ||
                    apellidoM.isNotEmpty() ||
                    (nombre.isNotEmpty() && nombre != "ANÓNIMO")

        binding.cbAnonimo.visibility =
            if (tieneTextoNoPermitido) View.GONE else View.VISIBLE
    }

    private fun salirDeAnonimo() {

        // 🔕 Ya no es anónimo
        esAnonimo = false

        // ⚫ Estado visual APAGADO
        binding.cbAnonimo.backgroundTintList =
            ContextCompat.getColorStateList(
                this,
                R.color.coloranonimoapa
            )

        binding.cbAnonimo.setColorFilter(
            ContextCompat.getColor(this, R.color.white)
        )

        // 🔄 Restaurar campos y estados
        setEstadoAnonimo(false)

        // 👁️ Revalidar visibilidad del botón
        validarMostrarAnonimo()
    }


    private fun verificarVersion() {
        lifecycleScope.launch {
            try {
                val response = apiService.getVersion(
                    version = VersionConfig.APP_VERSION,
                    aplicaionIDC = VersionConfig.APLICACION_IDC,
                    rolIdIDC = VersionConfig.ROL_IDC
                )

                // 🔍 1️⃣ IMPRIMIR TODO EL OBJETO RESPONSE
                Log.e("VERSION_API", "RESPONSE COMPLETO: $response")
                Log.e(
                    "VERSION_API",
                    "DETALLE -> code=${response.code()} body=${response.body()} isSuccessful=${response.isSuccessful}"
                )

                // 🔎 2️⃣ DECISIÓN
                if (response.isSuccessful && response.body() == true) {
                    Log.e("VERSION_API", "✔️ ENTRA → versión válida (NO se cierra sesión)")
                    return@launch
                }

                Log.e("VERSION_API", "❌ SALE → versión inválida o error (se cierra sesión)")
                cerrarSesion()

            } catch (e: Exception) {
                Log.e("VERSION_API", "🔥 EXCEPCIÓN", e)
                cerrarSesion()
            }
        }
    }




    private fun cerrarSesion() {

        showLoader(true)

        loader?.postDelayed({

            getSharedPreferences("SESSION", MODE_PRIVATE)
                .edit()
                .clear()
                .apply()

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()

        }, 500)
    }




}

