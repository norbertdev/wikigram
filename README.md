Objective
=========

WikiGram aims to compute the most used trigrams in the Wikipedia articles.


How to start WikiGram?
======================

Build WikiGram
--------------

* Install Maven (http://maven.apache.org/). Debian example:
```sh
aptitude install maven2
```

* Build the jar with all dependencies by running Maven in the root directory of the project (the directory containing the file 'pom.xml'):
```sh
mvn package assembly:single
```

Download a Wikipedia dump
-------------------------

* The dumps are downloadable at http://dumps.wikimedia.org/. For example:
```sh
wget https://dumps.wikimedia.org/frwiki/latest/frwiki-latest-pages-articles-multistream.xml.bz2
```

Start WikiGram
--------------

* Run the script, give the dump as parameter. For example:
```sh
./wikigram.sh frwiki-latest-pages-articles-multistream.xml.bz2
```

License
=======

See the LICENSE file.


Contact
=======

The online repository can be found at:
https://github.com/norbertdev/wikigram
