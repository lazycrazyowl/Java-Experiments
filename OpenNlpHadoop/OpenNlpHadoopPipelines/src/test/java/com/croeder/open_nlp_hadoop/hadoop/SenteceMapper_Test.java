package com.croeder.open_nlp_hadoop.hadoop;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

public class SenteceMapper_Test {
	
	static String[] cases = {"This is one sentence.", "And this is another.", "sp-53 is abbr. of its real name."};
	
	@Test
	public void test_1() {
		SentenceMapper sm = new SentenceMapper();
		String allTogether="";
		for (String s : cases) {
			allTogether+=s;
			allTogether+=" ";
		}
		String[] sentences = sm.getSentences(allTogether);
		int i=0;
		for (String s: sentences) {
			System.out.println("\"" + s + "\"" + "\n\"" + cases[i] + "\"");
			Assert.assertEquals(cases[i++], s);
		}

	}

}
