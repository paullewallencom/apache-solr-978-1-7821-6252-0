# 0) warning: completely clean the index, removing all the documents
curl 'http://localhost:8983/solr/paintings_geo/update?commit=true' -H 'Content-type:application/json' -d '{ "delete" : { "query" : "*:*" } }'


# 1) add a painting
curl 'http://localhost:8983/solr/paintings_geo/update?commit=true&wt=json' -H 'Content-type:application/json' -d '
[
  {
    "uri" : "01",
    "title" : "Mona Lisa"
  }
]'
# 2) add the same painting, with more fields
curl 'http://localhost:8983/solr/paintings_geo/update?commit=true&wt=json' -H 'Content-type:application/json' -d '
[
  {
    "uri" : "01",
    "title" : "Mona Lisa",
    "artist" : "http://dbpedia.org/resource/Leonardo Da Vinci",
    "museum" : "http://dbpedia.org/resource/Louvre",
    "city" : "http://dbpedia.org/resource/Paris"
  }
]'

# find out what it is on the index
curl 'http://localhost:8983/solr/paintings_geo/select?q=*:*&wt=json' -H 'Content-type:application/json'