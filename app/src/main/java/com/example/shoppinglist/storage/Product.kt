package com.example.shoppinglist.storage

import android.util.Log
import com.example.shoppinglist.adapters.ProductAdapter
import com.example.shoppinglist.storage.Product.Singleton.databaseRef
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

        val databaseRef = FirebaseDatabase.getInstance().getReference("productsHolyTom")
        val productList = arrayListOf<Product>()
    }

    fun updateData(productAdapter: ProductAdapter){
        Singleton.databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val products = arrayListOf<Product>()
                products.clear()
                for(ds in snapshot.children){
                    val product = ds.getValue(Product::class.java)
                    if(product !=null){
                        updateRecyclerViewProduct(productAdapter,product)
                        products.add(product)
                    }
                }

                Log.d("debugString", products.toString())
                removeItemListIfNotInDatabase(snapshot, productAdapter)
            }

        })
    }

    private fun removeItemListIfNotInDatabase(snapshot: DataSnapshot, productAdapter: ProductAdapter) {
        for(i in 0 until productList.size){
            if(i < productList.size){
                if(!snapshot.hasChild(productList[i].id)){
                    Log.d("debugString", "on enleve le produit $i")
                    productList.removeAt(i)
                    productAdapter.notifyItemRemoved(i)
                }
            }
        }
        productAdapter.updateProgress()
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
                        if(index ==  productList.size-1){
                            productAdapter.notifyItemChanged(index)
                        }
                        else{
                        productAdapter.notifyItemMoved(index, productList.size-1)
                        }

                }
                else{

                        productList.removeAt(index)
                        productList.add(0, product)
                        if(index == 0){
                            productAdapter.notifyItemChanged(0)
                        }
                        else{
                            productAdapter.notifyItemMoved(index, 0)
                        }

                }
            }
        }
        productAdapter.updateProgress()

    }

    fun removeItem() {
        databaseRef.child(id).removeValue()
    }

    override fun toString(): String {
        return "Product(text='$text', done=$done)"
    }


}