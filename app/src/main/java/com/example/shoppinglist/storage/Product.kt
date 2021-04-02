package com.example.shoppinglist.storage

import com.example.shoppinglist.adapters.ProductAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Product(
        val text: String = "",
        val done: Boolean = false
) {

    fun clone(bool : Boolean): Product {
        return Product(text, !bool)
    }


    object Singleton {

        val databaseRef = FirebaseDatabase.getInstance().getReference("products")
        val productList = arrayListOf<Product>(
                Product("pain de mie"),
                Product("Pains aux chocolat"),
                Product("Saucisson"),
                Product("Bière"),
                Product("Chips"),
                Product("Poisson")

        )
    }

    fun UpdateData(productAdapter: ProductAdapter){
        Singleton.databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Singleton.productList.clear()
                for(ds in snapshot.children){
                    val product = ds.getValue(Product::class.java)
                    if(product !=null){
                        Singleton.productList.add(product)
                    }
                }
                productAdapter.notifyDataSetChanged()
            }

        })
    }
}