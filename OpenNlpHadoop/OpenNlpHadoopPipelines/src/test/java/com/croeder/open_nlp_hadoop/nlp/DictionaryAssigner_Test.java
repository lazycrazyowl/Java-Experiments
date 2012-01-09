package com.croeder.open_nlp_hadoop.nlp;

import java.io.IOException;

import opennlp.tools.util.Span;

import org.junit.Test;
import org.junit.Assert;

import com.croeder.open_nlp_hadoop.opennlp_add.DictionaryNameAssigner;

public class DictionaryAssigner_Test {
	
	@Test
	public void test_1() throws IOException {
		DictionaryAssigner dfm = new DictionaryAssigner();
		String[] tokens = {
				"The",
				// chebi 16142
				"lipopolysaccharide",
				"is", 
				"cool",
				".",
		};
		String[] tokens5 = {
				"An",
				// chebi 33708
				"amino-acid", "residue",
				"is",
				"not",
				".",
		};
		String[] tokens2 = {
				"An",
				// chebi 33708
				"amino", "acid", "residue",
				"is",
				"not",
				".",
		};
		String[] tokens3 = {
				// PR:0000009054
				"Insulin", "is", "a","common", "protein", ".",
		};
		String[] tokens4 = {
				// chebi 23367
				"molecular", "entity", "is", "a", "term", "with", "multiple", "tokens", "."
		};
	
		String[][] tests = { tokens, tokens2, tokens3, tokens4, tokens5};

		for (String[] test : tests) {	
			Assert.assertTrue(dfm != null);
			DictionaryNameAssigner.Annotation[] annos = dfm.find(test);
			Assert.assertTrue(annos.length > 0);
			System.out.println(" ");
			for (String s : test) { System.out.print(s + " ");}
			System.out.println(" ");
			for (DictionaryNameAssigner.Annotation a : annos ) {
				System.out.println(a.toString());
			}
		}
		
	}

}
