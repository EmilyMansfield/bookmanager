#!/usr/bin/env ruby

require 'slop'

require_relative 'book'
require_relative 'library'

EXECUTABLE_NAME = File.basename(__FILE__)
LIBRARY_DIR =
  if ENV['XDG_DATA_HOME']
    "#{ENV['XDG_DATA_HOME']}/bookmanager"
  else
    "#{Dir.home}/.bookmanager"
  end

# Convert an array ["key", "value", "key", "value"]
# into a hash { key: "value", key: "value" }
def array_to_hash(array)
  unless array.length % 2 == 0
    raise RangeError, "array must have even length"
  end
  (0...array.length/2).map do |i|
    [array[2*i].to_sym, array[2*i+1].to_s]
  end.to_h
end

# Parse command line arguments
opts = Slop.parse do |o|
  o.bool '-h', '--help', 'display this help and exit'
  o.string '-l', '--library', 'name of the library',
    default: "library"
  o.banner = ''
end

# First position argument is a command
command = opts.arguments.shift

# Show help
if opts[:help] || command == nil
  puts "Usage: #{EXECUTABLE_NAME} [OPTION]... [COMMAND] [ARGS]..."
  puts "Manage a library of digital books and papers."
  puts opts
  exit
end

Dir.mkdir(LIBRARY_DIR) unless Dir.exist?(LIBRARY_DIR)
Dir.chdir(LIBRARY_DIR)

library = Library.new("#{opts[:library]}.yml")
args = array_to_hash(opts.arguments)

# Parse the command
case command
when 'add'
  library.add(args)
  library.save
when 'find'
  puts library.get(args).map { |b| b.to_h }.to_yaml
when 'path'
  puts library.get(args).map { |b| File.expand_path(b.path) }
when 'bibtex'
  puts library.get(args).map { |b| b.to_bibtex }
else
  puts "'#{command}' not recognized."
  show_help
end
