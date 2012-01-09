/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreemnets.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.croeder.open_nlp_hadoop.opennlp_add;

import java.util.LinkedList;
import java.util.List;
import opennlp.tools.dictionary.Index;
import opennlp.tools.util.Span;
import opennlp.tools.util.StringList;

/**
 * This is a dictionary based name assigner, it scans text
 * for names inside a dictionary, and returns an id into
 * an ontology or database.
 */
public class DictionaryNameAssigner  {

	public static class Annotation {
		Span span;
		String id;
		StringList[] stringLists;
	
		public Annotation(Span span, String id, StringList ... sl) {
			this.span = span; 
			this.id = id;
			this.stringLists = sl;
		}
	
		public String getId()   { return id; }
		public Span   getSpan() { return span; }

		public String toString() {
			return "Annotation: id:\"" + id 
				+ "\" span:" + span.toString() 
				+ stringListsToString();
		}		
		private String stringListsToString() {
			StringBuilder sb = new StringBuilder();
			for (StringList sl : stringLists) {
				sb.append(sl.toString() + ", ");
			}
			return sb.toString();	
		}
	}

  private com.croeder.open_nlp_hadoop.opennlp_add.Dictionary mDictionary;
  private Index mMetaDictionary;

  public DictionaryNameAssigner(Dictionary dict) {
    mDictionary = dict;
    mMetaDictionary = new Index(mDictionary.iterator());
  }

  public Annotation[] find(String[] tokenStrings) {
    List<Annotation> foundNames = new LinkedList<Annotation>();

    for (int startToken = 0; startToken < tokenStrings.length; startToken++) {
      Annotation foundName = null;
      String  tokens[] = new String[]{};

      for (int endToken = startToken; endToken < tokenStrings.length; endToken++) {
        String token = tokenStrings[endToken];

        // TODO: improve performance here
        String newTokens[] = new String[tokens.length + 1];
        System.arraycopy(tokens, 0, newTokens, 0, tokens.length);
        newTokens[newTokens.length - 1] = token;
        tokens = newTokens;

        if (mMetaDictionary.contains(token)) {
          StringList tokenList = new StringList(tokens);
          if (mDictionary.contains(tokenList)) {
			Span span = new Span(startToken, endToken + 1);

// need to use mDictionary find() and use the tokenList you get there!!!

			foundName = new Annotation(span, mDictionary.get(tokenList), tokenList);
          }
        }
        else {
          break;
        }
      }

      if (foundName != null) {
        foundNames.add(foundName);
      }
    }

    return (Annotation[]) foundNames.toArray(new Annotation[foundNames.size()]);
  }
  
  public void clearAdaptiveData() {
    // nothing to clear
  }
}
