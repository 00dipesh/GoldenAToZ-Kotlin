package com.goldendigitech.goldenatoz.analysis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goldendigitech.goldenatoz.R

class UserFragment : Fragment() {

    lateinit var rv_user :RecyclerView
    lateinit var userAdapter: UserAdapter
    lateinit var userModelList: List<UserModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user, container, false)



        rv_user = view.findViewById(R.id.rv_user)
        getUserModelList()
        val mLayoutManager :RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        rv_user.layoutManager = mLayoutManager
        userAdapter = UserAdapter(requireContext(),userModelList)
        rv_user.adapter = userAdapter

        return view
    }

    private fun getUserModelList() {
        userModelList = listOf(UserModel("4", "1", "1", "1", "1", ""))
    }
}