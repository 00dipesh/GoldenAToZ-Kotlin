package com.goldendigitech.goldenatoz.ProductView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.goldendigitech.goldenatoz.R
import com.goldendigitech.goldenatoz.databinding.ActivityAddedProductListBinding
import com.goldendigitech.goldenatoz.databinding.ActivityChangepasswordBinding

class AddedProductList : AppCompatActivity() {

    private lateinit var addedProductListBinding: ActivityAddedProductListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addedProductListBinding = ActivityAddedProductListBinding.inflate(layoutInflater)
        val view: View = addedProductListBinding.root
        setContentView(view)

        // Retrieve the product list from the intent
        val productList = intent.getSerializableExtra("PRODUCT_LIST") as? ArrayList<ProductviewModel>


        // Check if productList is not null
        if (productList != null) {
            // Initialize RecyclerView
            val layoutManager = LinearLayoutManager(this)
            addedProductListBinding.rvAddedProduct.layoutManager = layoutManager

            // Create and set adapter
            val adapter = AddedProductAdapter(productList)
            addedProductListBinding.rvAddedProduct.adapter = adapter
        } else {
            // Handle case where productList is null (optional)
            Toast.makeText(this, "No products added", Toast.LENGTH_SHORT).show()
        }

    }
}