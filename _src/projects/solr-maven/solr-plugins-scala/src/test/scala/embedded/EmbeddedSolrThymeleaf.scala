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
package embedded

import scala.collection.JavaConversions._
import org.apache.solr.client.solrj.SolrQuery
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer
import org.apache.solr.core.CoreContainer
import org.thymeleaf.TemplateEngine
import org.thymeleaf.templateresolver.FileTemplateResolver
import java.io.File
import org.thymeleaf.context.Context

/**
 *  Experimental Main to test Thymeleaf integration
 */
object EmbeddedSolrThymeleaf extends App {

  // EXAMPLE:
  val solr_home = new File("../../../solr-app/chp09/").getCanonicalPath()
  System.setProperty("solr.solr.home", solr_home)

  val coreContainer = new CoreContainer()
  coreContainer.load()

  println("CORES: " + coreContainer.getAllCoreNames())

  val server = new EmbeddedSolrServer(coreContainer, "arts_thymeleaf")

  val solrQuery = new SolrQuery().
    setQuery("*:*").
    setFacet(true).
    setFacetMinCount(1).
    setFacetLimit(8).
    addFacetField("artist").
    addFacetField("subject")

  val response = server.query(solrQuery)

  val docs = response.getResults()
  println("DOCS " + docs)

  docs.foreach(doc => {
    println(doc.getFieldValueMap())
  })

  response.getResults().getNumFound()

  val sb: StringBuffer = new StringBuffer

  val core = server.getCoreContainer().getCore("arts_thymeleaf")
  val templateDir = new File(core.getDataDir() + "/../conf/thymeleaf").getCanonicalPath()
  val template = new File(templateDir, "home.html").getAbsolutePath()

  val ctx = new Context()
  ctx.setVariable("response", response)

  val templateEngine = new TemplateEngine()

  val resolver = new FileTemplateResolver()
  resolver.setCharacterEncoding("UTF-8")

  templateEngine.setTemplateResolver(resolver)
  templateEngine.initialize

  val result = templateEngine.process(template, ctx)

  val text = new String(result.getBytes())

  println("\n\n#########################################\n\nHTML:\n" + text)

  server.shutdown()

}
