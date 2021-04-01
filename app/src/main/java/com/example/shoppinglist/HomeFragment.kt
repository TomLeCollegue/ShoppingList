package com.example.shoppinglist

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
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
        val addButtonText = view.findViewById<ConstraintLayout>(R.id.addProductButton)
        val addEditText = view.findViewById<EditText>(R.id.editTextAdd)
        val addButton = view.findViewById<FrameLayout>(R.id.buttonAddProduct)

        val recyclerViewProduct = view.findViewById<RecyclerView>(R.id.recyclerViewProduct)

        val adapter = ProductAdapter(Product.Singleton.productList, progressCustom)
        recyclerViewProduct.adapter = adapter

        adapter.updateProgress()



        var itemTouchHelper = ItemTouchHelper(SwipeToDelete(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerViewProduct)


        addButtonText.setOnClickListener {
            if(addEditText.visibility == View.GONE){
                addEditText.visibility = View.VISIBLE
                addButton.visibility = View.VISIBLE
            }
            else{
                addEditText.visibility = View.GONE
                addButton.visibility = View.GONE
            }
        }
    }
}