# NOTE: if you want to download and edit your own data execute the downloadFromDBPedia and dbpediaToPost scripts.
# They will create a downloaded and a solr_docs directories here.

set SOLR_DOCS=..\..\..\resources\dbpedia_paintings\solr_docs
java -Dcommit=yes -Durl=http://localhost:8983/solr/paintings/update -jar post.jar %SOLR_DOCS%\*.xml
