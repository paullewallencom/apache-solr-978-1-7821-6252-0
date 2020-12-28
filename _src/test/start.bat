
REM NOTE: if you have not defined a SOLR_DIST environment variable,
REM you can uncomment and edit the following line
REM export SOLR_DIST=/path/to/solr/example

java -Djetty.home=%SOLR_DIST% -Xms128m -Xmx1024m -Dsolr.solr.home=../solr-app/%1 -DSTOP.PORT=8893 -DSTOP.KEY=solr_stop -jar %SOLR_DIST%\start.jar
