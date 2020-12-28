curl -X POST "http://localhost:8983/solr/geonames/update?commit=true" -H "Content-Type: text/xml" --data-binary "<delete><query>*:*</query></delete>"
curl -X POST "http://localhost:8983/solr/geonames/update" -H "Content-Type: text/xml" --data-binary "<optimize />"
