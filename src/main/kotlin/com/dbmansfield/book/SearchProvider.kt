package com.dbmansfield.book

import org.freedesktop.dbus.Variant
import org.gnome.Shell.SearchProvider2

class SearchProvider(val library: Library) : SearchProvider2 {

    override fun GetInitialResultSet(terms: Array<String>): Array<String> {
        return arrayOf("foo", "bar")
    }

    override fun GetSubsearchResultSet(previousResults: Array<String>, terms: Array<String>): Array<String> {
        return arrayOf("foo", "bar")
    }

    override fun GetResultMetas(identifiers: Array<String>): Array<Map<String, Variant<*>>> {
        return identifiers.map {
            hashMapOf("id" to Variant<String>(it),
                    "name" to Variant<String>(it.capitalize()),
                    "gicon" to Variant<String>("x-office-document"),
                    "description" to Variant<String>("description"))
        }.toTypedArray()
    }

    override fun ActivateResult(identifier: String, terms: Array<String>, timestamp: Int) {
        Runtime.getRuntime().exec("notify-send -- $identifier")
    }

    override fun LaunchSearch(terms: Array<String>, timestamp: Int) {
        Runtime.getRuntime().exec("notify-send 'Launch Search'")
    }

    override fun getObjectPath() = "/com/dbmansfield/book/SearchProvider"

    override fun isRemote() = false
}