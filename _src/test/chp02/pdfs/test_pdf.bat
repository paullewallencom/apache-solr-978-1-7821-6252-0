set PDFS=..\..\..\resources/pdfs
cd %PDFS%
curl -X POST "http://localhost:8983/solr/pdfs/update/extract?extractFormat=text&literal.annotation=The+Wikipedia+Page+About+Apache+Lucene&commit=true" -F "Lucene.pdf=@Lucene.pdf"
