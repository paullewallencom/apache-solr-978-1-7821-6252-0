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

import org.apache.lucene.analysis.TokenStream
import org.apache.lucene.analysis.util.TokenFilterFactory
import java.util.Map
import java.util.HashMap
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.apache.lucene.analysis.util.ResourceLoaderAware
import org.apache.lucene.analysis.util.ResourceLoader
import java.lang.Boolean
import java.io.File

final class NERFilterFactory(args: Map[String, String]) extends TokenFilterFactory(args) with ResourceLoaderAware {

  val logger = LoggerFactory.getLogger(classOf[NERFilterFactory])

  assureMatchVersion()

  val useXML: Boolean = getBoolean(args, "useXMLAnnotations", false)
  val model: String = new File(this.require(args, "model")).getCanonicalPath()

  override def create(input: TokenStream): TokenStream = {
    
    new NERFilter(input, useXML, model)
  }

  override def inform(loader: ResourceLoader) = {

  }
}

