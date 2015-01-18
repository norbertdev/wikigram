#!/bin/sh

# check the parameter
if [ "${1}" = "" ]; then
    echo "Error: missing the filepath to the Wikipedia dump. Usage: ./wikigram.sh frwiki-XXXXXXXX-pages-articles.xml.bz2. The wikipedia dumps can be found at http://dumps.wikimedia.org/."
    exit 1
fi

# start the main method
java -server -cp target/wikigram-2015.01.18-jar-with-dependencies.jar norbert.wikigram.WikiGram ${1}
