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
		dict.put("1", new StringList("chair"));
		dict.put("1", new StringList("seat"));
		dict.put("1", new StringList("stool"));
		dict.put("2", new StringList("car"));
		dict.put("2", new StringList("automobile"));
		dict.put("2", new StringList("ride"));
	
		DictionaryNameAssigner dna = new DictionaryNameAssigner(dict);	
		String[] tokens = {"The", "chair", "was", "in", "the", "car"};
  		DictionaryNameAssigner.Annotation[] annos = dna.find(tokens);

		Assert.assertTrue(0 < annos.length);
		Assert.assertEquals(annos[0].getId(), "1");
		Assert.assertEquals(annos[0].getSpan(), new Span(1,2));
		Assert.assertEquals(annos[1].getSpan(), new Span(5,6));
		Assert.assertEquals(annos[1].getId(), "2");
	}	
}
