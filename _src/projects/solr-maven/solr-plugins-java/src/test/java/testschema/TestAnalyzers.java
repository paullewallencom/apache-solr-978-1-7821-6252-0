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

import static org.apache.lucene.analysis.BaseTokenStreamTestCase.assertAnalyzesTo;
import static org.apache.lucene.analysis.BaseTokenStreamTestCase.assertTokenStreamContents;

import it.seralf.solrbook.filters.CapitalizationFilter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.it.ItalianLightStemFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.junit.Test;

public class TestAnalyzers {

	private static final class CustomAnalyzer extends Analyzer {
		@Override
		protected TokenStreamComponents createComponents(final String fieldName, final Reader reader) {
			final Tokenizer tokenizer = new WhitespaceTokenizer(Version.LUCENE_45, reader);
			final TokenFilter filter1 = new ItalianLightStemFilter(tokenizer);
			final TokenFilter filter2 = new LowerCaseFilter(Version.LUCENE_45,filter1);
			final TokenFilter filter3 = new CapitalizationFilter(filter2);
			final TokenStreamComponents components = new TokenStreamComponents(tokenizer, filter3);
			return components;
		}
	}
	
	@Test
	public void testKeywordAnalyzer() throws IOException{
		final Analyzer keywordAnalyzer = new KeywordAnalyzer();
		assertAnalyzesTo(keywordAnalyzer, "this is an example phrase", new String[] { "this is an example phrase" });
	}
	
	@Test
	public void testwhitespaceAnalyzer() throws IOException{
		final Analyzer whitespaceAnalyzer = new WhitespaceAnalyzer(Version.LUCENE_45);
		assertAnalyzesTo(whitespaceAnalyzer, "this is an example phrase", new String[] { "this", "is", "an", "example", "phrase" });
	}
	
	@Test
	public void testPorterStemFilter() throws IOException{
		final Analyzer whitespaceAnalyzer = new WhitespaceAnalyzer(Version.LUCENE_45);
		assertTokenStreamContents(
			new PorterStemFilter(whitespaceAnalyzer.tokenStream("TEST_FIELD", new StringReader("this is an example phrase"))), 
			new String[] {"thi", "is", "an", "exampl", "phrase" }
		);
	}
	
	@Test
	public void testStandardAnalyzer() throws IOException{
		final Analyzer standardAnalyzer = new StandardAnalyzer(Version.LUCENE_45);
		assertAnalyzesTo(standardAnalyzer, "this is an example phrase", new String[] { "example", "phrase" });
	}
	
	@Test
	public void testCustomAnalyzer() throws IOException{
		final Analyzer customAnalyzer = new CustomAnalyzer();
		assertAnalyzesTo(customAnalyzer, "questa è una frase di esempio", new String[] { "Quest", "È", "Una", "Frase", "Di", "Esemp" });
	}

}
