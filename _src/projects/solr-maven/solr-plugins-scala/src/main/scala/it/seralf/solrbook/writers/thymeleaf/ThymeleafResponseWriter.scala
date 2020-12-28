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
package it.seralf.solrbook.writers.thymeleaf

import java.io.File
import java.io.Writer
import java.util.Map.Entry
import java.text.SimpleDateFormat
import java.util.Date
import org.apache.solr.common.util.NamedList
import org.apache.solr.request.SolrQueryRequest
import org.apache.solr.response.QueryResponseWriter
import org.apache.solr.response.ResultContext
import org.apache.solr.response.SolrQueryResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import org.thymeleaf.templateresolver.FileTemplateResolver
import java.util.Map
import java.util.Iterator
import org.apache.solr.common.SolrDocumentList
import org.apache.solr.common.SolrDocument
import java.util.LinkedList
import org.apache.solr.client.solrj.response.QueryResponse
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer
import org.apache.solr.util.SolrPluginUtils

class ThymeleafResponseWriter extends QueryResponseWriter {

  val logger: Logger = LoggerFactory.getLogger(this.getClass())

  def ThymeleafResponseWriter() = {
    logger.debug("\n\n\n#####################\n\n\nTHYMELEAF CREATE INSTANCE...")
  }

  override def getContentType(request: SolrQueryRequest, response: SolrQueryResponse): String = {
    logger.debug("THYMELEAF.getContentType")
    "text/html"
  }

  override def init(namedList: NamedList[_]) = {
    logger.debug("THYMELEAF PARAMETERS: {}", namedList.toString())
  }

  override def write(writer: Writer, request: SolrQueryRequest, response: SolrQueryResponse) = {
    val sb: StringBuffer = new StringBuffer
    val templateDir = new File(request.getCore().getDataDir() + "/../conf/thymeleaf").getCanonicalPath()
    val template = new File(templateDir, "home.html").getAbsolutePath()

    logger.debug("\n\n\n\n########### <p>DATA DIR: " + templateDir + "</p>\n\n\n\n")
    logger.debug("\n\n\n\n########### <p>VALUES: " + response.getValues() + "</p>\n\n\n\n")

    //// ----------------------------------------

    val now = new SimpleDateFormat("dd MMMM YYYY - HH:mm").format(new Date())
    val resultContext = response.getValues().get("response").asInstanceOf[ResultContext]

    val ctx = new Context()
    ctx.setVariable("today", now)
    ctx.setVariable("response", response)
    ctx.setVariable("resultContext", resultContext)
    ctx.setVariable("request", request)
    ctx.setVariable("core", request.getCore())
    ctx.setVariable("fields", response.getReturnFields().getLuceneFieldNames())
    ctx.setVariable("values", response.getValues())

    val dit = resultContext.docs.iterator()
    while (dit.hasNext()) {
      val d = dit.next()
      println("\n" + d)
    }

    val doclist: SolrDocumentList = SolrPluginUtils.docListToSolrDocumentList(
      resultContext.docs, request.getSearcher(),
      response.getReturnFields().getLuceneFieldNames(), null)

    println("\n\n\n_______________\nDOCLIST: " + doclist + "\n_______________\n\n")
    ctx.setVariable("docs", doclist)

    val templateEngine = new TemplateEngine()

    val resolver = new FileTemplateResolver()
    resolver.setCharacterEncoding("UTF-8")

    templateEngine.setTemplateResolver(resolver)
    templateEngine.initialize()

    //    val writer = new StringWriter()
    val result = templateEngine.process(template, ctx)

    // VIEW RESULTS
    val text = new String(result.getBytes())
    logger.debug("text")

    sb.append(text)

    writer.write(sb.toString())

  }

}
