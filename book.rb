require 'securerandom'

class Book
  attr_reader :title, :subtitle, :author, :coauthors, :date, :abstract,
    :uuid, :path

  def initialize(opts={})
    @title     = opts[:title]     || opts['title']    || ''
    @subtitle  = opts[:subtitle]  || opts['subtitle'] || ''
    @author    = opts[:author]    || opts['author']   || ''
    @coauthors = opts[:coauthors] || opts['coathors'] || ''
    @abstract  = opts[:abstract]  || opts['abstract'] || ''
    @date      = opts[:date]      || opts['date']     || Time.now
    @uuid      = opts[:uuid]      || opts['uuid']     || SecureRandom.uuid
    @path      = opts[:path]      || opts['path']     || ''

    @coauthors = @coauthors.split(',').map{ |x| x.lstrip }
    if @date.is_a? String
      date_array = @date.split('-').compact
      #m = /(?<year>1\d{4})-(?<month>\d\d)-(?<day>\d\d)/.match('date')
      #@date = Time.new(m[:year], m[:month], m[:day])
      @date = Time.new(*date_array)
    end
  end

  def to_h
    {
      'uuid' => @uuid,
      'title' => @title,
      'subtitle' => @subtitle,
      'author' => @author,
      'coauthors' => @coauthors,
      'date' => @date,
      'abstract' => @abstract,
      'path' => @path
    }
  end

  def to_s
    s = ""
    s << "#{@title}"
    s << " (#{@subtitle})" unless @subtitle.empty?
    s << ",\n"
    s << "by #{@author}"
  end

  def to_bibtex
    s = []
    s << "@book{#{@uuid}"
    s << "      author = \"#{@author}\""
    s << "      title = \"#{@title}\""
    s << "      year = #{@date.year}"
    s.join(",\n") + "\n}"
  end
end
