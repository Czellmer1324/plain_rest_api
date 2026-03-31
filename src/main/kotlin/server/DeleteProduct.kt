package com.czellmer1324.server

import com.czellmer1324.ProductStorage
import com.google.gson.Gson
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler

object DeleteProduct : HttpHandler {
    override fun handle(exchange: HttpExchange) {
        exchange.responseHeaders.set("Content-Type", "application/json")
        val gson = Gson()

        //Check to make sure method is correct
        if (exchange.requestMethod != "DELETE") {
            val message= gson.toJson("Method not allowed")
            sendResponse(exchange, message, 405)
            return
        }

        // get the ID from the URI.
        // Turn URI to string, split into string array, grab the last index as that is the id
        val idString = exchange.requestURI.toString().split('/').last()

        // try to convert the string version of id to Int
        try {
            val id = idString.toInt()

            // check to make sure the ID exists
            if (!ProductStorage.checkProductIdExists(id)) {
                val message = gson.toJson("No product exists with that ID")
                sendResponse(exchange, message, 404)
                return
            }

            ProductStorage.deleteProduct(id)

            val message = gson.toJson("Product with id $id was deleted.")
            sendResponse(exchange, message, 200)
        } catch (e: Exception) {
            val message = gson.toJson("Id must be a whole integer")
            sendResponse(exchange, message, 400)
        }
    }

    private fun sendResponse(e: HttpExchange, message:String, code: Int) {
        val bytes = message.toByteArray()
        e.sendResponseHeaders(code, bytes.size.toLong())
        e.responseBody.use { os ->
            os.write(bytes)
        }
    }
}