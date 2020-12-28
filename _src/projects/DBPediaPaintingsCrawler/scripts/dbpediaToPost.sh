#!/bin/sh
exec scala -savecompiled "$0" "$@"
!#

import scala.xml.XML
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamSource
import javax.xml.transform.stream.StreamResult
import java.net.URL
import java.util.Calendar
import java.text.SimpleDateFormat
import java.io.File
import java.io.FileFilter
import java.io.PrintStream
import java.io.FileWriter


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

  val rdfDir = new File(".")
  val outDir = new File("solr_docs")
  val xslt = "dbpediaToPost.xslt"
  
  if (!outDir.exists()) outDir.mkdirs()

  val files = rdfDir.listFiles(new FileFilter() {
    override def accept(pathname: File): Boolean = {
      pathname.getName().endsWith(".rdf.xml")
    }
  }).toList

  files.foreach(f => {
    transform(f.getAbsolutePath(), xslt, new File(outDir, f.getName()).getAbsolutePath())
