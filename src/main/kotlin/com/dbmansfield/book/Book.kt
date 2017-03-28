package com.dbmansfield.book

import java.io.File
import java.time.LocalDate
import java.util.*

data class Book(val title: String = "",
                val subtitle: String = "",
                val authors: List<String> = emptyList(),
                val date: LocalDate = LocalDate.now(),
                val abstract: String = "",
                val path: File? = null,
                val uuid: UUID = UUID.randomUUID()) {

    fun asBibtex(): String {
        val formattedAuthors: String = authors.joinToString(separator = " and ")
        return """
        |@book{$uuid,
        |    author = "$formattedAuthors",
        |    title = "$title",
        |    year = "${date.year}"
        |}
        """.trimMargin()
    }
}