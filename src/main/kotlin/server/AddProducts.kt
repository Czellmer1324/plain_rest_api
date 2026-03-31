package com.czellmer1324.server

import com.czellmer1324.ProductStorage
import com.czellmer1324.requests.AddProductRequest
import com.google.gson.Gson
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler

object AddProducts : HttpHandler{
    override fun handle(exchange: HttpExchange) {
        // Set the header content type as well as create the GSON obj for responding
        exchange.responseHeaders.set("Content-Type", "JSON")
        val gson = Gson()

        // check the method
        checkMethod(exchange, gson)

        // get the request body
        val requestBody = exchange.requestBody.use { stream ->
            convert(stream.readBytes(), gson)
        }

        // check that nothing from the request Body was null
        checkFullInfo(requestBody, exchange, gson)

        // Add the new product to storage
        // Assert not null because it is being checked that all info is being provided
        ProductStorage.addProduct(requestBody.name!!, requestBody.price!!, requestBody.description!!)

        // Respond that product was added
        val response = gson.toJson("Product added successfully!")
        exchange.sendResponseHeaders(200, response.toByteArray().size.toLong())
        exchange.responseBody.use { os ->
            os.write(response.toByteArray())
        }
    }

    private fun convert(input: ByteArray, gson: Gson) : AddProductRequest {
        val jsonString = String(input)
        val requestBody = AddProductRequest()
        return gson.fromJson(jsonString, requestBody.javaClass)
    }

    private fun checkMethod(exchange: HttpExchange, gson: Gson) {
        val method = exchange.requestMethod

        // Make sure they are using the post method
        if (method != "POST") {
            val response = gson.toJson("Method not allowed")
            val bytes = response.toByteArray()
            exchange.sendResponseHeaders(405, bytes.size.toLong())
            exchange.responseBody.use { os ->
                os.write(bytes)
            }
        }
    }

    private fun checkFullInfo(obj: AddProductRequest, e: HttpExchange, gson: Gson) {
        if (obj.price == null || obj.name == null || obj.description == null) {
            val response = gson.toJson("Incomplete product information.")
            val bytes = response.toByteArray()
            e.sendResponseHeaders(400, bytes.size.toLong())

            e.responseBody.use { os ->
                os.write(bytes)
            }
        }
    }
}