package com.dbmansfield.book

import com.xenomachina.argparser.ArgParser

class Args(parser: ArgParser) {
    val library by parser.storing("-l", "--library", help = "").default("library")
    val service by parser.flagging("--gapplication-service", help = "")
    val terms by parser.positionalList("COMMAND", 0..Int.MAX_VALUE, help = "")

    init {
        parser.force()
    }
}