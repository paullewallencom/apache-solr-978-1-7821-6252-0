#!/bin/sh

# generate Solr documents for paintings
./dbpediaToPost.sh ../../../resources/dbpedia_paintings/downloaded/ ../../../resources/dbpedia_paintings/solr_docs/ dbpediaToPost.xslt 'paintings'

# generate Solr documents for artists
./dbpediaToPost.sh ../../../resources/dbpedia_artists/downloaded/ ../../../resources/dbpedia_artists/solr_docs/ dbpediaToPost.xslt 'artists'

# generate Solr documents for museums
./dbpediaToPost.sh ../../../resources/dbpedia_museums/downloaded/ ../../../resources/dbpedia_museums/solr_docs/ dbpediaToPost.xslt 'museums'

# generate Solr documents for subjects
./dbpediaToPost.sh ../../../resources/dbpedia_subjects/downloaded/ ../../../resources/dbpedia_subjects/solr_docs/ dbpediaToPost.xslt 'subjects'
