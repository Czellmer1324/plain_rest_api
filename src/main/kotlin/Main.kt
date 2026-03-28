package com.czellmer1324

import com.sun.net.httpserver.HttpServer
import java.net.InetSocketAddress

fun main() {
    val server = HttpServer.create()
    server.bind(InetSocketAddress(8080), 0)
}