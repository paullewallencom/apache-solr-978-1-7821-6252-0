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
package it.seralf.solrbook.client.javascript;

import java.io.FileInputStream;
import java.util.Scanner;

import org.apache.bsf.BSFManager;

public class JavascriptExample {

	public static void main(String[] args) throws Exception {

		String myScript = new Scanner(new FileInputStream("scripts/javascript/example.js")).useDelimiter("\\Z").next();
		BSFManager manager = new BSFManager();
		manager.eval("javascript", "example.js", 0, 0, myScript);
		
	}

}
