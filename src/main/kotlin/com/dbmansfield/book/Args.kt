package com.dbmansfield.book

import com.xenomachina.argparser.ArgParser

class Args(parser: ArgParser) {
    val library by parser.storing("-l", "--library", help = "name of the library")
            .default("library")
    val service by parser.flagging("--gapplication-service", help = "run as a DBus service")
}