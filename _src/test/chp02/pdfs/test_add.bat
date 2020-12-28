set PDFS=..\..\..\resources\pdfs
cd %PDFS%
curl -X POST "http://localhost:8983/solr/pdfs/update?commit=true&wt=json" -H "Content-Type: text/xml" -d @docs.xml