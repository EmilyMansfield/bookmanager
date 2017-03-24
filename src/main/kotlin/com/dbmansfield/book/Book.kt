package com.dbmansfield.book

import java.io.File
import java.util.*

data class Book(val title: String = "",
                val subtitle: String = "",
                val authors: List<String> = emptyList(),
                val date: Calendar = Calendar.getInstance(),
                val abstract: String = "",
                val path: File? = null,
                val uuid: UUID = UUID.randomUUID()) {

    fun asBibtex(): String {
        return """
        |@book{$uuid
        |    author = "$authors"
        |    title = "$title"
        |    year = "${date.get(Calendar.YEAR)}"
        |}
        """.trimMargin()
    }
}