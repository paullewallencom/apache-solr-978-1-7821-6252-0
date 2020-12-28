curl -X POST "http://localhost:8983/solr/arts_paintings/update?commit=true" -H "Content-Type: text/xml" --data-binary "<delete><query>*:*</query></delete>"
curl -X POST "http://localhost:8983/solr/arts_paintings/update" -H "Content-Type: text/xml" --data-binary "<optimize />"

curl -X POST "http://localhost:8983/solr/arts_artists/update?commit=true" -H "Content-Type: text/xml" --data-binary "<delete><query>*:*</query></delete>"
curl -X POST "http://localhost:8983/solr/arts_artists/update" -H "Content-Type: text/xml" --data-binary "<optimize />"

curl -X POST "http://localhost:8983/solr/arts_museums/update?commit=true" -H "Content-Type: text/xml" --data-binary "<delete><query>*:*</query></delete>"
curl -X POST "http://localhost:8983/solr/arts_museums/update" -H "Content-Type: text/xml" --data-binary "<optimize />"

curl -X POST "http://localhost:8983/solr/arts_subjects/update?commit=true" -H "Content-Type: text/xml" --data-binary "<delete><query>*:*</query></delete>"
curl -X POST "http://localhost:8983/solr/arts_subjects/update" -H "Content-Type: text/xml" --data-binary "<optimize />"
