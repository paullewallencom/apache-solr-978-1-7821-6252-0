curl -X POST "http://localhost:8983/solr/paintings/update?commit=true" -H "Content-Type: text/xml" --data-binary "<delete><query>*:*</query></delete>"
curl -X POST "http://localhost:8983/solr/paintings/update" -H "Content-Type: text/xml" --data-binary "<optimize />"
