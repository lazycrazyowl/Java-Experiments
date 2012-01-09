package com.croeder.open_nlp_hadoop.nlp;

import org.junit.Test;
import org.junit.Assert;

import opennlp.tools.util.StringList;

public class StringList_Test {

	@Test
	public void test_hashCode() {
		java.util.Set<Integer> hashSet = new java.util.HashSet<Integer>();
		java.util.List<StringList> list = new java.util.ArrayList<StringList>();
		StringList sl = new StringList("what", "the", "heck");
		System.out.println("" + sl + ", " + sl.hashCode());
		list.add(sl);
		sl = new StringList("what", "the");
		System.out.println("" + sl + ", " + sl.hashCode());
		list.add(sl);
		sl = new StringList("what");
		System.out.println("" + sl + ", " + sl.hashCode());
		list.add(sl);
		sl = new StringList("the");
		System.out.println("" + sl + ", " + sl.hashCode());
		list.add(sl);
		sl = new StringList("heck");
		System.out.println("" + sl + ", " + sl.hashCode());
		list.add(sl);
		

		for (StringList slist : list) {
			hashSet.add(slist.hashCode());
		}

		Assert.assertEquals(5,hashSet.size());

	}

}
