set PDFS=..\..\..\resources/pdfs
cd %PDFS%
curl -X POST "http://localhost:8983/solr/pdfs/update/extract?extractOnly=true&extractFormat=text" -F "Lucene.pdf=@Lucene.pdf"