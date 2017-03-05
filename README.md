Book Manager
---

Keeping track of a large number of PDFs is a pain, especially when they
may be scattered around a filesystem, inconsistently named, or missing
identifying metadata.

This program provides a simple CLI for constructing a database of
documents (ebooks, academic papers, music, ...) in the form of a YAML
file, which can then be easily searched and parsed.

Possible query output includes the path to the file on disk, a BibTeX
entry for referencing, and of course the entire database entry.

By default the library database is saved in `$HOME/.bookmanager`,
although `XDG_DATA_HOME` will be honoured if it is set. A single user
may have multiple libraries under this directory, which can be selected
via the `-l` option.

Usage
---

~~~
> $ book_manager.rb --help
Usage: book_manager.rb [OPTION]... [COMMAND] [ARGS]...
Manage a library of digital books and papers.

    -h, --help     display this help and exit
    -l, --library  name of the library
~~~

When invoking, options are specified first, then a command, followed by
a list of key-value pairs for that command. Omitted key-value pairs have
sane defaults, and are unordered.

For example, to add a few papers

~~~
> $ book_manager.rb add title 'Groundbreaking paper' author 'A.N Author' \
	date '1990' path '~/Papers/gr-paper.pdf'

> $ book_manager.rb add author 'Author B. Two' title 'Lotsa Research'
	uuid 'ab2:lotsa-research'
~~~

To get the full database entry

~~~
> $ book_manager.rb find title 'Groundbr'
---
- uuid: 542c3e4b-6057-4c36-9fe7-1f1bb86aa130
  title: Groundbreaking Paper
  subtitle: ''
  author: A.N Author
  coauthors: []
  date: 1990-01-01 00:00:00.000000000 +00:00
  abstract: ''
  path: '~/Papers/gr-paper.pdf'
~~~

To generate the BibTeX entry

~~~
> $ book_manager.rb bibtex uuid ab2
@book{ab2:lotsa-research,
      author = "Author B. Two",
	  title = "Lotsa Research",
	  year = 2017
}
~~~

To open the file (`~` is expanded to the user's home directory) in
`evince`, say

~~~
> $ evince `book_manager.rb path author 'Two'`
~~~

Querying
---

To search for a document, specify the entries to filter by in the same
way as when adding a document. String arguments are checked by
substring, although when specifying a UUID the start must match.

For example

~~~
> $ book_manager.rb find author 'James' title 'group' uuid 'ab'
~~~

will return all documents with an author containing the string
`"James"`, a title containing the string `"group"`, and a UUID beginning
with `ab`.

TODO
---

See the Github issue list.
