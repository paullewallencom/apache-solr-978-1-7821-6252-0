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
package testschema;

import java.io.Reader
import java.io.StringReader
import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.Analyzer.TokenStreamComponents
import org.apache.lucene.analysis.BaseTokenStreamTestCase.assertAnalyzesTo
import org.apache.lucene.analysis.BaseTokenStreamTestCase.assertTokenStreamContents
import org.apache.lucene.analysis.core.KeywordAnalyzer
import org.apache.lucene.analysis.core.LowerCaseFilter
import org.apache.lucene.analysis.core.WhitespaceAnalyzer
import org.apache.lucene.analysis.core.WhitespaceTokenizer
import org.apache.lucene.analysis.en.PorterStemFilter
import org.apache.lucene.analysis.it.ItalianLightStemFilter
import org.apache.lucene.analysis.miscellaneous.CapitalizationFilter
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.util.Version
import org.junit.Test
import it.seralf.solrbook.filters.ner.NERFilter
import it.seralf.solrbook.tokenizers.NERTokenizer

class TestNER {

  class CustomItalianAnalyzer extends Analyzer {
    override def createComponents(fieldName: String, reader: Reader): TokenStreamComponents = {
      val tokenizer = new WhitespaceTokenizer(Version.LUCENE_45, reader)
      val filter1 = new ItalianLightStemFilter(tokenizer)
      val filter2 = new LowerCaseFilter(Version.LUCENE_45, filter1)
      val filter3 = new CapitalizationFilter(filter2)
      val components = new TokenStreamComponents(tokenizer, filter3)
      components
    }
  }

  @Test
  def testCustomAnalyzer() = {
    val customAnalyzer = new CustomItalianAnalyzer()
    assertAnalyzesTo(customAnalyzer, "questa è una frase di esempio", List("Quest", "È", "Una", "Frase", "Di", "Esemp").toArray)
  }

  @Test
  def testwhitespaceAnalyzer() = {
    val whitespaceAnalyzer = new WhitespaceAnalyzer(Version.LUCENE_45)
    assertAnalyzesTo(whitespaceAnalyzer, "this is an example phrase", List("this", "is", "an", "example", "phrase").toArray)
  }

  @Test
  def testPorterStemFilter() = {
    val whitespaceAnalyzer = new WhitespaceAnalyzer(Version.LUCENE_45)
    assertTokenStreamContents(
      new PorterStemFilter(whitespaceAnalyzer.tokenStream("TEST_FIELD", new StringReader("this is an example phrase"))),
      List("thi", "is", "an", "exampl", "phrase").toArray)
  }

  @Test
  def testStandardAnalyzer() = {
    val standardAnalyzer = new StandardAnalyzer(Version.LUCENE_45)
    assertAnalyzesTo(standardAnalyzer, "this is an example phrase", List("example", "phrase").toArray)
  }

  @Test
  def testNERFilterXML() = {
    val tokenizer_stream = new KeywordAnalyzer().tokenStream("TEST_FIELD", new StringReader("Leonardo da Vinci was the italian painter who painted the Mona Lisa."))
    assertTokenStreamContents(
      new NERFilter(tokenizer_stream,
        true, "stanford_classifiers/english.all.3class.distsim.crf.ser.gz"),
      List("<PERSON>Leonardo da Vinci</PERSON> was the italian painter who painted the <PERSON>Mona Lisa</PERSON>.").toArray)
  }

  @Test
  def testNERFilterNoXML() = {
    val tokenizer_stream = new KeywordAnalyzer().tokenStream("TEST_FIELD", new StringReader("Leonardo da Vinci was the italian painter who painted the Mona Lisa."))
    assertTokenStreamContents(
      new NERFilter(tokenizer_stream,
        false, "stanford_classifiers/english.all.3class.distsim.crf.ser.gz"),
      List("Leonardo/PERSON da/PERSON Vinci/PERSON was/O the/O italian/O painter/O who/O painted/O the/O Mona/PERSON Lisa/PERSON./O").toArray)
  }

  class NaiveNERAnalyzer extends Analyzer {
    override def createComponents(fieldName: String, reader: Reader): TokenStreamComponents = {
      val tokenizer = new NERTokenizer(reader)
      val components = new TokenStreamComponents(tokenizer)
      components
    }
  }

}
