curl -X GET 'http://localhost:8983/solr/paintings/select?q=*:*&rows=0&wt=json&indent=true&facet=true&facet.field=artist&facet.field=subject'