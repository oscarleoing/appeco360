package com.example.pro

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

// ------------------ DATOS ------------------
data class CategoriaReporteUI(
    val nombre: String,
    val colorSuperior: Int,
    val colorInferior: Int,
    val icono: Int
)

data class SubCategoriaReporteUI(
    val nombre: String,
    val icono: Int,
    val colorSuperior: Int,
    val colorInferior: Int
)




// ------------------ LISTENER ------------------
interface OnCategoriaSeleccionadaListener {
    fun onCategoriaSeleccionada(categoria: CategoriaReporteUI)
    fun onSubCategoriaSeleccionada(subCategoria: SubCategoriaReporteUI)
}

var listener: OnCategoriaSeleccionadaListener? = null
class MainFragment4 : Fragment() {

    private lateinit var recyclerCarrusel: RecyclerView
    private lateinit var recyclerSubCarrusel: RecyclerView
    private lateinit var layoutSubCarrusel: View
    private lateinit var tvTituloSubCategorias: TextView

    private var categorias: List<CategoriaReporteUI> = emptyList()
    private var subcategorias: MutableList<SubCategoriaReporteUI> = mutableListOf()
    private var categoriaSeleccionada: String? = null
    private var subcategoriaSeleccionada: String? = null

    private lateinit var subcategoriaAdapter: SubcategoriaAdapter
    var listener: OnCategoriaSeleccionadaListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.activity_main4, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        recyclerCarrusel = view.findViewById(R.id.recyclerCarrusel)
        recyclerSubCarrusel = view.findViewById(R.id.recyclerSubCarrusel)
        layoutSubCarrusel = view.findViewById(R.id.layoutSubCarrusel)
        tvTituloSubCategorias = view.findViewById(R.id.tvTituloSubCategorias)

        // Colores iniciales
        layoutSubCarrusel.setBackgroundColor(
            ContextCompat.getColor(requireContext(), R.color.color_alumbrado_inferior)
        )
        tvTituloSubCategorias.setBackgroundColor(
            ContextCompat.getColor(requireContext(), R.color.color_alumbrado_inferior)
        )

        // ---------- CARRUSEL CATEGORÍAS ----------
        categorias = mostrarCarrusel()
        recyclerCarrusel.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerCarrusel.adapter = CategoriaAdapter(categorias)
        recyclerCarrusel.setHasFixedSize(true)

        // ---------- SUBCARRUSEL ----------
        subcategoriaAdapter = SubcategoriaAdapter(subcategorias)
        recyclerSubCarrusel.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerSubCarrusel.adapter = subcategoriaAdapter
        recyclerSubCarrusel.setHasFixedSize(true)
        recyclerSubCarrusel.itemAnimator = null
        recyclerSubCarrusel.setItemViewCacheSize(20)

