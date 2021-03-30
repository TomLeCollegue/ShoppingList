package com.example.shoppinglist.storage

class Product(
        val text: String,
        val done: Boolean = false
) {

    fun clone(bool : Boolean): Product {
        return Product(text, !bool)
    }


    object Singleton {
        val productList = arrayListOf<Product>(
                Product("pain de mie"),
                Product("Pains aux chocolat"),
                Product("Saucisson"),
                Product("Bière"),
                Product("Chips"),
                Product("Poisson")
        )
    }
}