package com.czellmer1324

import java.math.BigDecimal

object ProductStorage {
    private val storage = HashMap<Int, Product>()
    private var nextId = 0

    fun getProducts() : List<Product> {
        return storage.values.toList()
    }

    fun getProduct(id: Int) : Product? {
        return storage[id]
    }

    fun addProduct(name: String, price: BigDecimal, description: String) {
        val product = Product(nextId, name, price, description)
        storage[nextId] = product
        nextId++
    }

    fun deleteProduct(id: Int) : Boolean {
        storage.remove(id) ?: return false

        return true
    }
}