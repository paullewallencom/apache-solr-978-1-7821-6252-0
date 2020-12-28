export SOLR_DOCS=../../../resources/dbpedia_paintings/solr_docs
java -Dcommit=yes -Durl=http://localhost:8983/solr/paintings_xslt/update -jar post.jar $SOLR_DOCS/*.xml