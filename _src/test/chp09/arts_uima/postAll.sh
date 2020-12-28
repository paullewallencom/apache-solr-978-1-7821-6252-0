# populate the single core for multiple entities

echo "\n\nPOSTING paintings documents"
java -Dcommit=yes -Durl=http://localhost:8983/solr/arts_uima/update -jar post.jar ../../../resources/dbpedia_paintings/solr_docs/*.xml
echo "\n\nPOSTING artists documents"
java -Dcommit=yes -Durl=http://localhost:8983/solr/arts_uima/update -jar post.jar ../../../resources/dbpedia_artists/solr_docs/*.xml
echo "\n\nPOSTING museums documents"
java -Dcommit=yes -Durl=http://localhost:8983/solr/arts_uima/update -jar post.jar ../../../resources/dbpedia_museums/solr_docs/*.xml
echo "\n\nPOSTING subjects documents"
java -Dcommit=yes -Durl=http://localhost:8983/solr/arts_uima/update -jar post.jar ../../../resources/dbpedia_subjects/solr_docs/*.xml