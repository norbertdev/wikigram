Objective
=========

WikiGram aims to compute the most used trigrams in the Wikipedia articles.


How to start WikiGram?
======================

Download a Wikipedia dump
-------------------------

* The dumps are provided by [Wikimedia](http://dumps.wikimedia.org/). For example:
```sh
wget https://dumps.wikimedia.org/frwiki/latest/frwiki-latest-pages-articles-multistream.xml.bz2
```

* The interlangua Wikipedia dump is smaller:
```sh
wget https://dumps.wikimedia.org/iawiki/latest/iawiki-latest-pages-articles-multistream.xml.bz2
```


Build WikiGram
--------------

* Clone with [git](http://git-scm.com/) and build with [Maven](http://maven.apache.org/):
```sh
git clone http://github.com/norbertdev/wikigram.git
cd wikigram
mvn package assembly:single
```


Start WikiGram
--------------

* Run the script, give the dump as parameter. For example:
```sh
./wikigram.sh frwiki-latest-pages-articles-multistream.xml.bz2
```


License
=======

[GNU GPL 3](https://www.gnu.org/licenses/gpl-3.0.html) or any later version published by the Free Software Foundation.
