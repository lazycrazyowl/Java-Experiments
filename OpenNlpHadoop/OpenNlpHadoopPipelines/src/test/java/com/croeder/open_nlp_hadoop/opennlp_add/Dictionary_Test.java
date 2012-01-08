package com.croeder.open_nlp_hadoop.opennlp_add;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import opennlp.tools.util.StringList;

public class Dictionary_Test {

	@Test
	public void test_1() {
		Dictionary dict = new Dictionary();
		dict.put("1", new StringList("chair", "seat", "stool"));
		dict.put("2", new StringList("car","automobile","ride"));
		assertTrue(dict.contains(new StringList("car", "automobile", "ride")));
	}

	@Test
	public void test_2() {
		com.croeder.open_nlp_hadoop.opennlp_add.Dictionary dict = new com.croeder.open_nlp_hadoop.opennlp_add.Dictionary();
		dict.put("1", new StringList("chair", "seat", "stool"));
		dict.put("2", new StringList("car","automobile","ride"));
		StringList sl = new StringList("car", "automobile", "ride");
		assertEquals("2", dict.get(sl));
	}
	@Test
	public void test_3() {
		Dictionary dict = new Dictionary();
		dict.put("1", new StringList("chair", "seat", "stool"));
		dict.put("2", new StringList("car","automobile","ride"));
		Assert.assertEquals("1", dict.get(new StringList("chair", "seat", "stool")) );
	}
	@Test
	public void test_4() {
		Dictionary dict = new Dictionary();
		dict.put("1", new StringList("chair", "seat", "stool"));
		dict.put("2", new StringList("car","automobile","ride"));
		Assert.assertFalse(dict.contains(new StringList("foobar")));
	}
	@Test
	public void test_5() {
		Dictionary dict = new Dictionary();
		dict.put("1", new StringList("chair", "seat", "stool"));
		dict.put("2", new StringList("car","automobile","ride"));
		Assert.assertEquals(2, dict.size());
	}
	@Test
	public void test_6() {
		Dictionary dict = new Dictionary();
		dict.put("1", new StringList("chair", "seat", "stool"));
		dict.put("2", new StringList("car","automobile","ride"));
		Assert.assertTrue(  dict.contains(new StringList("chair", "seat", "stool")));
	}
	@Test
	public void test_7() {
		Dictionary dict = new Dictionary();
		dict.put("1", new StringList("chair", "seat", "stool"));
		dict.put("2", new StringList("car","automobile","ride"));
		Assert.assertEquals(dict.get(    new StringList("chair", "seat", "stool")), "1");
	}
	@Test
	public void test_8() {
		Dictionary dict = new Dictionary();
		dict.put("1", new StringList("chair", "seat", "stool"));
		dict.put("2", new StringList("car","automobile","ride"));
		dict.remove(new StringList("car", "automobile", "ride"));
		Assert.assertFalse( dict.contains(new StringList("car", "automobile", "ride")));
		Assert.assertTrue(  dict.contains(new StringList("chair", "seat", "stool")));
		Assert.assertEquals(dict.get(    new StringList("chair", "seat", "stool")), "1");
		Assert.assertEquals(1, dict.size());
	}

  //public void put(String id, StringList tokens) {
  //public boolean contains(StringList tokens) {
  //public void remove(StringList tokens) {
  //public Iterator<StringList> iterator() {
  //public int size() {

  //public boolean equals(Object obj) {
  //public int hashCode() {
  //public String toString() {
}
