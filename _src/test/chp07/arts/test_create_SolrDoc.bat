#!/bin/sh

# ./downloadFromDBPedia.sh
# generate Solr documents for paintings
./dbpediaToPost.sh . output dbpediaToPost.xslt 'paintings'
