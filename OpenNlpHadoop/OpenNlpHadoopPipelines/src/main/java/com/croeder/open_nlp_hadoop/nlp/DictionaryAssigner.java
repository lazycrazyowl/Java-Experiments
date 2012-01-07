package com.croeder.open_nlp_hadoop.nlp;

import com.croeder.open_nlp_hadoop.opennlp_add.DictionaryNameAssigner;
import opennlp.tools.util.Span;
import opennlp.tools.util.StringList;
import com.croeder.open_nlp_hadoop.opennlp_add.Dictionary;
import java.io.File;
import java.io.IOException;

// TODO: use tokenizer for splitting names and synonyms into tokens the same 
// way they will be found in text.

public class DictionaryAssigner {
	DictionaryNameAssigner _dna; 
	static final TokenizerMapper tm = new TokenizerMapper();
	
	public DictionaryAssigner() 
	throws IOException {
		Dictionary dict = new Dictionary();
		
		ProReader pr = new ProReader(new File("src/main/resources/pro.obo"));
		while (pr.hasNext()) {
			DictionaryEntry de = pr.next();
			
			// name
			dict.put(de.getId(), new StringList(de.getName()) );
			
			
			// synonyms
			for (String s : de.getSynonyms()) {
				StringList sl = new StringList(s.split(" "));
				dict.put(de.getId(), sl);
			}
			
		}
		_dna = new DictionaryNameAssigner(dict);
	}
	
	DictionaryNameAssigner.Annotation[] find(String[] tokens) {
		if (_dna != null) {
			return _dna.find(tokens);
		}
		else {
			return null;
		}
	}	
}
