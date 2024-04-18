package com.goldendigitech.goldenatoz.ProductView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goldendigitech.goldenatoz.R
import androidx.fragment.app.Fragment


class ProductviewFragment : Fragment() {

    lateinit var rv_productview: RecyclerView
    lateinit var productviewAdapter: ProductviewAdapter
    lateinit var hlist: List<ProductviewModel>
    lateinit var searchView: SearchView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        val view: View = inflater.inflate(R.layout.fragment_productview, container, false)

        // Initialize RecyclerView
        rv_productview = view.findViewById(R.id.rv_productview)
        searchView = view.findViewById(R.id.sv_searchView)

        // Initialize product list
        productList()

//        // Setup RecyclerView
//        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
//        rv_productview.layoutManager = mLayoutManager
//        productviewAdapter = ProductviewAdapter(requireContext(), hlist)
//        rv_productview.adapter = productviewAdapter

        // Define the number of columns in the grid
        val numberOfColumns = 2 // Adjust this value based on your preference

        // Setup GridLayoutManager
        val mLayoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(requireContext(), numberOfColumns)
        rv_productview.layoutManager = mLayoutManager

        productviewAdapter = ProductviewAdapter(requireContext(), hlist)
        rv_productview.adapter = productviewAdapter


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
        tempList.add(
            ProductviewModel(
                "Jeeru 1 LT",
                "1 Lt",
                "20",
                "Jay Jalaram",
                R.drawable.jeeru,
                "Mineral Enhanced",
                "19.7 Kilograms",
                "1 Litres",
                "ARL International",
                "Store In Room Temperature",
                "Bottle",
                "ARL International",
                "Jeeru With Added Minerals Water",
                "10000 millilitre",
                "10 x 10 x 10 cm; 19.7 kg",
                "Flavored",
                "Bag"
            )
        )
        tempList.add(
            ProductviewModel(
                "Bisaleri 2 LT",
                "2 Lt",
                "50",
                "Gloud 9",
                R.drawable.biselri1,
                "Mineral Enhanced",
                "19.7 Kilograms",
                "2 Litres",
                "Gloud 9",
                "Store In Room Temperature",
                "Bottle",
                "ARL International",
                "Bisleri With Added Minerals Water",
                "20000 millilitre",
                "10 x 10 x 10 cm; 19.7 kg",
                "Unflavored",
                "Bag"
            )
        )
        tempList.add(
            ProductviewModel(
                "Bisaleri 1.5 LT",
                "1.5 Lt",
                "10",
                "Golden Digi",
                R.drawable.bisaleri,
                "Mineral Enhanced",
                "19.7 Kilograms",
                "1.5 Litres",
                "ARL International",
                "Store In Room Temperature",
                "Bottle",
                "ARL International",
                "Bisleri With Added Minerals Water",
                "15000 millilitre",
                "10 x 10 x 10 cm; 19.7 kg",
                "Unflavored",
                "Bag"
            )
        )
        hlist = tempList
    }

}