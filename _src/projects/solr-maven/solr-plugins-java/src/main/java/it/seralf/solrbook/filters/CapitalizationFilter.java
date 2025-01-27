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
package it.seralf.solrbook.filters;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public final class CapitalizationFilter extends TokenFilter {

	private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

	public CapitalizationFilter(final TokenStream in) {
		super(in);
	}

	@Override
	public final boolean incrementToken() throws IOException {
		if (input.incrementToken()) {
			// manipulation of the chars buffer
			final char[] chars = termAtt.buffer();
			chars[0] = Character.toUpperCase(chars[0]);
			for (int i = 1; i < chars.length; i++)
				chars[i] = Character.toLowerCase(chars[i]);
			return true;
		} else {
			return false;
		}
	}
}