        // ❗ NO volver a ocultarlo si ya había una categoría seleccionada
        if (categoriaSeleccionada == null) {
            layoutSubCarrusel.visibility = View.GONE
        } else {
            layoutSubCarrusel.visibility = View.VISIBLE
        }

    }

    // ------------------ Centrar item suavemente ------------------
    private fun centrarItemSuavemente(recyclerView: RecyclerView, position: Int) {

        // 🔹 Ocultar teclado y quitar foco al inicio
        val view = activity?.currentFocus
        if (view != null) {
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            view.clearFocus()
        }

        val smoothScroller = object : LinearSmoothScroller(requireContext()) {
            override fun getHorizontalSnapPreference(): Int = SNAP_TO_START
            override fun calculateDtToFit(
                viewStart: Int, viewEnd: Int, boxStart: Int, boxEnd: Int, snapPreference: Int
            ): Int {
                val boxCenter = (boxStart + boxEnd) / 2
                val viewCenter = (viewStart + viewEnd) / 2
                return boxCenter - viewCenter
            }

            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return 120f / displayMetrics.densityDpi
            }
        }
        smoothScroller.targetPosition = position
        recyclerView.layoutManager?.startSmoothScroll(smoothScroller)
    }

    // ------------------ Selección subcategoría ------------------
    private fun setSubcategoriaClick(sub: SubCategoriaReporteUI, position: Int) {
        val prevIndex = subcategorias.indexOfFirst { it.nombre == subcategoriaSeleccionada }
        subcategoriaSeleccionada = sub.nombre

        // 🔹 Solo actualiza los ítems necesarios (no toda la lista)
        if (prevIndex != -1) subcategoriaAdapter.notifyItemChanged(prevIndex)
        subcategoriaAdapter.notifyItemChanged(position)

        recyclerSubCarrusel.post {
            centrarItemSuavemente(recyclerSubCarrusel, position)
        }

        listener?.onSubCategoriaSeleccionada(sub)
    }


    // ------------------ ADAPTER CATEGORÍAS ------------------
    inner class CategoriaAdapter(private val items: List<CategoriaReporteUI>) :
        RecyclerView.Adapter<CategoriaAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val txtTitulo: TextView = itemView.findViewById(R.id.txtTitulo)
            val imgIcono: ImageView = itemView.findViewById(R.id.imgIcono)
            val itemCard: MaterialCardView = itemView.findViewById(R.id.itemCard)
            val layoutSuperior: LinearLayout = itemView.findViewById(R.id.layoutSuperior)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_categoria, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val categoria = items[position]
            holder.txtTitulo.text = categoria.nombre
            holder.imgIcono.setImageResource(categoria.icono)

            val colorSuperior = ContextCompat.getColor(requireContext(), categoria.colorSuperior)
            val colorInferior = ContextCompat.getColor(requireContext(), categoria.colorInferior)
            val isSelected = categoria.nombre == categoriaSeleccionada

            holder.layoutSuperior.setBackgroundColor(
                if (isSelected) colorInferior else colorSuperior
            )

            holder.itemCard.strokeWidth = if (isSelected) 5 else 3
            holder.itemCard.strokeColor =
                if (isSelected) Color.BLACK else colorSuperior

            holder.itemCard.setOnClickListener {
                categoriaSeleccionada = categoria.nombre
                val nuevasSubcategorias = obtenerSubcategorias(categoria.nombre)

                recyclerSubCarrusel.itemAnimator = null
                subcategorias.clear()
                subcategorias.addAll(nuevasSubcategorias)

                subcategoriaSeleccionada = null
                subcategoriaAdapter.actualizarLista(nuevasSubcategorias)

                tvTituloSubCategorias.setBackgroundColor(colorInferior)
                layoutSubCarrusel.setBackgroundColor(colorSuperior)
                layoutSubCarrusel.visibility = View.VISIBLE

                recyclerSubCarrusel.post {
                    recyclerSubCarrusel.scrollToPosition(0)
                }

                notifyDataSetChanged()
                centrarItemSuavemente(recyclerCarrusel, position)
                listener?.onCategoriaSeleccionada(categoria)
            }
        }
    }

    // ------------------ ADAPTER SUBCATEGORÍAS ------------------
    inner class SubcategoriaAdapter(private val items: MutableList<SubCategoriaReporteUI>) :
        RecyclerView.Adapter<SubcategoriaAdapter.ViewHolder>() {

        init {
            setHasStableIds(true)
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val txtSubCategoria: TextView = itemView.findViewById(R.id.txtSubCategoria)
            val imgSubIcono: ImageView = itemView.findViewById(R.id.imgSubIcono)
            val layoutSuperior: View = itemView.findViewById(R.id.layoutSuperior)
            val itemCard: MaterialCardView = itemView.findViewById(R.id.itemCard)
        }

        override fun getItemId(position: Int): Long = items[position].nombre.hashCode().toLong()
        override fun getItemCount(): Int = items.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_subcategoria, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val sub = items[position]
            holder.txtSubCategoria.text = sub.nombre
            holder.imgSubIcono.setImageResource(sub.icono)

            val colorInferior = ContextCompat.getColor(requireContext(), sub.colorInferior)
            val isSelected = sub.nombre == subcategoriaSeleccionada

            holder.layoutSuperior.setBackgroundColor(
                if (isSelected) colorInferior
                else ContextCompat.getColor(requireContext(), android.R.color.transparent)
            )

            holder.itemCard.strokeWidth = if (isSelected) 3 else 2
            holder.itemCard.strokeColor =
                if (isSelected) Color.BLACK else ContextCompat.getColor(
                    requireContext(),
                    android.R.color.darker_gray
                )

            holder.itemCard.setOnClickListener {
                setSubcategoriaClick(sub, position)
            }
        }

        fun actualizarLista(nuevas: List<SubCategoriaReporteUI>) {
            items.clear()
            items.addAll(nuevas)
            subcategoriaSeleccionada = null
            notifyDataSetChanged()
            recyclerSubCarrusel.scrollToPosition(0)
        }
    }


