package com.dbmansfield.book.app

object CommandParser {
    // Pairwise chunk a list then convert each pair to a k,v entry of a map
    // [1,2,3,4,5,6] -> [[1,2],[3,4],[5,6]] -> {1 => 2, 3 => 4, 5 => 6}
    // Drop the last element if the list is not of even length
    fun <T> listToHash(l: List<T>): Map<T, T> {
        val trimmedList: List<T> = if (l.size % 2 == 0) l else l.dropLast(1)
        return (0..trimmedList.size / 2 - 1).map { trimmedList[2 * it] to trimmedList[2 * it + 1] }.toMap()
    }

    // Parse the command and delegate to the Library
    fun run(terms: List<String>) {
        val command = terms.first()
        val args = listToHash(terms.drop(1))
        when (command) {
            "add" -> {
            }
            "find" -> {
            }
            "path" -> {
            }
            "bibtex" -> {
            }
            else -> {
                println("$command is not a recognized command")
            }
        }
    }
}

