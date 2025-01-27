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
import org.apache.solr.client.solrj.impl.HttpSolrServer
import org.apache.solr.common.SolrInputDocument

String url = "http://localhost:8983/solr/arts"
def server = new HttpSolrServer( url );

def doc = new SolrInputDocument()

doc.addField("uri", "http://TEST/Leonardo_da_Vinci")
doc.addField("artist", "Leonardo Da Vinci")
doc.addField("city", "Vinci")
doc.addField("note", "TEST document added to the index by groovy")
		
server.add(doc)
server.commit()
