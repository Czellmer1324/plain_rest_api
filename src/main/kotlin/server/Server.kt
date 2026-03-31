package com.czellmer1324.server

import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import java.util.concurrent.Executors

object Server {
    private val server: HttpServer =  HttpServer.create(InetSocketAddress(PORT), 0)
    private const val PORT = 8080

    init {
        server.executor = Executors.newVirtualThreadPerTaskExecutor()
        server.createContext("/addProduct", AddProducts)
        server.createContext("/products", GetProducts)
        server.createContext("/product/", GetProductById)
        server.createContext("/deleteProduct/", DeleteProduct)
    }

    fun start() {
        server.start()
        println("Server is running on http://localhost:$PORT")
    }

}