require 'rake/clean'

$prefix       = ENV['prefix']       || '/usr/local'
$exec_prefix  = ENV['exec_prefix']  || $prefix
$bindir       = ENV['bindir']       || "#{$exec_prefix}/bin"
$datarootdir  = ENV['datarootdir']  || "#{$prefix}/share"
$datadir      = ENV['datadir']      || $datarootdir

$subs = {
  'prefix': $prefix,
  'exec_prefix': $exec_prefix,
  'bindir': $bindir,
  'datarootdir': $datarootdir,
  'datadir': $datadir
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

$provider_dir = "#{$datadir}/gnome-shell/search-providers"
$services_dir = "#{$datadir}/dbus-1/services"
$applications_dir = "#{$datadir}/applications"

directory $provider_dir
directory $services_dir
directory $applications_dir

$provider = 'com.dbmansfield.book.SearchProvider.ini'
$service = 'com.dbmansfield.book.SearchProvider.service'
$desktop = 'com.dbmansfield.book.desktop'

file $desktop do |t|
  substitute_file("#{t.name}.in", t.name)
end
CLOBBER << $desktop

file $service do |t|
  substitute_file("#{t.name}.in", t.name)
end
CLOBBER << $service

task process: [$desktop, $service] do
end

task install: [:process, $provider_dir, $applications_dir] do
  cp $provider, "#{$provider_dir}/#{$provider}"
  cp $service, "#{$services_dir}/#{$service}"
  cp $desktop, "#{$applications_dir}/#{$desktop}"
end
