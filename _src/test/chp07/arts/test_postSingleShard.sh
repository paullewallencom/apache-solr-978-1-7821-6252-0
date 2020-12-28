
# add some test data to a single shard:
curl 'http://localhost:8983/solr/arts_paintings/update?commit=true&wt=json' -H 'Content-type:application/json' -d '
[
  {
    "uri" : "TEST_SHARDS_01",
    "title" : "This is a DUMMY title",
    "artist" : "Some Artist Name",
    "museum" : "Some Museum"
  }
]'

# find out what it is on the indexes, multiple shards:
# curl -X GET 'http://localhost:9999/solr/paintings/select?q=title:dummy&wt=json&indent=true&fl=*,[shard]&shards=localhost:8983/solr/arts_paintings,localhost:9999/solr/paintings'