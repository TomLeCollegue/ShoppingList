package com.example.shoppinglist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.adapters.ProductAdapter
import com.example.shoppinglist.customviews.HorizontalProgressView
import com.example.shoppinglist.customviews.ProgressCustom
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
        val horizontalProgressView = view.findViewById<HorizontalProgressView>(R.id.horizontalProgressView2)

        val addEditText = view.findViewById<EditText>(R.id.editTextAdd)
        val addButton = view.findViewById<FrameLayout>(R.id.buttonAddProduct)

        val recyclerViewProduct = view.findViewById<RecyclerView>(R.id.recyclerViewProduct)

        val adapter = ProductAdapter(Product.Singleton.productList, progressCustom, horizontalProgressView)
        recyclerViewProduct.adapter = adapter

        adapter.updateProgress()



        var itemTouchHelper = ItemTouchHelper(SwipeToDelete(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerViewProduct)

        addButton.setOnClickListener {
            val text = addEditText.text.toString()
            if(text != ""){
                Product.Singleton.productList.add(0,Product(addEditText.text.toString()))
                adapter.notifyItemInserted(0)
                addEditText.setText("")
                recyclerViewProduct.smoothScrollToPosition(0)
                adapter.updateProgress()
            }
        }

    }
}