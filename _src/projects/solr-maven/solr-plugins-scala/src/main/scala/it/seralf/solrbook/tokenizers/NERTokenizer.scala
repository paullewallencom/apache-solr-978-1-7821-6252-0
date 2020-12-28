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
package it.seralf.solrbook.tokenizers

import java.io.Reader
import java.util.Scanner
import org.apache.lucene.analysis.Tokenizer
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute
import org.apache.lucene.analysis.tokenattributes.TypeAttribute
import it.seralf.solrbook.ner.NER
import org.apache.lucene.analysis.BaseTokenStreamTestCase.CheckClearAttributesAttribute
import org.apache.lucene.analysis.tokenattributes.PositionLengthAttribute
import org.apache.lucene.analysis.BaseTokenStreamTestCase.CheckClearAttributesAttribute
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute
import org.apache.lucene.analysis.tokenattributes.PositionLengthAttribute
import org.apache.lucene.analysis.tokenattributes.TypeAttribute

/**
 * This class can be used as an idea to start writing a simple custom tokenizer.
 */
final class NERTokenizer(input: Reader) extends Tokenizer(input) {

  //  SEE: http://wiki.apache.org/solr/UpdateRequestProcessor

  val serializedClassifier = "stanford_classifiers/english.all.3class.distsim.crf.ser.gz"
  val ner = new NER(serializedClassifier)
  val sb = new Scanner(input).useDelimiter("\\Z")
  val text = sb.next()
  val keywords = ner.getAnnotations(text)
  val iterator = keywords.iterator()

  val annotatedText = ner.getAnnotatedText(text, false)

  def NERTokenizer() = {
  }

  val checkClearAtt = addAttribute(classOf[CheckClearAttributesAttribute])
  val termAttribute = addAttribute(classOf[CharTermAttribute])
  val offset = addAttribute(classOf[OffsetAttribute])
  val posInc = addAttribute(classOf[PositionIncrementAttribute])
  val posLen = addAttribute(classOf[PositionLengthAttribute])
  val typeAtt = addAttribute(classOf[TypeAttribute])
  var done: Boolean = false

  this.reset()

  override final def incrementToken(): Boolean = {

    this.clearAttributes()
    termAttribute.setEmpty().append("bogusTerm");
    if (offset != null) offset.setOffset(14584724, 24683243);
    if (typeAtt != null) typeAtt.setType("bogusType");
    if (posInc != null) posInc.setPositionIncrement(45987657);
    if (posLen != null) posLen.setPositionLength(45987653);

    checkClearAtt.getAndResetClearCalled(); // reset it, because we called clearAttribute() before

    true

  }

}
