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

    fun add(book: Book) {
        books.plus(book)
    }

    fun find(title: String? = null, authors: List<String> = emptyList(), uuid: String? = null): List<Book> {
        // Various match functions to compare against
        // TODO: Move these into a proper Filter class, or something like that
        // Every book should match an empty or null query

        // Match book starting with the query UUID
        fun matchesUuid(book: Book): Boolean {
            return book.uuid.toString().startsWith(uuid ?: "")
        }

        // Match if any of the book's authors contain any of the query authors as a substring
        fun matchesAuthors(book: Book): Boolean {
            return authors.isEmpty() || authors.any { a -> book.authors.any { b -> b.contains(a, ignoreCase = true) } }
        }

        // Match if the book's title contains the query title as a substring
        fun matchesTitle(book: Book): Boolean {
            return book.title.contains(title ?: "", ignoreCase = true)
        }

        return books.filter(::matchesUuid).filter(::matchesAuthors).filter(::matchesTitle)
    }

}