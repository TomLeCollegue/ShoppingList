package com.example.shoppinglist.storage

import com.example.shoppinglist.adapters.ProductAdapter
import com.example.shoppinglist.storage.Product.Singleton.productList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Product(
    val text: String = "",
    var done: Boolean = false,
    val id: String = "product0"
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

    fun updateData(productAdapter: ProductAdapter){
        Singleton.databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                for(ds in snapshot.children){
                    val product = ds.getValue(Product::class.java)
                    if(product !=null){
                        productList.add(product)
                    }
                }
                productList.sortBy { it.done }
                productAdapter.notifyDataSetChanged()
                productAdapter.updateProgress()
            }

        })
    }

    fun updateProduct(){
        Singleton.databaseRef.child(id).setValue(this)
    }

    fun addProduct(){
        Singleton.databaseRef.child(id).setValue(this)
    }


    fun isAlreadyExisting() : Int{
        productList.forEachIndexed { index, product ->
            if(product.id == id){
                return index
            }
        }
        return -1
    }

}