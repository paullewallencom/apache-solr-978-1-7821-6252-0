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
package it.seralf.solrbook.filters.ner

import org.apache.lucene.analysis.TokenFilter
import org.apache.lucene.analysis.TokenStream
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import it.seralf.solrbook.ner.NER
import java.util.Scanner
import java.util.Arrays
import scala.collection.JavaConverters._
import scala.collection.JavaConversions._
import org.apache.solr.util.plugin.SolrCoreAware
import org.apache.solr.core.SolrCore
import java.io.File

// NOTE: this class requires to be used with KeywordTokenizer
final class NERFilter(input: TokenStream, useXML: Boolean, classifierModel: String) extends TokenFilter(input) {

  val ner = new NER(classifierModel)
  this.clearAttributes()

  override final def incrementToken(): Boolean = {

    if (input.incrementToken()) {
      val term = input.getAttribute(classOf[CharTermAttribute])
      val text = term.buffer().toList.mkString("").trim
      term.setEmpty()
      val annotatedText = ner.getAnnotatedText(text, useXML)
      term.append(annotatedText)
      true
    } else {
      false
    }

  }

}
