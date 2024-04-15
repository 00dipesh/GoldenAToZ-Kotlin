package com.goldendigitech.goldenatoz.SalarySlip


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goldendigitech.goldenatoz.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

class SalarySlipAdapter(private val context: Context) : RecyclerView.Adapter<SalarySlipAdapter.MyViewHolder>(){
    private var salarySlipModelList: List<EmployeeSalarySlip> = listOf()

    fun updateData(newList: List<EmployeeSalarySlip>) {
        salarySlipModelList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_salary_slip, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ssm = salarySlipModelList[position]
        val smonth = ssm.FileName
        val parts = smonth.split("_")
        val monthName = parts[2]
        holder.tv_month.text = monthName

        val year = parts[3].split("\\.")[0].toInt()
        val monthNumber = parseMonth(monthName)
        val yearMonth = YearMonth.of(year, monthNumber)
        val startDate = yearMonth.atDay(1)
        val endDate = yearMonth.atEndOfMonth()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formattedStartDate = startDate.format(formatter)
        val formattedEndDate = endDate.format(formatter)
        holder.tv_date.text = "$formattedStartDate - $formattedEndDate"

        val pdfData = ssm.FileContent
        val filename = ssm.FileName

        holder.itemView.setOnClickListener {
            val i = Intent(context, SalarySlipView::class.java)
            i.putExtra("PDFFILE", pdfData)
            i.putExtra("FILENAME", filename)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return salarySlipModelList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_month: TextView = itemView.findViewById(R.id.tv_month)
        val tv_salary: TextView = itemView.findViewById(R.id.tv_salary)
        val tv_date: TextView = itemView.findViewById(R.id.tv_date)
        val ll_main: LinearLayout = itemView.findViewById(R.id.ll_main)
    }

    private fun parseMonth(monthName: String): Int {
        return when (monthName.toLowerCase()) {
            "jan" -> 1
            "feb" -> 2
            "mar" -> 3
            "apr" -> 4
            "may" -> 5
            "jun" -> 6
            "jul" -> 7
            "aug" -> 8
            "sep" -> 9
            "oct" -> 10
            "nov" -> 11
            "dec" -> 12
            else -> throw IllegalArgumentException("Invalid month name")
        }
    }

    private fun openPdf(fileName: String, fileContent: String) {
        val decodedBytes = android.util.Base64.decode(fileContent, android.util.Base64.DEFAULT)
        val file = File(context.cacheDir, fileName)
        val fos: FileOutputStream
        try {
            fos = FileOutputStream(file)
            fos.write(decodedBytes)
            fos.close()

            // Open the PDF file using PDFView
            val intent = Intent(context, SalarySlipView::class.java)
            intent.putExtra("filePath", file.absolutePath)
            context.startActivity(intent)

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}