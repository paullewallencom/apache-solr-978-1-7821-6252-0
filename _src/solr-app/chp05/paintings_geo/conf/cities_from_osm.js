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

// custom function to normalize entities from dbpedia at runtime
function normalize(namespace, original) {
	if (original != null) {
		return original.replace(namespace,"").replace("_"," ").replace("%20"," ").trim();
	} else {
		return original;
	}
}

// custom function to retrieve th reference to another core
function getCore(cmd, coreName) {
	var core = cmd.getReq().getCore();
	var req = cmd.getReq();
	var container = core.getCoreDescriptor().getCoreContainer();
	return container.getCore(coreName);
}

function processAdd(cmd) {

	var ns = "http://dbpedia.org/resource/";

	// SEE: org.apache.solr.common.SolrInputDocument
	var doc = cmd.solrDoc;
	var uri = doc.getFieldValue("uri") + "";
	var city = doc.getFieldValue("city") + "";

	// sets up the last update time:
	doc.setField("last_update", new java.util.Date());

	var other = getCore(cmd, 'cities');
	var searcher = other.newSearcher("/select");
	var term = new org.apache.lucene.index.Term("city", normalize(ns, city.toLowerCase()));
	var query = new org.apache.lucene.search.TermQuery(term);
	var docset = searcher.getDocSet(query);

	doc.setField("[QUERY]", "/"+other.getName()+"?q="+query.toString());
	doc.setField("[DOCS HITS]", docset.size());
	
	var doc_it = docset.iterator();
	while (doc_it.hasNext()) {
		var id = doc_it.nextDoc();
		var doc_found = searcher.doc(id);
		var city_value = doc_found.getField("city").stringValue();
		var lat = doc_found.getField("lat").stringValue();
		var lon = doc_found.getField("lon").stringValue();
		doc.setField("city_entity", normalize(ns, city_value));
		doc.setField("city_coordinates", normalize(ns, lat)+","+normalize(ns, lon));
	}

	searcher.close();
}

function processDelete(cmd) {
	// not implemented
}

function processMergeIndexes(cmd) {
	// not implemented
}

function processCommit(cmd) {
	// not implemented
}

function processRollback(cmd) {
	// not implemented
}

function finish() {
}
