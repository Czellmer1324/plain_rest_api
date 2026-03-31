package com.czellmer1324.server

import com.czellmer1324.ProductStorage
import com.google.gson.Gson
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler

object GetProduct : HttpHandler {
    override fun handle(exchange: HttpExchange) {
        exchange.responseHeaders.set("Content-Type", "JSON")
        val gson = Gson()

        // verify that the method is get method
        if (exchange.requestMethod != "GET") {
            val badReqRes = gson.toJson("Method not allowed")
            val bytes = badReqRes.toByteArray()
            exchange.sendResponseHeaders(405, bytes.size.toLong())
            exchange.responseBody.use { os ->
                os.write(bytes)
            }
        }

        // need to retrieve the list of products
        val products = ProductStorage.getProducts()
        // convert the list of products to JSON
        val response = gson.toJson(products)
        // send the message back
        val bytes = response.toByteArray()
        exchange.sendResponseHeaders(200, bytes.size.toLong())
        exchange.responseBody.use { os ->
            os.write(bytes)
        }
    }
}