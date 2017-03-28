package com.dbmansfield.book

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.File
import java.io.FileNotFoundException

class Library(file: File) {
    val books: Array<Book>

    init {
        if (file.canRead()) {
            val mapper = ObjectMapper(YAMLFactory())
                    .registerModule(KotlinModule())
                    .registerModule(JavaTimeModule())
            books = mapper.readValue(file, Array<Book>::class.java) ?: emptyArray()
        } else {
            throw FileNotFoundException("Cannot read $file")
        }
    }

    companion object {
        fun findLibrary(filename: String): Library {
            // Load the library file, trying a few alternatives
            val attempts = listOf(filename, "$filename.yml", "$filename.yaml").map(::File)
            var f: File? = attempts.firstOrNull(File::canRead)
            if (f == null) {
                // None of the alternatives exist and are readable, so find the first
                // one that doesn't exist
                f = attempts.firstOrNull { !it.exists() }
                if (f == null) {
                    // All alternatives exist and cannot be read, so bail
                    throw FileAlreadyExistsException(attempts.first(),
                            reason = "Library already exists and is not readable")
                }
                if (!f.createNewFile()) {
                    // An alternative is available but cannot be created, so bail
                    throw FileNotFoundException("Cannot read library and failed to create alternative $f")
                }
                // Make the newly created file a valid empty YAML file
                f.appendText("---\n")
            }
            return Library(f)
        }
    }
}