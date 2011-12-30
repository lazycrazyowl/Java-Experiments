package com.croeder.open_nlp_hadoop.nlp;

import java.io.IOException;

import opennlp.tools.util.Span;

import org.junit.Test;
import org.junit.Assert;

public class DictionaryFinderMap_Test {
	
	@Test
	public void test_1() throws IOException {
		DictionaryFinderMapper dfm = new DictionaryFinderMapper();
		String[] tokens = {
				"The",
				"lipopolysaccharide",
				"is", 
				"cool",
				".",
				"An",
				"amino-acid", 
				"residue",
				"is",
				"not",
				"."
		};
		
		Assert.assertTrue(dfm != null);
		Span[] spans = dfm.find(tokens);
		Assert.assertTrue(spans.length > 0);
		for (Span s : spans ) {
			System.out.println("" + s.getStart() + ", " + s.getEnd() + " " + tokens[s.getStart()]);
		}
		
	}

}
