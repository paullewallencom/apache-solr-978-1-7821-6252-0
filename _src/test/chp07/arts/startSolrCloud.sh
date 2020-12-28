
# NOTE: change this path to your Solr installation path
# if you have already defined an environment SOLR_DIST variable, just comment this line
export SOLR_DIST=/home/seralf/SolrStarterBook/solr/

cd $SOLR_DIST/example
#java -Dsolr.solr.home=/home/seralf/SolrStarterBook/solr-app/chp07 -DSTOP.PORT=8893 -DSTOP.KEY=stop_solr -jar start.jar
java -Dbootstrap_confdir=/home/seralf/SolrStarterBook/solr-app/chp07/arts/conf -Dcollection.configName=arts_collection -DzkRun -DnumShards=2 -jar start.jar