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
$applications_dir = "#{$datadir}/applications"

directory $provider_dir
directory $applications_dir

$service = 'com.dbmansfield.book.search-provider.service'
$desktop = 'com.dbmansfield.book.desktop'

file $desktop do |t|
  substitute_file("#{t.name}.in", t.name)
end

file $service do |t|
  substitute_file("#{t.name}.in", t.name)
end

task process: [$desktop, $service] do
end

task install: [:process, $provider_path] do
  cp $service, "#{$provider_dir}/#{$service}"
  cp $desktop, "#{$applications_dir}/#{$desktop}"
end
