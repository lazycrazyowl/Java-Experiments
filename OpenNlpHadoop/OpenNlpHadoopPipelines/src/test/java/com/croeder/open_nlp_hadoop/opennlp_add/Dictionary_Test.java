package com.croeder.open_nlp_hadoop.opennlp_add;

import org.junit.Test;
import org.junit.Assert;

import opennlp.tools.util.StringList;

public class Dictionary_Test {

	@Test
	public void test() {
		Dictionary dict = new Dictionary();
		dict.put("1", new StringList("chair", "seat", "stool"));
		dict.put("2", new StringList("car","automobile","ride"));
		Assert.assertTrue(dict.contains(new StringList("car")));
		Assert.assertFalse(dict.contains(new StringList("foobar")));
		Assert.assertEquals(6, dict.size());
		dict.remove(new StringList("car"));
		Assert.assertFalse(dict.contains(new StringList("car")));
		Assert.assertEquals(5, dict.size());
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
