package com.croeder.open_nlp_hadoop.nlp;

import org.junit.Test;
import org.junit.Assert;
import java.io.File;

public class ProReader_Test {

	@Test
	public void test_1() throws Exception {
		ProReader pr = new ProReader(new File("src/main/resources/pro.obo"));
		if (pr.hasNext()) {
			DictionaryEntry de = pr.next();
			Assert.assertEquals("CHEBI:16412", de.getId());
			Assert.assertEquals("lipopolysaccharide", de.getName());
			
			Assert.assertEquals("LPS", de.getSynonyms().iterator().next());
			
		}
		
	}
}
