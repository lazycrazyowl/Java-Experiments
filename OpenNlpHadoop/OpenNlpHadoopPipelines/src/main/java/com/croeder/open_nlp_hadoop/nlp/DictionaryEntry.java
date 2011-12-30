package com.croeder.open_nlp_hadoop.nlp;

import java.util.Collection;

public class DictionaryEntry {
	String _id;
	String _name;
	Collection<String> _synonyms;
	String _definition;
	
	public Collection<String> getSynonyms() {
		return _synonyms;
	}
	
	public String getId() {
		return _id;
	}
	
	public String getName() {
		return _name;
	}

	public String getDefinition() {
		return _definition;
	}

	public DictionaryEntry(String id, String name, String definition, Collection<String> synonyms) {
		_id = id;
		_name = name;
		_definition = definition;
		_synonyms = synonyms;
	}

}
