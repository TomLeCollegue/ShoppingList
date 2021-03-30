package com.example.shoppinglist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.adapters.ProductAdapter
import com.example.shoppinglist.storage.Product

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressCustom = view.findViewById<ProgressCustom>(R.id.progressCustom)
        val addProductButton = view.findViewById<FrameLayout>(R.id.addProductButton)
        val addButtonText = view.findViewById<TextView>(R.id.buttonAddText)

        val recyclerViewProduct = view.findViewById<RecyclerView>(R.id.recyclerViewProduct)

        val adapter = ProductAdapter(Product.Singleton.productList)
        recyclerViewProduct.adapter = adapter

        addProductButton.setOnClickListener {
            it.apply {
                transitionName = "addProductButton"
            }
            val extras = FragmentNavigatorExtras(
                    it to "addProductButton",
                    addButtonText to "buttonAddText"
            )
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_addProductFragment, null, null, extras)
        }

        var itemTouchHelper = ItemTouchHelper(SwipeToDelete(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerViewProduct)


    }

}