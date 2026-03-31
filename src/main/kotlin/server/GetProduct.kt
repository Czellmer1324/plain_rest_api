package com.czellmer1324.server

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler

object GetProduct : HttpHandler {
    override fun handle(exchange: HttpExchange) {
        // need to retrieve the list of products
        
        // convert the list of products to JSON
        // send the message back
    }
}