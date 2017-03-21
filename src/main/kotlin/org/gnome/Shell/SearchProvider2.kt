package org.gnome.Shell

import org.freedesktop.dbus.DBusInterface
import org.freedesktop.dbus.Variant

interface SearchProvider2 : DBusInterface {

    fun GetInitialResultSet(terms: Array<String>): Array<String>
    fun GetSubsearchResultSet(previousResults: Array<String>, terms: Array<String>): Array<String>
    fun GetResultMetas(identifiers: Array<String>): Array<Map<String, Variant<*>>>
    fun ActivateResult(identifier: String, terms: Array<String>, timestamp: Int)
    fun LaunchSearch(terms: Array<String>, timestamp: Int)
}