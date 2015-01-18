#!/bin/sh

# check the parameter
if [ "${1}" = "" ]; then
    echo "Error: missing the filepath to the Wikipedia dump. Usage: ./wikigram.sh frwiki-XXXXXXXX-pages-articles.xml.bz2. The wikipedia dumps can be found at http://dumps.wikimedia.org/."
    exit 1
fi

# check the ${M2_REPO} environment variable
if [ "${M2_REPO}" = "" ]; then
    echo "Error: missing environment variable M2_REPO."
    exit 1
fi

# start the main method
java -server -cp ${M2_REPO}/org/apache/commons/commons-compress/1.2/commons-compress-1.2.jar:${M2_REPO}/org/hamcrest/hamcrest-core/1.1/hamcrest-core-1.1.jar:target/wikigram-2011.12.17.jar norbert.wikigram.WikiGram ${1}
