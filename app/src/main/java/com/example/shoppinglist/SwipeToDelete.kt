package com.example.shoppinglist

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.adapters.ProductAdapter

class SwipeToDelete(var adapter: ProductAdapter):ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        TODO("Not yet implemented")
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        var pos = viewHolder.adapterPosition
        adapter.toggleItem(pos)
    }


}