
REM NOTE: change this path to your Solr installation path
REM if you have already defined an environment SOLR_DIST variable, just comment this line
REM set SOLR_DIST=..\..\..\solr\example
REM cd $SOLR_DIST
java -Dbootstrap_confdir=../../SolrStarterBook/solr-app/chp07/arts/conf -Dcollection.configName=arts_collection -DzkRun -DnumShards=2 -jar start.jar