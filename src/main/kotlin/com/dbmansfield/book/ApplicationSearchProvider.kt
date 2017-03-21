package com.dbmansfield.book

import org.freedesktop.dbus.DBusConnection
import org.freedesktop.dbus.exceptions.DBusException
import java.lang.Thread.sleep
import kotlin.system.exitProcess

fun main(vararg args: String) {
    val bus: DBusConnection = try {
        DBusConnection.getConnection(DBusConnection.SESSION)
    } catch(e: DBusException) {
        print("Could not connect to bus: $e")
        exitProcess(1)
    }
    bus.requestBusName("com.dbmansfield.book.SearchProvider")
    bus.exportObject("/com/dbmansfield/book/SearchProvider", SearchProvider())
    // Want to wait on the thread the exported object is running on, but we can't get access to it
    // so we just sleep a bit instead.
    sleep(10000)
    bus.disconnect()
}