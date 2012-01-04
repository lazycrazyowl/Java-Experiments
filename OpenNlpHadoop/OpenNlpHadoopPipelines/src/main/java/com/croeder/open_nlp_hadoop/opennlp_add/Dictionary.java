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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import opennlp.tools.dictionary.serializer.Attributes;
import opennlp.tools.dictionary.serializer.DictionarySerializer;
import opennlp.tools.dictionary.serializer.Entry;
import opennlp.tools.dictionary.serializer.EntryInserter;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.StringList;

/**
 * This class is a dictionary.
 */
public class Dictionary implements Iterable<StringList> {

  private static class StringListWrapper {

    private final StringList stringList;
    private final boolean isCaseSensitive;

    private StringListWrapper(StringList stringList, boolean isCaseSensitive) {
      this.stringList = stringList;
      this.isCaseSensitive = isCaseSensitive;
    }

    private StringList getStringList() {
      return stringList;
    }

    public boolean equals(Object obj) {

      boolean result;

      if (obj == this) {
        result = true;
      }
      else if (obj instanceof StringListWrapper) {
        StringListWrapper other = (StringListWrapper) obj;

        if (isCaseSensitive) {
          result = this.stringList.equals(other.getStringList());
        }
        else {
          result = this.stringList.compareToIgnoreCase(other.getStringList());
        }
       }
      else {
        result = false;
      }

      return result;
    }

    public int hashCode() {
      // if lookup is too slow optimize this
      return this.stringList.toString().toLowerCase().hashCode();
    }

    public String toString() {
      return this.stringList.toString();
    }
  }

  private Map<StringListWrapper, String> entryMap = new HashMap<StringListWrapper, String>();
  private boolean caseSensitive;

  /**
   * Initializes an empty {@link Dictionary}.
   */
  public Dictionary() {
    this(false);
  }

  public Dictionary(boolean caseSensitive) {
    this.caseSensitive = caseSensitive;
  }


  /**
   * Adds the tokens to the dictionary as one new entry.
   *
   * @param tokens the new entry
   */
  public void put(String id, StringList tokens) {
      entryMap.put(new StringListWrapper(tokens, caseSensitive), id);
  }

  /**
   * Checks if this dictionary has the given entry.
   *
   * @param tokens
   *
   * @return true if it contains the entry otherwise false
   */
  public boolean contains(StringList tokens) {
      return entryMap.keySet().contains(new StringListWrapper(tokens, caseSensitive));
  }

  /**
   * Removes the given tokens form the current instance.
   *
   * @param tokens
   */
  public void remove(StringList tokens) {
      entryMap.remove(new StringListWrapper(tokens, caseSensitive));
  }

  /**
   * Retrieves an Iterator over all tokens.
   *
   * @return token-{@link Iterator}
   */
  public Iterator<StringList> iterator() {
    final Iterator<StringListWrapper> entries = entryMap.keySet().iterator();

    return new Iterator<StringList>() {

      public boolean hasNext() {
        return entries.hasNext();
      }

      public StringList next() {
        return entries.next().getStringList();
      }

      public void remove() {
        entries.remove();
      }};
  }

  /**
   * Retrieves the number of tokens in the current instance.
   *
   * @return number of tokens
   */
  public int size() {
    return entryMap.keySet().size();
  }


  public boolean equals(Object obj) {

    boolean result;

    if (obj == this) {
      result = true;
    }
    else if (obj != null && obj instanceof Dictionary) {
      Dictionary dictionary  = (Dictionary) obj;

      result = entryMap.equals(dictionary.entryMap);
    }
    else {
      result = false;
    }

    return result;
  }

  public int hashCode() {
    return entryMap.hashCode();
  }

  public String toString() {
    return entryMap.toString();
  }

}
