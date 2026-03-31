package com.czellmer1324.server

import com.czellmer1324.ProductStorage
import com.czellmer1324.requests.AddProductRequest
import com.google.gson.Gson
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import java.util.concurrent.Executors

object Server {
    private val server: HttpServer =  HttpServer.create(InetSocketAddress(PORT), 0)
    private const val PORT = 8080

    init {
        server.executor = Executors.newVirtualThreadPerTaskExecutor()
        server.createContext("/addProduct", AddProducts)
        server.createContext("/products", GetProduct)
    }

    fun start() {
        server.start()
        println("Server is running on http://localhost:$PORT")
    }

}