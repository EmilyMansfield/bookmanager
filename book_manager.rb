#!/usr/bin/env ruby

require_relative 'book'
require_relative 'library'

EXECUTABLE_NAME = File.basename(__FILE__)
LIBRARY_NAME = 'library.yml'

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

def show_help
  puts "Usage: #{EXECUTABLE_NAME} [COMMAND] [ARGS]..."
  puts "Manage a library of digital books and papers."
end

# Parse command line arguments
# TODO: Use slop then remove parsed args
# First position argument is a command
$command = ARGV[0]
unless $command
  show_help
  exit
end

$library = Library.new(LIBRARY_NAME)
$args = array_to_hash(ARGV[1..-1])

# Parse the command
case $command
when 'add'
  $library.add($args)
  $library.save
when 'find'
  puts $library.get($args).map { |b| b.to_h }.to_yaml
when 'path'
  puts $library.get($args).map { |b| File.expand_path(b.path) }
when 'bibtex'
  puts $library.get($args).map { |b| b.to_bibtex }
else
  puts "'#{$command}' not recognized."
  show_help
end
