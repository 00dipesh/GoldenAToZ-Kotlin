package com.goldendigitech.goldenatoz.ProductView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.goldendigitech.goldenatoz.R

class AddedProductAdapter(private val productList: List<ProductviewModel>) :
    RecyclerView.Adapter<AddedProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.tv_product_name)
        val productOffer: TextView = itemView.findViewById(R.id.tv_offer)
        val productOfferPrice: TextView = itemView.findViewById(R.id.tv_offerprice)
        val productMRP: TextView = itemView.findViewById(R.id.tv_price)
        val imageShow: ImageView = itemView.findViewById(R.id.iv_product_image)
        val productMinus: TextView = itemView.findViewById(R.id.tv_minusproduct)
        val productQty: TextView = itemView.findViewById(R.id.tv_addqty)
        val productPlus: TextView = itemView.findViewById(R.id.tv_plusproduct)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_added_product, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = productList[position]
        holder.productName.text = currentItem.productname
        holder.productOffer.text = "₹ 10 off"
        holder.productMRP.text = "MRP : ₹20.00"
        holder.productOfferPrice.text = "₹10.00"

        holder.productQty.text = currentItem.quantity.toString() // Set the quantity
        // Load image into ImageView using Glide
        Glide.with(holder.itemView.context)
            .load(currentItem.imagePath)
            .into(holder.imageShow)


        // Increment quantity when plus button is clicked
        holder.productPlus.setOnClickListener {
            currentItem.quantity++
            holder.productQty.text = currentItem.quantity.toString()
            notifyDataSetChanged()   // if you want the RecyclerView to reflect the changes immediately.
        }

        // Decrement quantity when minus button is clicked
        holder.productMinus.setOnClickListener {
            if (currentItem.quantity > 0) { // Ensure quantity doesn't go below 0
                currentItem.quantity--
                holder.productQty.text = currentItem.quantity.toString()
                notifyDataSetChanged()     // Notify adapter about the data change
            }
        }


    }

    override fun getItemCount() = productList.size
}