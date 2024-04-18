package com.goldendigitech.goldenatoz.ProductView

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.goldendigitech.goldenatoz.R
import com.goldendigitech.goldenatoz.databinding.ActivityProductItemViewBinding

class ProductItemView : AppCompatActivity() {

    lateinit var productItemViewBinding: ActivityProductItemViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productItemViewBinding = ActivityProductItemViewBinding.inflate(layoutInflater)
        val view: View = productItemViewBinding.root
        setContentView(view)


        val productModel: ProductviewModel? = intent.getParcelableExtra("PRODUCT_MODEL")
        productItemViewBinding.tvProductname.text = productModel?.productname
        productItemViewBinding.tvSpeciality.text = productModel?.Specialty
        productItemViewBinding.tvWeight.text = productModel?.pweight
        productItemViewBinding.tvVolume.text = productModel?.Volume
        productItemViewBinding.tvIngradienttype.text = productModel?.IngredientType
        productItemViewBinding.tvBrand.text = productModel?.Brand
        productItemViewBinding.tvStorageinstruction.text = productModel?.StorageInstructions
        productItemViewBinding.tvPackageinformation.text = productModel?.PackageInformation
        productItemViewBinding.tvManufacture.text = productModel?.Manufacturer
        productItemViewBinding.itempartnumber.text = productModel?.Itempartnumber
        productItemViewBinding.tvNetqty.text = productModel?.NetQuantity
        productItemViewBinding.tvDimensions.text = productModel?.ProductDimensions
        productItemViewBinding.tvFlavoured.text = productModel?.Flavour
        productItemViewBinding.tvComponents.text = productModel?.IncludedComponents

        val text = "₹20.00 | ₹10.00"
        val parts = text.split(" | ")
        val spannableString = SpannableString(parts[0])

        spannableString.setSpan(
            StrikethroughSpan(),
            0,
            spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        // Set the SpannableString to the TextView
        productItemViewBinding.tvPrice.text =
            SpannableStringBuilder().append(spannableString).append(" | ").append(parts[1])

        // Load image using Glide
        productModel?.imagePath?.let { imagePath ->
            Glide.with(this)
                .load(imagePath)
                .into(productItemViewBinding.ivPiamge)
        }



        productItemViewBinding.btnClose.setOnClickListener {
//            val productviewFragment = ProductviewFragment()
//            val fragmentManager = supportFragmentManager
//            val fragmentTransaction = fragmentManager.beginTransaction()
//            fragmentTransaction.replace(R.id.fragment_container, productviewFragment)
//            fragmentTransaction.addToBackStack(null)
//            fragmentTransaction.commit()

            onBackPressed()
        }


        // Set up the back button click listener
        productItemViewBinding.ivBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })


    }


    fun strikeThroughText(text: String): SpannableStringBuilder {
        val spannable = SpannableStringBuilder(text)
        spannable.setSpan(StrikethroughSpan(), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannable
    }
}