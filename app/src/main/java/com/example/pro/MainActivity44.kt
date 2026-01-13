package com.example.pro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pro.databinding.ItemCategoriaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainFragment44 : Fragment() {

    private lateinit var recyclerCarrusel: RecyclerView

    // Variables globales para guardar los datos seleccionados
    private var idAgrupacionSeleccionada: Int? = null
    private var nombreAgrupacionSeleccionada: String? = null
    private var inactivoSeleccionado: Boolean? = null

    // ==================== 🔹 RetrofitClient integrado ====================
    object RetrofitClient {
        private const val BASE_URL = "https://aplicativos.ags.gob.mx/"

        val apiService: ApiService by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }

    // ==================== Ciclo de vida del Fragment ====================
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista = inflater.inflate(R.layout.activity_main44, container, false)
        recyclerCarrusel = vista.findViewById(R.id.recyclerCarrusel)
        recyclerCarrusel.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        return vista
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        obtenerAgrupaciones()
    }

    // ==================== 🔹 Función para obtener agrupaciones ====================
    private fun obtenerAgrupaciones() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.getAgrupaciones()
                if (response.isSuccessful) {
                    val listaAgrupaciones = response.body() ?: emptyList()
                    withContext(Dispatchers.Main) {
                        mostrarAgrupaciones(listaAgrupaciones)
                    }
                } else {
                    Log.e("MainFragment44", "❌ Error al obtener agrupaciones: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("MainFragment44", "⚠️ Excepción: ${e.message}")
            }
        }
    }

    // ==================== 🔹 Mostrar agrupaciones en Recycler ====================
    private fun mostrarAgrupaciones(lista: List<Agrupacion>) {
        val adapter = object : RecyclerView.Adapter<CategoriaViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
                val binding = ItemCategoriaBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return CategoriaViewHolder(binding)
            }

            override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
                holder.bind(lista[position])
            }

            override fun getItemCount(): Int = lista.size
        }

        recyclerCarrusel.adapter = adapter
    }

    // ==================== 🔹 ViewHolder interno ====================
    inner class CategoriaViewHolder(private val binding: ItemCategoriaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Agrupacion) {
            binding.txtTitulo.text = item.agrupacion
            binding.imgIcono.setImageResource(R.drawable.encalumbrado)

            // Si está inactivo, mostrar semitransparente
            binding.itemCard.alpha = if (item.inactivo) 0.4f else 1f

            binding.itemCard.setOnClickListener {
                idAgrupacionSeleccionada = item.idAgrupacion
                nombreAgrupacionSeleccionada = item.agrupacion
                inactivoSeleccionado = item.inactivo

                Log.d(
                    "AgrupacionSeleccionada",
                    "✅ ID: $idAgrupacionSeleccionada | Nombre: $nombreAgrupacionSeleccionada | Inactivo: $inactivoSeleccionado"
                )

                // 🔹 Aquí puedes invocar la carga de clasificaciones si lo deseas:
                // obtenerClasificaciones(idAgrupacionSeleccionada!!)
            }
        }
    }
}

