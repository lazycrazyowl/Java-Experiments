package com.croeder.open_nlp_hadoop.hadoop;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

public class TokenizerMapper_Test {
	
	
	@Test
	public void test() {
		TokenizerMapper tm = new TokenizerMapper();
		String[] tokens = tm.tokenize("This is a simple test.");
		Assert.assertEquals("This", tokens[0]);
		Assert.assertEquals("is", tokens[1]);
		Assert.assertEquals("a", tokens[2]);
		Assert.assertEquals("simple", tokens[3]);
		Assert.assertEquals("test", tokens[4]);
		Assert.assertEquals(".", tokens[5]);
	}
	@Test
	public void test2() {
		TokenizerMapper tm = new TokenizerMapper();
		String[] tokens = tm.tokenize("This is a bio test p-53");
		Assert.assertEquals("This", tokens[0]);
		Assert.assertEquals("is", tokens[1]);
		Assert.assertEquals("a", tokens[2]);
		Assert.assertEquals("bio", tokens[3]);
		Assert.assertEquals("test", tokens[4]);
		Assert.assertEquals("p-53", tokens[5]);		
	}
}
