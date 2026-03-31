package com.czellmer1324.server

import com.czellmer1324.ProductStorage
import com.google.gson.Gson
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler

object GetProductById : HttpHandler {
    override fun handle(exchange: HttpExchange) {
        exchange.responseHeaders.set("Content-Type", "JSON")
        val gson = Gson()

        //check to make sure it is a GET request
        if (exchange.requestMethod != "GET") {
            val response = gson.toJson("Method not allowed")
            val bytes = response.toByteArray()
            exchange.sendResponseHeaders(405, bytes.size.toLong())
            exchange.responseBody.use { os ->
                os.write(bytes)
            }
        }

        // Get the URI and split it based on /'s, get the last part of the array for the ID
        val idString = exchange.requestURI.toString().split('/').last()

        //try to convert the id to an int
        try {
            val id = idString.toInt()
            val responseObj = ProductStorage.getProduct(id)

            val responseJson = gson.toJson(responseObj)
            val responseBytes = responseJson.toByteArray()
            exchange.sendResponseHeaders(200, responseBytes.size.toLong())
            exchange.responseBody.use { os ->
                os.write(responseBytes)
            }

        } catch (e: Exception) {
            val response = gson.toJson("ID must be a whole integer")
            val bytes = response.toByteArray()
            exchange.sendResponseHeaders(400, bytes.size.toLong())
            exchange.responseBody.use { os ->
                os.write(bytes)
            }
        }

    }
}