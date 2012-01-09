package com.croeder.open_nlp_hadoop.nlp;

import com.croeder.open_nlp_hadoop.opennlp_add.DictionaryNameAssigner;
import com.croeder.open_nlp_hadoop.opennlp_add.Dictionary;
import com.croeder.open_nlp_hadoop.hadoop.TokenizerMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

import opennlp.tools.util.Span;
import opennlp.tools.util.StringList;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;

// TODO: fix path to resources

public class DictionaryAssigner {
        Tokenizer tokenizer;
	DictionaryNameAssigner _dna; 
	
	public DictionaryAssigner() 
	throws IOException {
		Dictionary dict = new Dictionary();
                InputStream modelIn=null;
                try {
                        modelIn =  new FileInputStream("src/main/resources/en-token.bin");
                        TokenizerModel model = new TokenizerModel(modelIn);
                        tokenizer = new TokenizerME(model);
                }
                catch (IOException e) {
                  e.printStackTrace();
                }
		
		ProReader pr = new ProReader(new File("src/main/resources/pro.obo"));
		while (pr.hasNext()) {
			DictionaryEntry de = pr.next();
			
			// name
			dict.put(de.getId(), new StringList(tokenizer.tokenize(de.getName())) );
			
			// synonyms
			for (String s : de.getSynonyms()) {
				dict.put(de.getId(), new StringList( tokenizer.tokenize(s)));
			}
			
		}
		_dna = new DictionaryNameAssigner(dict);
	}
	
	public DictionaryNameAssigner.Annotation[] find(String[] tokens) {
		if (_dna != null) {
			return _dna.find(tokens);
		}
		else {
			return null;
		}
	}	
}
