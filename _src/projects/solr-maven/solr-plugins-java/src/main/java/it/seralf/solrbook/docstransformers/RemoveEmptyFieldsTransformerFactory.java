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
package it.seralf.solrbook.docstransformers;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.lucene.document.Field;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.transform.DocTransformer;
import org.apache.solr.response.transform.TransformerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is useful to remove empty fields from the results
 * @author seralf
 *
 */
public class RemoveEmptyFieldsTransformerFactory extends TransformerFactory {

	@Override
	public DocTransformer create(final String field, final SolrParams params, final SolrQueryRequest req) {
		return new RemoveEmptyFieldsTransformer();
	}

	private static class RemoveEmptyFieldsTransformer extends DocTransformer {

		final String name = "noempty";
		private static final Logger logger = LoggerFactory.getLogger(RemoveEmptyFieldsTransformer.class);

		@Override
		public String getName() {
			return this.name;
		}

		private void removeEmpty(final List<?> list){
			final Iterator<?> it = list.iterator();
			while (it.hasNext()) {
				final Object obj = it.next();
				if(obj == null 
					|| obj instanceof Field && ((Field)obj).stringValue().trim().equals("")
					|| obj instanceof String && ((String)obj).trim().equals("")){
					it.remove();
				}
			}
		}
		
		@Override
		public void transform(final SolrDocument doc, final int docid) {

			final Iterator<Entry<String, Object>> it = doc.entrySet().iterator();
			while(it.hasNext()){
				final Entry<String, Object> entry = it.next();
				if(entry.getValue() == null) {
					it.remove();
				}else if(entry.getValue() instanceof Field && ((Field)entry.getValue()).stringValue().trim().equals("")){
					it.remove();
				}else if(entry.getValue() instanceof List<?>){
					final List<?> list = (List<?>)entry.getValue();
					removeEmpty(list);
					if(list.size()==0) it.remove();
				}else {
					logger.info("NOT EMPTY: " + entry.getKey());
				}
			}

		}

	}

}
