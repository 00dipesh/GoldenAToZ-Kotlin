package com.goldendigitech.goldenatoz.ProductView

import android.content.Context
import android.content.Intent
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StrikethroughSpan
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
    private var addItemClickListener: OnAddItemClickListener? = null


    // Interface for click listener
    interface OnAddItemClickListener {
        fun onAddItemClicked(productViewModel: ProductviewModel)
    }

    // Setter for click listener
    fun setOnAddItemClickListener(listener: OnAddItemClickListener) {
        this.addItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pm = filteredProductList[position]
        holder.bind(pm)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductItemView::class.java)
            intent.putExtra(
                "PRODUCT_MODEL",
                pm
            ) // Pass the entire product model to the ProductDetailsActivity
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = filteredProductList.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tv_name: TextView = itemView.findViewById(R.id.tv_product_name)
        private val iv_product: ImageView = itemView.findViewById(R.id.iv_product_image)
        private val tv_price: TextView = itemView.findViewById(R.id.tv_price);
        private val tvaddProduct: TextView = itemView.findViewById(R.id.tvaddProduct)


        fun bind(productViewModel: ProductviewModel) {
            tv_name.text = productViewModel.productname
            tv_price.text = strikeThroughText("₹20.00")
            Glide.with(itemView.context)
                .load(productViewModel.imagePath)
                .into(iv_product)
        }

        init {
            // Set click listener for "Add" TextView
            tvaddProduct.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    addItemClickListener?.onAddItemClicked(filteredProductList[position])
                }
            }
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

    fun strikeThroughText(text: String): SpannableStringBuilder {
        val spannable = SpannableStringBuilder(text)
        spannable.setSpan(StrikethroughSpan(), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannable
    }
}
