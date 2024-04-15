package com.goldendigitech.goldenatoz.PersonalInfo

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goldendigitech.goldenatoz.R
import com.goldendigitech.goldenatoz.employee.Document
import android.util.Base64
import com.github.barteksc.pdfviewer.PDFView
import java.nio.charset.Charset

class DocumentAdapter(
    private val context: Context) : RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder>() {
    private var documentList: List<Document> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_doc, parent, false)
        return DocumentViewHolder(view)
    }

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        val document = documentList[position]

        // Set document-related information
        holder.fileNameTextView.text = document.fileName

        // Load document content based on the file type
        when (document.fileType) {
            ".jpeg" -> loadImage(holder, document.fileContent)
            ".pdf" -> loadPdf(holder, document.fileContent)
            // Add more cases as needed for other file types
        }
    }

    override fun getItemCount(): Int {
        return documentList.size
    }

    inner class DocumentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val documentContainer: FrameLayout = itemView.findViewById(R.id.documentContainer)
        val pdfViewDoc: PDFView = itemView.findViewById(R.id.pdfview_doc)
        val fileNameTextView: TextView = itemView.findViewById(R.id.fileNameTextView)
    }

    private fun loadImage(holder: DocumentViewHolder, fileContent: String) {
        val imageBytes = Base64.decode(fileContent, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        if (decodedImage != null) {
            holder.imageView.setImageBitmap(decodedImage)
            holder.imageView.visibility = View.VISIBLE
            holder.documentContainer.visibility = View.GONE
        } else {
            // Handle image decoding failure
            holder.imageView.visibility = View.GONE
            holder.documentContainer.visibility = View.VISIBLE
        }
    }

    fun updateData(newList: List<Document>) {
        documentList = newList
        notifyDataSetChanged()
    }

    private fun loadPdf(holder: DocumentViewHolder, fileContent: String) {
        val pdfBytes = Base64.decode(fileContent, Base64.DEFAULT)
        val pdfData = String(pdfBytes, Charset.defaultCharset())
        holder.pdfViewDoc.fromBytes(pdfBytes).load()
        holder.documentContainer.visibility = View.VISIBLE
        holder.pdfViewDoc.visibility = View.VISIBLE
        holder.imageView.visibility = View.GONE
    }
}