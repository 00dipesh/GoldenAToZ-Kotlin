package com.goldendigitech.goldenatoz.SalarySlip

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goldendigitech.goldenatoz.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

class SalarySlipAdapter(var salarySlips: List<EmployeeSalarySlip>) : RecyclerView.Adapter<SalarySlipAdapter.SalarySlipViewHolder>() {

    inner class SalarySlipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMonth: TextView = itemView.findViewById(R.id.tv_month)
        val tvSalary: TextView = itemView.findViewById(R.id.tv_salary)
        val tvDate: TextView = itemView.findViewById(R.id.tv_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalarySlipViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_salary_slip, parent, false)
        return SalarySlipViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SalarySlipViewHolder, position: Int) {
        val currentItem = salarySlips[position]
        holder.tvMonth.text = currentItem.FileName // Change to appropriate field
        holder.tvSalary.text = currentItem.FileType // Change to appropriate field
        holder.tvDate.text = currentItem.CreatedAt // Change to appropriate field

        val smonth = currentItem.FileName
        val parts = smonth.split("_")
        val monthName = parts[2]


        holder.tvMonth.text = monthName


        val year = parts[3].split("\\.")[0].toInt()
        val monthNumber = parseMonth(monthName)
        val yearMonth = YearMonth.of(year, monthNumber)
        val startDate = yearMonth.atDay(1)
        val endDate = yearMonth.atEndOfMonth()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formattedStartDate = startDate.format(formatter)
        val formattedEndDate = endDate.format(formatter)
        holder.tvDate.text = "$formattedStartDate - $formattedEndDate"


        val pdfData = currentItem.FileContent
        val filename = currentItem.FileName

    }

    override fun getItemCount(): Int {
        return salarySlips.size
    }

    private fun parseMonth(monthName: String): Int {
        return when (monthName.toLowerCase(Locale.getDefault())) {
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


}