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
		return original.replace(namespace, "").replace("_", " ").replace("%20",
				" ").trim();
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
	var ns_category = ns + "Category:";

	// SEE: org.apache.solr.common.SolrInputDocument
	var doc = cmd.solrDoc;

	// sets up the last update time:
	doc.setField("last_update", new java.util.Date());

	// add a field with the normlized value of an artist names
	doc.setField("artist_entity", normalize(ns, doc.getFieldValue("artist")));
	doc.setField("museum_entity", normalize(ns, doc.getFieldValue("museum")));
	doc.setField("city_entity", normalize(ns, doc.getFieldValue("city")));
	doc.setField("title_entity", normalize(ns, doc.getFieldValue("title")));
	doc.setField("subject_entity", normalize(ns, doc.getFieldValue("title")));
	//
	if (doc.getFieldNames().contains('subject')) {
		var subjects = doc.getField("subject").getValues();
		// doc.setField("subject_entity", subjects.toString());
		for (i = 0; i < subjects.size(); i++) {
			doc.setField("subject_entity", normalize(ns, subjects.get(i)
					.toString()));
		}
	}
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
