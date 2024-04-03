package com.goldendigitech.goldenatoz.ProductView

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goldendigitech.goldenatoz.Home.HomeSubMenuModel
import com.goldendigitech.goldenatoz.R


class ProductviewFragment : Fragment() {

    lateinit var rv_productview: RecyclerView
    lateinit var productviewAdapter: ProductviewAdapter
    lateinit var hlist: List<ProductviewModel>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_productview, container, false)

        // Initialize RecyclerView
        rv_productview = view.findViewById(R.id.rv_productview)

        // Initialize product list
        productList()

        // Setup RecyclerView
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        rv_productview.layoutManager = mLayoutManager
        productviewAdapter = ProductviewAdapter(requireContext(), hlist)
        rv_productview.adapter = productviewAdapter

        return view
    }

    private fun productList() {
        val tempList = ArrayList<ProductviewModel>()
        tempList.add(ProductviewModel("Jeeru 1 LT", "1 Lt", "1"))
        tempList.add(ProductviewModel("Bisaleri 2 LT", "2 Lt", "5"))
        hlist = tempList
    }
}