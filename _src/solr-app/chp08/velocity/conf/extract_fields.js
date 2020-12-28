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

function processAdd(cmd) {
	var doc = cmd.solrDoc;
	// sets up the last update time:
	doc.setField("last_update", new java.util.Date());
	// add separate fields for born/died informations
	var bd_pattern = "BORN-DIED=\\((b\\.\\s+(\\d{4})\\s*,\\s*(.*))\\s*,\\s*(d\\.\\s+(\\d{4})\\s*,\\s*(.*))\\)";
	var born_died = doc.getField("BORN-DIED").toString();
	doc.setField("born_year", born_died.replaceAll(bd_pattern, "$2"));
	doc.setField("born_place", born_died.replaceAll(bd_pattern, "$3"));
	doc.setField("died_year", born_died.replaceAll(bd_pattern, "$5"));
	doc.setField("died_place", born_died.replaceAll(bd_pattern, "$6"));
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
