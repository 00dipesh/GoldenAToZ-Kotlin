package com.goldendigitech.goldenatoz.ProductView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goldendigitech.goldenatoz.R

class ProductviewAdapter(
    var context: Context, var productViewModelList: List<ProductviewModel>
) : RecyclerView.Adapter<ProductviewAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductviewAdapter.MyViewHolder, position: Int) {
        val pm = productViewModelList[position]
        holder.bind(pm)
    }

    override fun getItemCount(): Int {
        return productViewModelList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tv_name: TextView = itemView.findViewById(R.id.tv_name)
        private val tv_weight: TextView = itemView.findViewById(R.id.tv_weight)
        private val tv_qtv: TextView = itemView.findViewById(R.id.tv_qty)

        fun bind(productViewModel: ProductviewModel) {
            tv_name.text = productViewModel.productname
            tv_weight.text = productViewModel.pweight
            tv_qtv.text = productViewModel.pqty
        }
    }
}