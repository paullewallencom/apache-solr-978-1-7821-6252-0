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
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamSource
import javax.xml.transform.stream.StreamResult
import java.io.File
import java.io.FileFilter

def transform(dataXML: String, inputXSL: String, outputHTML: String) = {
  try {
    val factory = TransformerFactory.newInstance();
    val xslStream = new StreamSource(inputXSL);
    val transformer = factory.newTransformer(xslStream);
    val in = new StreamSource(dataXML);
    val out = new StreamResult(outputHTML);
    transformer.transform(in, out);
    System.out.println("The generated XML file is:" + outputHTML);
  } catch {
    case e: Exception => println(e)
  }
}

// ---- MAIN ----
// EXAMPLE createSolrDocs.sh data/downloaded/ data/solr_docs ../../test/chp03/dbpedia_start/dbpediaToPost.xslt 

val rdfDir = new File(args { 0 }).getCanonicalFile()
val outDir = new File(args { 1 }).getCanonicalFile()
if (!outDir.exists()) outDir.mkdirs()
val xslt = args { 2 }

// if the directory containing RDF is wrong or does not exists, throw an exception
if (!rdfDir.exists()) throw new RuntimeException(rdfDir + " does not exists!")

val files = rdfDir.listFiles(new FileFilter() {
  override def accept(pathname: File): Boolean = {
    pathname.getName().endsWith(".rdf.xml")
  }
}).toList

files.foreach(f => {
  transform(f.getAbsolutePath(), xslt, new File(outDir, f.getName().replaceAll("\\.rdf\\.", "\\.post\\.")).getAbsolutePath())
})

