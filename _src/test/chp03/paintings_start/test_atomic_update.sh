curl 'http://localhost:8983/solr/paintings_start/update?commit=true&wt=json' -H 'Content-type:application/json' -d '
[
  {
    "uri" : "http://en.wikipedia.org/wiki/Mona_Lisa",
    "title" : "Mona Lisa",
    "museum" : "Louvre"
  }
]'

curl 'http://localhost:8983/solr/paintings_start/update?commit=true&wt=json' -H 'Content-type:application/json' -d '
[
  {
    "uri" : "http://en.wikipedia.org/wiki/Mona_Lisa",
    "title" : {"set":"Mona Lisa (again)"},
    "revision"  : {"inc":2},
    "museum" : {"add":"Louvre Museum"}
  }
]'

curl 'http://localhost:8983/solr/paintings_start/update?commit=true&wt=json' -H 'Content-type:application/json' -d '
[
  {
    "uri" : "http://en.wikipedia.org/wiki/Mona_Lisa",
    "title" : {"set":"Mona Lisa (modified)"},
    "revision"  : {"inc":1},
    "museum" : {"set":"Another Museum"},
    "_version_" : 1
  }
]'

