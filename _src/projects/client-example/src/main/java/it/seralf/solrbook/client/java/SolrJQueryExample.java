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
package it.seralf.solrbook.client.java;

import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;

public class SolrJQueryExample {

	public static void main(String[] args) throws Exception {
		
		HttpSolrServer server = new HttpSolrServer("http://localhost:8983/solr/arts");

		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("uri", "http://TEST/Leonardo_da_Vinci");
		doc.addField("artist", "Leonardo Da Vinci");
		doc.addField("city", "Vinci");
		doc.addField("note", "document updated");
		server.add(doc);
		server.commit();

		SolrQuery solrQuery = new SolrQuery()
			.setQuery("vermeer~0.5")
			.setFacet(true).setFacetMinCount(1).setFacetLimit(8)
			.addFacetField("museum_entity").addFacetField("city")
			.setHighlight(true).setHighlightSnippets(3)
			.setParam("hl.fl", "artist");

		System.out.printf("QUERY: %s/%s\n\n", server.getBaseURL(), solrQuery);
		
		QueryResponse rsp = server.query(solrQuery);
		
		System.out.println("#### DOCs");
		for (SolrDocument d : rsp.getResults()) {
			System.out.println(d);
		}
		
		System.out.println("#### facets");
		for (Object e : rsp.getLimitingFacets()) {
			System.out.println(e);
		}
		
		System.out.println("#### highligths");
		for (Entry<?,?> e : rsp.getHighlighting().entrySet()) {
			System.out.println(e);
		}
		
		server.shutdown();
	}

}
