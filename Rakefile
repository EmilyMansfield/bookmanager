$prefix       = ENV['prefix']       || '/usr/local'
$exec_prefix  = ENV['exec_prefix']  || $prefix
$bindir       = ENV['bindir']       || "#{$exec_prefix}/bin"

$subs = {
  'prefix': $prefix,
  'exec_prefix': $exec_prefix,
  'bindir': $bindir
}

# Perform Autoconf-style substitution replacing @foo@ with $subs['foo']
def substitute!(str, hash)
  hash.each { |k, v| str.gsub!("@#{k}@", v.to_s) }
  str
end

# Perform variable substitution on entire file
def substitute_file(in_file, out_file)
  f = File.open(in_file, 'r').read
  substitute!(f, $subs)
  File.open(out_file, 'w').write(f)
end

task :process_desktop do
  f = 'com.dbmansfield.book.desktop'
  substitute_file("#{f}.in", f)
end

task :process_service do
  f = 'com.dbmansfield.book.search-provider.service'
  substitute_file("#{f}.in", f)
end

task process: [:process_desktop, :process_service] do
end

task default: [:process]
