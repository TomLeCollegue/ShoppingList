package com.example.shoppinglist.storage

import android.util.Log
import com.example.shoppinglist.adapters.ProductAdapter
import com.example.shoppinglist.storage.Product.Singleton.productList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Product(
    var text: String = "",
    var done: Boolean = false,
    var id: String = "product0"
) {

    fun clone(bool : Boolean): Product {
        return Product(text, !bool, id)
    }


    object Singleton {

        val databaseRef = FirebaseDatabase.getInstance().getReference("products")
        val productList = arrayListOf<Product>(
         /*       Product("pain de mie"),
                Product("Pains aux chocolat"),
                Product("Saucisson"),
                Product("Bière"),
                Product("Chips"),
                Product("Poisson")
*/
        )
    }

    fun updateData(productAdapter: ProductAdapter){
        Singleton.databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    val product = ds.getValue(Product::class.java)
                    if(product !=null){
                        updateRecyclerViewProduct(productAdapter,product)
                        //productList.sortBy { it.done }
                    }
                }


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
        for(i in productList.indices){
            if(productList[i].id == id){
                return i
            }
        }
        return -1

    }

    fun updateRecyclerViewProduct(productAdapter: ProductAdapter, product: Product){
        if(product.isAlreadyExisting() == -1){
            if(!product.done){
                productList.add(0,product)
                productAdapter.notifyItemInserted(0)
            }
            else{
                productList.add(product)
                productAdapter.notifyItemInserted(productList.size)
            }
        }
        else{
            val index = product.isAlreadyExisting()
            productList[index].text = product.text
            if(productList[index].done != product.done){
                Log.d("debugUpdate",index.toString())
                if(product.done){
                    productList.removeAt(index)
                    productList.add(product)
                    productAdapter.notifyItemMoved(index, productList.size-1)
                }
                else{
                    productList.removeAt(index)
                    productList.add(0,product)
                    productAdapter.notifyItemMoved(index,0)
                }
            }
        }
        productAdapter.updateProgress()

    }

}