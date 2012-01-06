package com.croeder.open_nlp_hadoop.nlp;

import opennlp.tools.namefind.DictionaryNameFinder;
import opennlp.tools.util.Span;
import opennlp.tools.util.StringList;
import opennlp.tools.dictionary.Dictionary;
import java.io.File;
import java.io.IOException;

// TODO: use tokenizer for splitting names and synonyms into tokens the same 
// way they will be found in text.

public class DictionaryFinder {
	DictionaryNameFinder _dnf; 
	static final TokenizerMapper tm = new TokenizerMapper();
	
	public DictionaryFinder() 
	throws IOException {
		Dictionary dict = new Dictionary();
		
		ProReader pr = new ProReader(new File("src/main/resources/pro.obo"));
		while (pr.hasNext()) {
			DictionaryEntry de = pr.next();
			
			// name
			dict.put( new StringList(de.getName()) );
			
			
			// synonyms
			for (String s : de.getSynonyms()) {
				StringList sl = new StringList(s.split(" "));
				dict.put(sl);
			}
			
		}
		_dnf = new DictionaryNameFinder(dict);
	}
	
	Span[] find(String[] tokens) {
		if (_dnf != null) {
			return _dnf.find(tokens);
		}
		else {
			return null;
		}
	}	
}
