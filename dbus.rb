#!/usr/bin/env ruby

require 'dbus'

class SearchProvider < DBus::Object
  # Create the interface
  dbus_interface 'org.gnome.Shell.SearchProvider2' do

    dbus_method(:GetInitialResultSet,
                'in terms:as, out results:as') do |terms|
      ['foo', 'bar']
    end

    dbus_method(:GetSubsearchResultSet,
                'in previous_results:as, in terms:as, out results:as') do |previous_results, terms|
      ['foo', 'bar']
    end

    dbus_method(:GetResultMetas,
                'in identifiers:as, out metas:aa{sv}') do |identifiers|
      identifiers.map do |id|
        {
          'id': id,
          'name': id.capitalize,
          'gicon': 'x-office-document',
          'description': 'Foo description'
        }
      end
    end

    dbus_method(:ActivateResult,
                'in identifier:s, in terms:as, in timestamp:u') do |identifier, terms, timestamp|
      `notify-send -- #{identifier}`
    end

    dbus_method(:LaunchSearch,
                'in terms:as, in timestamp:u') do |terms, timestamp|
      `notify-send LaunchSearch`
    end
  end
end

bus = DBus.session_bus
service = bus.request_service('com.dbmansfield.book.search-provider.service')
exported_obj = SearchProvider.new('/com/dbmansfield/book/SearchProvider')
service.export(exported_obj)

main = DBus::Main.new
main << bus
main.run
