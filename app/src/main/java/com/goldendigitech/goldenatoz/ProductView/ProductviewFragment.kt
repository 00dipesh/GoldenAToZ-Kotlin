package com.goldendigitech.goldenatoz.ProductView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goldendigitech.goldenatoz.R


class ProductviewFragment : Fragment() {

    lateinit var rv_productview: RecyclerView
    lateinit var productviewAdapter: ProductviewAdapter
    lateinit var hlist: List<ProductviewModel>
    lateinit var searchView: SearchView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_productview, container, false)

        // Initialize RecyclerView
        rv_productview = view.findViewById(R.id.rv_productview)
        searchView = view.findViewById(R.id.sv_searchView)

        // Initialize product list
        productList()

        // Setup RecyclerView
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        rv_productview.layoutManager = mLayoutManager
        productviewAdapter = ProductviewAdapter(requireContext(), hlist)
        rv_productview.adapter = productviewAdapter

        // Setup SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                productviewAdapter.filter.filter(newText)
                return true
            }
        })

        return view
    }

    private fun productList() {
        val tempList = ArrayList<ProductviewModel>()
        tempList.add(ProductviewModel("Jeeru 1 LT", "1 Lt", "11","Jay Jalaram",R.drawable.jeeru))
        tempList.add(ProductviewModel("Bisaleri 2 LT", "2 Lt", "50","Gloud 9",R.drawable.biselri_1lt))
        tempList.add(ProductviewModel("Bisaleri 1.5 LT", "1.5 Lt", "10","Golden Digi",R.drawable.bisaleri))
        hlist = tempList
    }
}