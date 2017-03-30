package com.dbmansfield.book.app

import com.dbmansfield.book.Library
import com.dbmansfield.book.SearchProvider
import org.freedesktop.dbus.DBusConnection
import org.freedesktop.dbus.exceptions.DBusException
import java.lang.Thread.sleep
import kotlin.system.exitProcess

// Export a DBus object to provide a SearchProvider interface that Gnome-Shell
// can interface to allow desktop searching of books
fun runService(library: Library) {
    val bus: DBusConnection = try {
        DBusConnection.getConnection(DBusConnection.SESSION)
    } catch(e: DBusException) {
        print("Could not connect to bus: $e")
        exitProcess(1)
    }
    bus.requestBusName("com.dbmansfield.book.SearchProvider")
    bus.exportObject("/com/dbmansfield/book/SearchProvider", SearchProvider(library))
    // Want to wait on the thread the exported object is running on, but we
    // can't get access to it so we just sleep a bit instead.
    sleep(10000)
    bus.disconnect()
}