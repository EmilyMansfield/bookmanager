package com.dbmansfield.book

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.File

class Library(val file: File) {
    init {
        if (file.canRead()) {
            val mapper = ObjectMapper(YAMLFactory())
                    .registerModule(KotlinModule())
                    .registerModule(JavaTimeModule())
            val books: Array<Book> = mapper.readValue(file, Array<Book>::class.java)
            println(books.first().asBibtex())
        } else {
            println("File not found")
        }
    }
}