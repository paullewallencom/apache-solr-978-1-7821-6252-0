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

import scala.collection.JavaConversions.asScalaBuffer

/**
 * This is an example Application to show how to construct a simple annotator using
 * the Stanford classifier and scala. This example has been used as a base for the
 * Solr examples for annotation.
 */
object MainNERAnnotator extends App {

  val serializedClassifier = "stanford_classifiers/english.all.3class.distsim.crf.ser.gz"

  val text = """
    Metamorphosis of Narcissus (1937) is an oil-on-canvas painting by the Spanish surrealist Salvador Dalí. 
    This painting is from Dalí's Paranoiac-critical period. 
    According to Greek mythology, Narcissus fell in love with his own reflection in a pool. 
    Unable to embrace the watery image, he pined away, and the gods immortalized him as a flower. 
    Dali completed this painting in 1937 on his long awaited return to Paris after having had great success in the United States. 
    The painting shows Narcissus sitting in a pool, gazing down.
  """"

  val ner = new NER(serializedClassifier)
  val keywords = ner.getAnnotations(text)

  println("\nKEYWORDS: " + keywords.toList.mkString(" | "))

  println("ANNOTATED TEXT [XML]:\n" + ner.getAnnotatedText(text, true))
  println("ANNOTATED TEXT [PLAIN]:\n" + ner.getAnnotatedText(text, false))

}
