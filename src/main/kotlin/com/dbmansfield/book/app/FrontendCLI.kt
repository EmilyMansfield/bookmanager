package com.dbmansfield.book.app

import com.dbmansfield.book.Args
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.ShowHelpException
import java.io.File

val program = "bookmanager"
val homeDir = System.getProperty("user.home")
val libraryDir = "${System.getenv("XDG_DATA_HOME") ?: homeDir}/$program"

// The help formatter includes options we don't want to list, like
// --gapplication-service. This was generated by the ruby gem slop,
// which also looks much nicer I think.
val help = """Usage: $program [OPTION]... [COMMAND] [ARGS]...
Manage a library of digital books and papers.

    -h, --help     display this help and exit
    -l, --library  name of the library
"""

fun main(vararg args: String) {
    File(libraryDir).mkdir()
    System.setProperty("user.dir", libraryDir)
    val parsedArgs = Args(ArgParser(args))

    try {
        if (parsedArgs.service) {
            runService()
            return
        }
        val commandParser = CommandParser
        commandParser.run(parsedArgs.terms)
    } catch(e: ShowHelpException) {
        println(help)
        return
    }
}