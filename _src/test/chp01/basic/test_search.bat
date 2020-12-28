curl -H "Content-Type: text/xml" -X POST "http://localhost:8983/solr/basic/select?q=id:testdoc&wt=json"

REM TIP: if you try to execute a similar example, searching for name:document, 
REM you will find out there are no results at all, even if we know that the word "document" is used two times in this long "name" field. 
REM This is simply because we have not yet defined a tokenization method, that helps us split the text into several words, 
REM in order to make possible to search for single words.