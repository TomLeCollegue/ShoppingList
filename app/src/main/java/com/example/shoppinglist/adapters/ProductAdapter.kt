package com.example.shoppinglist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.customviews.ProgressCustom
import com.example.shoppinglist.R
import com.example.shoppinglist.customviews.HorizontalProgressView
import com.example.shoppinglist.storage.Product

class ProductAdapter(val products : ArrayList<Product>, val progressCustom: ProgressCustom, val horizontalProgressView: HorizontalProgressView) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private val TYPE_NORMAL = 1
    private val TYPE_DONE = 2

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val text = view.findViewById<TextView>(R.id.productName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when(viewType){
            TYPE_NORMAL ->  {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_list, parent, false)
                return ProductAdapter.ViewHolder(view)
            }
            TYPE_DONE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_list_checked, parent, false)
                return ProductAdapter.ViewHolder(view)
            }
        }


        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_list, parent, false)
        return ProductAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = products[position].text
    }

    override fun getItemViewType(position: Int): Int {
        if(products[position].done){
            return TYPE_DONE
        }
        return TYPE_NORMAL
    }

    fun toggleItem(position: Int){
        val product = products[position]
        val productClone = product.clone(product.done)


        productClone.updateProduct()
    }

    fun updateProgress(){
        var result = 0
        products.forEach {
            if(it.done) result++
            }
        val progress: Float = (result.toFloat() / products.size.toFloat()) * 100f
        progressCustom.progress = progress.toInt()
        horizontalProgressView.progress = progress.toInt()

    }
}