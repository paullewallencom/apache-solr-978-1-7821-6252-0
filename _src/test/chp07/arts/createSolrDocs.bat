REM generate Solr documents for paintings
dbpediaToPost.bat ../../../resources/dbpedia_paintings/downloaded/ ../../../resources/dbpedia_paintings/solr_docs/ dbpediaToPost.xslt 'paintings'

REM generate Solr documents for artists
dbpediaToPost.bat ../../../resources/dbpedia_artists/downloaded/ ../../../resources/dbpedia_artists/solr_docs/ dbpediaToPost.xslt 'artists'

REM generate Solr documents for museums
dbpediaToPost.bat ../../../resources/dbpedia_museums/downloaded/ ../../../resources/dbpedia_museums/solr_docs/ dbpediaToPost.xslt 'museums'

REM generate Solr documents for subjects
dbpediaToPost.bat ../../../resources/dbpedia_subjects/downloaded/ ../../../resources/dbpedia_subjects/solr_docs/ dbpediaToPost.xslt 'subjects'