// ------------------ DATOS ------------------


// Mostrar categorías
    private fun mostrarCarrusel(): List<CategoriaReporteUI> {
        return listOf(
            CategoriaReporteUI("Alumbrado", R.color.color_alumbrado_superior, R.color.color_alumbrado_inferior, R.drawable.encalumbrado),
            CategoriaReporteUI("Calles", R.color.color_calles_superior, R.color.color_calles_inferior, R.drawable.calles_sn),
            CategoriaReporteUI("Miaa", R.color.color_miaa_superior, R.color.color_miaa_inferior, R.drawable.aguaenc),
            CategoriaReporteUI("Apoyos", R.color.color_apoyos_superior, R.color.color_apoyos_inferior, R.drawable.encapoyos),
            CategoriaReporteUI("Parques y Jardines", R.color.color_parques_y_jardines_superior, R.color.color_parques_y_jardines_inferior, R.drawable.encparques_y_jardines),
            CategoriaReporteUI("Limpia", R.color.color_limpia_superior, R.color.color_limpia_inferior, R.drawable.enclimpieza),
            CategoriaReporteUI("Vigilancia", R.color.color_vigilancia_superior, R.color.color_vigilancia_inferior, R.drawable.encvigilancia),
            CategoriaReporteUI("Convivencia", R.color.color_convivencia_superior, R.color.color_convivencia_inferior, R.drawable.enccovivencia),
            CategoriaReporteUI("Ciudad", R.color.color_ciudad_superior, R.color.color_ciudad_inferior, R.drawable.encciudad),
      //      CategoriaReporteUI("Otros", R.color.color_otros_superior, R.color.color_otros_inferior, R.drawable.otros)
        )
    }

    // Obtener todas las subcategorías
    private fun obtenerSubcategorias(nombreCategoria: String): List<SubCategoriaReporteUI> {
        return when (nombreCategoria) {
            "Alumbrado" -> listOf(
                SubCategoriaReporteUI("Luminarias", R.drawable.luminaria, R.color.color_alumbrado_superior, R.color.color_alumbrado_inferior),
                SubCategoriaReporteUI("Espacios Comunes", R.drawable.espacios_comunes, R.color.color_alumbrado_superior, R.color.color_alumbrado_inferior),
                SubCategoriaReporteUI("Mejoras", R.drawable.mejoras, R.color.color_alumbrado_superior, R.color.color_alumbrado_inferior),
                SubCategoriaReporteUI("Apagón", R.drawable.apagon, R.color.color_alumbrado_superior, R.color.color_alumbrado_inferior),
                SubCategoriaReporteUI("Arbotantes", R.drawable.arbotantes, R.color.color_alumbrado_superior, R.color.color_alumbrado_inferior),
                SubCategoriaReporteUI("Postes", R.drawable.postes, R.color.color_alumbrado_superior, R.color.color_alumbrado_inferior)
            )
            "Calles" -> listOf(
                SubCategoriaReporteUI("Baches", R.drawable.baches, R.color.color_calles_superior, R.color.color_calles_inferior),
                SubCategoriaReporteUI("Banquetas", R.drawable.banquetas, R.color.color_calles_superior, R.color.color_calles_inferior),
                SubCategoriaReporteUI("Topes", R.drawable.topes, R.color.color_calles_superior, R.color.color_calles_inferior),
                SubCategoriaReporteUI("Rampas", R.drawable.rampas, R.color.color_calles_superior, R.color.color_calles_inferior),
                SubCategoriaReporteUI("Mantenimiento de Señalamientos", R.drawable.mantenimiento_de_senialamientos, R.color.color_calles_superior, R.color.color_calles_inferior),
                SubCategoriaReporteUI("Mantenimiento", R.drawable.mantenimiento_de_calles, R.color.color_calles_superior, R.color.color_calles_inferior),
                SubCategoriaReporteUI("Limpieza", R.drawable.limpieza, R.color.color_calles_superior, R.color.color_calles_inferior),
                SubCategoriaReporteUI("Escombro", R.drawable.escombros, R.color.color_calles_superior, R.color.color_calles_inferior),
                SubCategoriaReporteUI("Rehabilitación de Fachadas", R.drawable.rehabilitaciondefachadas, R.color.color_calles_superior, R.color.color_calles_inferior)
            )
            "Miaa" -> listOf(
                SubCategoriaReporteUI("Drenaje", R.drawable.drenaje, R.color.color_miaa_superior, R.color.color_miaa_inferior),
                SubCategoriaReporteUI("Fugas de Agua", R.drawable.fuga_de_agua, R.color.color_miaa_superior, R.color.color_miaa_inferior),
                SubCategoriaReporteUI("Obra Inconclusa", R.drawable.obras_inconclusas, R.color.color_miaa_superior, R.color.color_miaa_inferior),
                SubCategoriaReporteUI("Falta de Agua", R.drawable.falta_de_agua, R.color.color_miaa_superior, R.color.color_miaa_inferior),
                SubCategoriaReporteUI("Alcantarillas", R.drawable.alcantarillas, R.color.color_miaa_superior, R.color.color_miaa_inferior),
                SubCategoriaReporteUI("Instalación", R.drawable.instalacion, R.color.color_miaa_superior, R.color.color_miaa_inferior)
            )
            "Apoyos" -> listOf(
                SubCategoriaReporteUI("Económicos", R.drawable.recurso_1, R.color.color_apoyos_superior, R.color.color_apoyos_inferior),
                SubCategoriaReporteUI("Ortopédicos", R.drawable.ortopedicos, R.color.color_apoyos_superior, R.color.color_apoyos_inferior),
                SubCategoriaReporteUI("Pañales", R.drawable.paniales, R.color.color_apoyos_superior, R.color.color_apoyos_inferior),
                SubCategoriaReporteUI("Alimentos", R.drawable.alimentos, R.color.color_apoyos_superior, R.color.color_apoyos_inferior),
                SubCategoriaReporteUI("Asesoría", R.drawable.asesoria, R.color.color_apoyos_superior, R.color.color_apoyos_inferior),
                SubCategoriaReporteUI("Servicios Médicos", R.drawable.servicios_medicos, R.color.color_apoyos_superior, R.color.color_apoyos_inferior)
            )
            "Parques y Jardines" -> listOf(
                SubCategoriaReporteUI("Árbol de tu Casa", R.drawable.arbol_de_tu_casa, R.color.color_parques_y_jardines_superior, R.color.color_parques_y_jardines_inferior),
                SubCategoriaReporteUI("Poda", R.drawable.poda, R.color.color_parques_y_jardines_superior, R.color.color_parques_y_jardines_inferior),
                SubCategoriaReporteUI("Áreas Verdes", R.drawable.areas_verdes, R.color.color_parques_y_jardines_superior, R.color.color_parques_y_jardines_inferior),
                SubCategoriaReporteUI("Rehabilitación", R.drawable.rehabilitacion, R.color.color_parques_y_jardines_superior, R.color.color_parques_y_jardines_inferior),
                SubCategoriaReporteUI("Desmalezado", R.drawable.desmalezado, R.color.color_parques_y_jardines_superior, R.color.color_parques_y_jardines_inferior),
                SubCategoriaReporteUI("Equipamiento", R.drawable.equipamiento, R.color.color_parques_y_jardines_superior, R.color.color_parques_y_jardines_inferior),
                SubCategoriaReporteUI("Camellones", R.drawable.camellones, R.color.color_parques_y_jardines_superior, R.color.color_parques_y_jardines_inferior),
                SubCategoriaReporteUI("Recolección", R.drawable.recoleccion_de_ramas_puntero_r, R.color.color_parques_y_jardines_superior, R.color.color_parques_y_jardines_inferior),
             //   SubCategoriaReporteUI("Otros", R.drawable.otros, R.color.color_parques_y_jardines_superior, R.color.color_parques_y_jardines_inferior)
            )
            "Limpia" -> listOf(
                SubCategoriaReporteUI("Muebles", R.drawable.muebles, R.color.color_limpia_superior, R.color.color_limpia_inferior),
                SubCategoriaReporteUI("Contenedores", R.drawable.contenedor, R.color.color_limpia_superior, R.color.color_limpia_inferior),
                SubCategoriaReporteUI("Basura", R.drawable.basura, R.color.color_limpia_superior, R.color.color_limpia_inferior),
                SubCategoriaReporteUI("Recolección de Residuos", R.drawable.recoleccion, R.color.color_limpia_superior, R.color.color_limpia_inferior),
                SubCategoriaReporteUI("Limpieza de Espacios", R.drawable.limpieza_areas, R.color.color_limpia_superior, R.color.color_limpia_inferior),
              //  SubCategoriaReporteUI("Contenedores de Áreas", R.drawable.contenedor, R.color.color_limpia_superior, R.color.color_limpia_inferior)
            )
            "Vigilancia" -> listOf(
                SubCategoriaReporteUI("Vigilancia", R.drawable.vigilancia, R.color.color_vigilancia_superior, R.color.color_vigilancia_inferior),
                SubCategoriaReporteUI("Vehículos Abandonados", R.drawable.vehiculos_abandonados, R.color.color_vigilancia_superior, R.color.color_vigilancia_inferior),
                SubCategoriaReporteUI("Apoyo Vial", R.drawable.apoyo_vial, R.color.color_vigilancia_superior, R.color.color_vigilancia_inferior),
                SubCategoriaReporteUI("Mejoras Viales", R.drawable.mejoras_viales, R.color.color_vigilancia_superior, R.color.color_vigilancia_inferior)
            )
            "Convivencia" -> listOf(
                SubCategoriaReporteUI("Ruido", R.drawable.ruido, R.color.color_convivencia_superior, R.color.color_convivencia_inferior),
                SubCategoriaReporteUI("Mercados", R.drawable.mercados, R.color.color_convivencia_superior, R.color.color_convivencia_inferior),
                SubCategoriaReporteUI("Permisos", R.drawable.permisos, R.color.color_convivencia_superior, R.color.color_convivencia_inferior)
            )
            "Ciudad" -> listOf(
                SubCategoriaReporteUI("Problemas Vecinales", R.drawable.problemas_vecinales, R.color.color_ciudad_superior, R.color.color_ciudad_inferior),
                SubCategoriaReporteUI("Obra", R.drawable.obras, R.color.color_ciudad_superior, R.color.color_ciudad_inferior),
                SubCategoriaReporteUI("Aclaración", R.drawable.aclaracion, R.color.color_ciudad_superior, R.color.color_ciudad_inferior),
                SubCategoriaReporteUI("Uso de Suelo", R.drawable.uso_de_suelo, R.color.color_ciudad_superior, R.color.color_ciudad_inferior),
                SubCategoriaReporteUI("Escombro Vía Pública", R.drawable.escombro_via_publica, R.color.color_ciudad_superior, R.color.color_ciudad_inferior),
                SubCategoriaReporteUI("Fraccionamiento", R.drawable.fraccionamiento, R.color.color_ciudad_superior, R.color.color_ciudad_inferior),
                SubCategoriaReporteUI("Fincas en riesgo", R.drawable.fincas_en_riesgo, R.color.color_ciudad_superior, R.color.color_ciudad_inferior),


                //     SubCategoriaReporteUI("Otros", R.drawable.otros, R.color.color_ciudad_superior, R.color.color_ciudad_inferior)
            )
          //  "Otros" -> listOf(
           //     SubCategoriaReporteUI("Otros", R.drawable.otros, R.color.color_otros_superior, R.color.color_otros_inferior)
           // )
            else -> emptyList()
        }
    }
}
