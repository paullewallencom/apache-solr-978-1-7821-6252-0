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
package it.seralf.solrbook.crawler.dbpedia

object downloadMoreEntities extends App {

  import scala.xml.Elem
  import scala.xml.XML
  import java.io.File
  import java.io.PrintStream
  import java.net.URL
  import java.io.FileOutputStream
  import java.util.Scanner
  import scala.io.Source

  def decodeHtmlEntities(html: String) = html.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&eq;", "==").replaceAll("&quot;", "\"")

  def normalizeXMLtext(xml: String): String = {
    decodeHtmlEntities(xml)
      .replaceAll("\\b(.+?)\\b=\"<.+?>(.+?)<.+?>\"", "$1=\"$2\"")
  }

  def saveFile(file: File, content: String) {
    val dir = file.getParentFile()
    if (!dir.exists()) dir.mkdirs()
    val outFile = new File(dir, file.getName() + ".xml")
    println("\tSAVE PAGE TO: " + outFile.getCanonicalPath() + "\n")
    val text = normalizeXMLtext(content)
    (new PrintStream(new FileOutputStream(outFile))).println(text)
  }

  def downloadURL(url: URL, dirName: String): Unit = {
    try {
      val name = url.getPath().toString().replaceAll("^.*/(.*?)$", "$1")
      println("DOWNLOADING PAGE FROM: " + url)
      val xml = XML.load(url)

      val dir = new File(dirName)
      if (!dir.exists()) dir.mkdirs()
      val outFile = new File(dir, name + ".xml")

      println("\tSAVE PAGE TO: " + outFile.getCanonicalPath() + "\n")
      val text = normalizeXMLtext(xml.toString)
      (new PrintStream(new FileOutputStream(outFile))).println(text)

    } catch {
      case e: Throwable => System.err.println("ERROR with url: " + url + "\n" + e.getMessage())
    }
  }

  /**
   * get a list of links from a file
   */
  def getList(xml: Elem, name: String) = for (a <- (xml \\ name); x <- a.attributes) yield {
    val attr = x.value.toString
    new URL("http://dbpedia.org/data/" + attr.substring(attr.lastIndexOf("/") + 1) + ".rdf")
  }

  /**
   * download the RDF files into a specific directory
   */
  def downloadAll(dir: String, entities: Seq[URL]) {
    var i = 0
    entities.foreach(entity => {
      println("LOAD " + entity)
      val xml = Source.fromURL(entity).mkString
      val outFile = new File(dir, entity.getPath().substring(entity.getPath().lastIndexOf("/") + 1).replace("Category:", "_"))
      if (!outFile.exists()) {
        println("[" + i + "/" + entities.length + "]SAVING FILE: " + outFile)
        saveFile(outFile, xml.toString)
      } else {
        println("[" + i + "/" + entities.length + "]FILE: " + outFile + " already saved")
      }
      i = i + 1
    })
  }

  // MAIN
  // usage: downloadMoreEntities /path/to/SolrStarterBook/resources/dbpedia_paintings/downloaded 
  val paintings_rdf_dir = new File(args { 0 }).getAbsoluteFile()
  val paintings = paintings_rdf_dir.listFiles().filter(f => f.getName().endsWith(".rdf.xml"))
  // resources_dir is used to create the other directories where the dbpedia_painting is
  val resources_dir = paintings_rdf_dir.getParentFile().getParent()

  paintings.foreach(painting => {

    var p = 0

    try {
      val fileXML = XML.load(painting.toURL())

      val artists = getList(fileXML, "artist")
      val museums = getList(fileXML, "museum")
      val subjects = getList(fileXML, "subject")

      println(s"\n\n[" + p + "/${paintings.length}]file: $painting\n\tartists: $artists\n\tsubjects: $subjects\n\tmuseums: $museums")

      // TODO: references update... FOLDER!!!!!
      downloadAll(resources_dir + "/dbpedia_artists/downloaded", artists);
      downloadAll(resources_dir + "/dbpedia_museums/downloaded", museums);
      downloadAll(resources_dir + "/dbpedia_subjects/downloaded", subjects);

      p = p + 1

    } catch {
      case e: Throwable => System.err.println("ERROR with file: " + painting + "\n" + e.getMessage())
    }

  })

}
