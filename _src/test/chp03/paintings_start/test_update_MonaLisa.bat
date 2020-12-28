# 0) warning: completely clean the index, removing all the documents
curl 'http://localhost:8983/solr/paintings_start/update?commit=true' -H 'Content-type:application/json' -d '
{
	"delete" : {
		"query" : "*:*"
	} 
}'

# 1) add a painting
curl 'http://localhost:8983/solr/paintings_start/update?commit=true&wt=json' -H 'Content-type:application/json' -d '
[
  {
    "uri" : "http://en.wikipedia.org/wiki/Mona_Lisa",
    "title" : "Mona Lisa",
    "museum" : "unknown"
  }
]'


# 2) add the same painting, with more fields
curl 'http://localhost:8983/solr/paintings_start/update?commit=true&wt=json' -H 'Content-type:application/json' -d '
[
  {
    "uri" : "http://en.wikipedia.org/wiki/Mona_Lisa",
    "title" : "Mona Lisa",
    "artist" : "Leonardo Da Vinci",
    "museum" : "Louvre"
  }
]'

# 3) find out what it is on the index
curl 'http://localhost:8983/solr/paintings_start/select?q=*:*&commit=true&wt=json' -H 'Content-type:application/json'