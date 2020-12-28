/*******************************************************************************
 * Copyright 2013 Alfredo Serafini (http://seralf.it)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
import org.apache.solr.client.solrj.SolrQuery
import org.apache.solr.client.solrj.impl.HttpSolrServer
import org.apache.solr.client.solrj.response.QueryResponse
import org.apache.solr.common.SolrInputDocument
import scala.collection.JavaConversions._

object ok extends App {

  val server = new HttpSolrServer("http://localhost:8983/solr/arts")

  val doc = new SolrInputDocument()
  doc.addField("uri", "http://TEST/Leonardo_da_Vinci")
  doc.addField("artist", "Leonardo Da Vinci")
  doc.addField("city", "Vinci")
  doc.addField("note", "document updated")
  server.add(doc)
  server.commit()

  val solrQuery = new SolrQuery()
    .setQuery("vermeer~0.5")
    .setFacet(true).setFacetMinCount(1).setFacetLimit(8)
    .addFacetField("museum_entity").addFacetField("city")
    .setHighlight(true).setHighlightSnippets(3)
    .setParam("hl.fl", "artist")

  printf("QUERY: %s/%s\n\n", server.getBaseURL(), solrQuery)

  val rsp = server.query(solrQuery)

  println("#### DOCs")
  rsp.getResults().foreach(d => println(d))

  println("#### facets")
  rsp.getLimitingFacets().foreach(d => println(d))

  println("#### highligths")
  rsp.getHighlighting().entrySet().foreach(d => println(d))

  server.shutdown()

}
