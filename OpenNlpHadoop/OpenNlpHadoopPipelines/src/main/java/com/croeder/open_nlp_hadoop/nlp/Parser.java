package com.croeder.open_nlp_hadoop.nlp;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;

import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.AbstractBottomUpParser;
import opennlp.tools.util.Span;

import com.croeder.open_nlp_hadoop.hadoop.TokenizerMapper;

//http://danielmclaren.com/2007/05/11/getting-started-with-opennlp-natural-language-processing/
// TODO: assumes a single space between tokens in Sentence.

public class Parser {
    opennlp.tools.parser.Parser parser;
	TokenizerMapper tm;
	
	public Parser() 
	throws IOException {
		InputStream modelIn=null;
        try {
            //modelIn =  new FileInputStream("src/main/resources/en-parser-chunking.bin");
            modelIn =  this.getClass().getClassLoader().getResourceAsStream("en-parser-chunking.bin");
            ParserModel model = new ParserModel(modelIn);
			parser = ParserFactory.create(model);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
		tm = new TokenizerMapper();
	}

	public  Parse[] parse(String sentence) {
		String[] tokens = tm.tokenize(sentence);
		return parse(sentence, Arrays.asList(tokens));
	}

	public  Parse[] parse(String sentence, List<String> tokens) {
		Parse inputParse = new Parse(sentence, 
								new Span(0, sentence.length()), "INC", 1, null);
		int numParses=0;
		int start=0;
		for (String tok : tokens) {
			Span s = new Span(start, start + tok.length());
			// TODO: assumes a single space between tokens in Sentence.
			start += tok.length() + 1;
			inputParse.insert(new Parse(sentence, s, AbstractBottomUpParser.TOK_NODE, 1.0, numParses));
			numParses++;
		}
		return parser.parse(inputParse, numParses);
	}	
}
