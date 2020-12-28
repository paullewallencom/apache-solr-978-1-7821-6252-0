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
package it.seralf.solrbook.ner

import org.apache.tika.parser.pdf._
import org.apache.tika.metadata._
import org.apache.tika.parser._
import java.io._
import org.xml.sax._

object pdfHandler extends ContentHandler {
  val contents: StringBuffer = new StringBuffer()

  def characters(ch: Array[Char], start: Int, length: Int) {
    contents.append(new String(ch))
  }

  def endDocument() {
  }

  def endElement(uri: String, localName: String, qName: String) {
  }

  def endPrefixMapping(prefix: String) {
  }

  def ignorableWhitespace(ch: Array[Char], start: Int, length: Int) {
  }

  def processingInstruction(target: String, data: String) {
  }

  def setDocumentLocator(locator: Locator) {
  }

  def skippedEntity(name: String) {
  }

  def startDocument() {
  }

  def startElement(uri: String, localName: String, qName: String, atts: Attributes) {
  }

  def startPrefixMapping(prefix: String, uri: String) {
  }
}

/**
 * This is a simple example Application to extract text from a PDF using scala and Tika library
 * For example: >> scala pdfHandler ../../../resources/apache-solr-ref-guide-4.5.pdf
 */
object pdf extends App {

  val file = new File(args { 0 }).getCanonicalFile()

  val pdf: PDFParser = new PDFParser();

  val stream: InputStream = new FileInputStream(file)
  val handler: ContentHandler = pdfHandler
  val metadata: Metadata = new Metadata()

  pdf.parse(stream, handler, metadata)

  stream.close()

  val contents: String = pdfHandler.contents.toString()

  println(contents)

}
