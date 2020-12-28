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

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.core.CoreContainer;

public class EmbeddedSolrExample {

	public static void main(String[] args) throws Exception {
		
		// NOTE: we can override this configuration, passing a valid slr home from command line
		System.setProperty("solr.solr.home", "solr-home");
		
		CoreContainer container = new CoreContainer();
		container.load();
		EmbeddedSolrServer server = new EmbeddedSolrServer(container, "arts");

		// delete all documents
		server.deleteByQuery("*:*");

		Artist doc = new Artist("http://en.wikipedia.org/wiki/Leonardo_da_Vinci","Leonardo Da Vinci", "Vinci", "Florence");
		server.addBean(doc);

		server.commit();

		QueryResponse rsp = server.query(new SolrQuery("leonardo"), METHOD.GET);

		SolrDocumentList oo = rsp.getResults();
		for (SolrDocument d : oo) {
			for (String field : d.getFieldNames()) {
				System.out.printf("%s = %s\n", field, d.getFieldValue(field));
			}
		}

		server.shutdown();
	}

}
