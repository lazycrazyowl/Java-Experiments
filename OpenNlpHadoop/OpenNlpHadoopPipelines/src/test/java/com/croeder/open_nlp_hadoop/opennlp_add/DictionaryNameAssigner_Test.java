package com.croeder.open_nlp_hadoop.opennlp_add;

import org.junit.Test;
import org.junit.Assert;

import opennlp.tools.util.StringList;
import opennlp.tools.util.Span;

public class DictionaryNameAssigner_Test  {

  	//public Span[] find(String[] tokenStrings) {

	@Test
	public void test() {
		Dictionary dict = new Dictionary();
		dict.put("1", new StringList("chair", "seat", "stool"));
		dict.put("2", new StringList("car","automobile","ride"));
	
		DictionaryNameAssigner dna = new DictionaryNameAssigner(dict);	
		String[] tokens = {"The", "chair", "was", "in", "the", "car"};
  		Span[] spans = dna.find(tokens);
	    Assert.assertEquals(new Span(1,2), spans[0]);	
	    Assert.assertEquals(new Span(5,6), spans[1]);	
	}	
}
