
# NOTE: if you have not defined a SOLR_DIST environment variable,
# you can uncomment and edit the following line
# export SOLR_DIST=/path/to/solr/example

# MINIMAL TODO FOR CHAPTER 2:
# java -Dsolr.solr.home=../../solr-app/$1 -jar start.jar

cd $SOLR_DIST
java -Xms128m -Xmx1024m -DSOLR_DIST=$SOLR_DIST -Dsolr.solr.home=../../solr-app/$1 -Djetty.port=8983 -DSTOP.PORT=8893 -DSTOP.KEY=solr_stop -jar start.jar

