
# NOTE: change this path to your Solr installation path
# if you have already defined an environment SOLR_DIST variable, just comment this line
REM set SOLR_DIST=..\..\..\solr\example
REM cd $SOLR_DIST
java -Dsolr.solr.home=/home/seralf/SolrStarterBook/solr-app/chp05 -Djetty.port=9999 -DSTOP.PORT=8993 -DSTOP.KEY=stop_solr -jar start.jar

#RIFATTORIZZARE
