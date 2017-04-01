package com.dbmansfield.book.app

import com.dbmansfield.book.Book
import com.dbmansfield.book.Library
import java.io.File
import java.time.LocalDate
import java.util.*

class CommandParser(val library: Library) {
    companion object {
        // Pairwise chunk a list then convert each pair to a k,v entry of a map
        // [1,2,3,4,5,6] -> [[1,2],[3,4],[5,6]] -> {1 => 2, 3 => 4, 5 => 6}
        // Drop the last element if the list is not of even length
        fun <T> listToHash(l: List<T>): Map<T, T> {
            val trimmedList: List<T> = if (l.size % 2 == 0) l else l.dropLast(1)
            return (0..trimmedList.size / 2 - 1).map { trimmedList[2 * it] to trimmedList[2 * it + 1] }.toMap()
        }

        // Split a string at a delimiter then trim whitespace
        // Return an empty list if the input was null
        fun splitAndTrim(str: String?, delimiter: Char): List<String> {
            return str?.split(delimiter)?.map(String::trim) ?: emptyList()
        }
    }

    // Parse the command and delegate to the Library
    fun parse(terms: List<String>) {
        val command = terms.first()
        val args = listToHash(terms.drop(1))
        when (command) {
            "add" -> {
                val book = Book(
                        title = args["title"] ?: "",
                        subtitle = args["subtitle"] ?: "",
                        authors = splitAndTrim(args["authors"] ?: args["author"], ','),
                        date = if (args["date"] == null) LocalDate.now() else LocalDate.parse(args["date"]),
                        abstract = args["abstract"] ?: "",
                        path = if (args["path"] == null) null else File(args["path"]),
                        uuid = if (args["uuid"] == null) UUID.randomUUID() else UUID.fromString(args["uuid"]))
                library.add(book)
            }
            "find" -> {
                library.find(args["title"], splitAndTrim(args["authors"], ',')).forEach(::println)
            }
            "path" -> {
            }
            "bibtex" -> {
                println(Book(title = "Test title", authors = listOf("Author 1", "Author 2")).asBibtex())
            }
            else -> {
                println("$command is not a recognized command")
            }
        }
    }
}

