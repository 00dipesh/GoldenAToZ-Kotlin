package com.goldendigitech.goldenatoz.ProductView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.goldendigitech.goldenatoz.R
import android.widget.Filterable
import android.widget.ImageView
import com.bumptech.glide.Glide

class ProductviewAdapter(
    private val context: Context,
    private var productViewModelList: List<ProductviewModel>
) : RecyclerView.Adapter<ProductviewAdapter.MyViewHolder>(), Filterable {

    private var filteredProductList: List<ProductviewModel> = productViewModelList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pm = filteredProductList[position]
        holder.bind(pm)
    }

    override fun getItemCount(): Int = filteredProductList.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tv_name: TextView = itemView.findViewById(R.id.tv_product_name)
        private val tv_weight: TextView = itemView.findViewById(R.id.tv_product_weight)
        private val tv_qtv: TextView = itemView.findViewById(R.id.tv_product_qty)
        private val tv_storename: TextView = itemView.findViewById(R.id.tv_store_name)
        private val iv_product: ImageView = itemView.findViewById(R.id.iv_product_image)



        fun bind(productViewModel: ProductviewModel) {
            tv_name.text = productViewModel.productname
            tv_weight.text = productViewModel.pweight
            tv_qtv.text = productViewModel.pqty
            tv_storename.text = productViewModel.storename
            Glide.with(itemView.context)
                .load(productViewModel.imagePath)
                .into(iv_product)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<ProductviewModel>()

                if (constraint.isNullOrBlank()) {
                    filteredList.addAll(productViewModelList)
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()

                    for (item in productViewModelList) {
                        if (item.productname.toLowerCase().contains(filterPattern)) {
                            filteredList.add(item)
                        }
                    }
                }

                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.values != null) {
                    filteredProductList = results.values as List<ProductviewModel>
                    notifyDataSetChanged()
                }
            }
        }
    }
}
