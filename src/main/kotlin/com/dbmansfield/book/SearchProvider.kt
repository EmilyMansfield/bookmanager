package com.dbmansfield.book

import org.freedesktop.dbus.UInt32
import org.freedesktop.dbus.Variant
import org.gnome.Shell.SearchProvider2

class SearchProvider(val library: Library) : SearchProvider2 {

    override fun GetInitialResultSet(terms: Array<String>): Array<String> {
        return library.find(title = terms.reduce { a, b -> a + " " + b }).map { it.uuid.toString() }.toTypedArray()
    }

    override fun GetSubsearchResultSet(previousResults: Array<String>, terms: Array<String>): Array<String> {
        // TODO: Do this properly
        return GetInitialResultSet(terms)
    }

    override fun GetResultMetas(identifiers: Array<String>): Array<Map<String, Variant<*>>> {
        return identifiers.map { library.find(uuid = it).first() }.map {
            hashMapOf("id" to Variant<String>(it.uuid.toString()),
                    "name" to Variant<String>(it.title),
                    "gicon" to Variant<String>("x-office-document"),
                    "description" to Variant<String>("By ${it.authors}"))
        }.toTypedArray()
    }

    override fun ActivateResult(identifier: String, terms: Array<String>, timestamp: UInt32) {
        Runtime.getRuntime().exec("xdg-open ${library.find(uuid = identifier).first().path}")
    }

    override fun LaunchSearch(terms: Array<String>, timestamp: UInt32) {}

    override fun getObjectPath() = "/com/dbmansfield/book/SearchProvider"

    override fun isRemote() = false
}