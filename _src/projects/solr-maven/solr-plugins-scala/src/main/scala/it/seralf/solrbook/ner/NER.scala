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

import scala.collection.JavaConverters._
import scala.collection.JavaConversions._
import java.util.LinkedList
import edu.stanford.nlp.ie.crf.CRFClassifier
import edu.stanford.nlp.ling.CoreLabel
import edu.stanford.nlp.ling.CoreAnnotations.AnswerAnnotation
import edu.stanford.nlp.ling.CoreAnnotations

class NER(serializedClassifier: String) {

  // initialize the classifier, in order to reuse it when possible
  val classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier)

  def getAnnotations(text: String) = {

    // initialize the list to be returned
    val keywords = new LinkedList[(String, String)]

    // read every sentence
    for (sentence <- classifier.classify(text)) {

      // this is a small function to decide if an annotation is interesting or not for us
      def recognized(word: CoreLabel): Boolean = {
        !word.get(classOf[CoreAnnotations.AnswerAnnotation]).equals("O") &&
          !word.get(classOf[CoreAnnotations.AnswerAnnotation]).equals("")
      }

      // get the annotation associated with a word
      def annotation(word: CoreLabel): String = word.get(classOf[CoreAnnotations.AnswerAnnotation])

      // filters out what does not interest us
      val annotatedWords = sentence.toList.filterNot(word => !recognized(word))

      // here we group the results by annotations, and produce the final format
      val map = annotatedWords
        .groupBy(word => annotation(word))
        .map(e => e._1 match {
          case "LOCATION" => (e._1, e._2.mkString(", "))
          case _ => (e._1, e._2.mkString(" "))
        })

      keywords.appendAll(map)
    }

    // return the results
    keywords
  }

  // this is a facility method useful if we want to obtain a version of the original text augmented with inline annotations
  def getAnnotatedText(text: String, useXML: Boolean) = if (useXML)
    classifier.classifyWithInlineXML(text)
  else
    classifier.classifyToString(text)

}
