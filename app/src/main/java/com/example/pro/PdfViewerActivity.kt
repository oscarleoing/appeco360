package com.example.pro

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.IOException



    class PdfViewerActivity : AppCompatActivity() {

        private var pdfRenderer: PdfRenderer? = null
        private var parcelFileDescriptor: ParcelFileDescriptor? = null

        private lateinit var layoutPagesContainer: LinearLayout
        private lateinit var btnBack: Button

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_pdf_viewer)

            layoutPagesContainer = findViewById(R.id.layoutPagesContainer)
            btnBack = findViewById(R.id.btnBack)

            btnBack.setOnClickListener {
                finish()
            }

            val pdfPath = intent.getStringExtra("pdfPath")
            if (pdfPath != null) {
                openRenderer(pdfPath)
                showAllPages()
            } else {
                finish() // No PDF path, close activity
            }
        }

        private fun openRenderer(filePath: String) {
            try {
                val file = File(filePath)
                parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
                pdfRenderer = PdfRenderer(parcelFileDescriptor!!)
            } catch (e: IOException) {
                e.printStackTrace()
                finish()
            }
        }

        private fun showAllPages() {
            pdfRenderer?.let { renderer ->
                layoutPagesContainer.removeAllViews()
                for (i in 0 until renderer.pageCount) {
                    val page = renderer.openPage(i)

                    // Calcula tamaño de la página según densidad de pantalla
                    val width = resources.displayMetrics.densityDpi / 72 * page.width
                    val height = resources.displayMetrics.densityDpi / 72 * page.height

                    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                    page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
                    page.close()

                    // Crear ImageView para mostrar la página
                    val imageView = ImageView(this).apply {
                        setImageBitmap(bitmap)
                        adjustViewBounds = true
                        scaleType = ImageView.ScaleType.FIT_XY
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        ).apply {
                            setMargins(0, 0, 0, 0) // 👈 sin espacios entre páginas
                        }
                    }

                    layoutPagesContainer.addView(imageView)
                }
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            pdfRenderer?.close()
            parcelFileDescriptor?.close()
        }
    }
