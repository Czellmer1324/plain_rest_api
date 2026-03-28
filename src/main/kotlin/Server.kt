package com.czellmer1324

import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress
import java.util.concurrent.Executors

object Server {
    val server: HttpServer =  HttpServer.create(InetSocketAddress(PORT), 0)
    const val THREAD_NUM = 5
    const val PORT = 8080

    init {
        server.executor = Executors.newFixedThreadPool(THREAD_NUM)
    }

    fun start() {
        server.start()
        println("Server is running on http://localhost:$PORT")
    }
}