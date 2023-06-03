package com.juanmora.square.employeelist.features

import java.io.File

object JsonHelper {

    /**
     * Helper function which will load JSON from
     * the path specified
     *
     * @param path : Path of JSON file
     * @return json : JSON from file at given path
     */
    fun getJsonFromFile(path: String): String {
        // Load the JSON response
        val uri = this.javaClass.classLoader?.getResource(path)

        uri ?: return ""

        val file = File(uri.path)
        return String(file.readBytes())
    }
}
