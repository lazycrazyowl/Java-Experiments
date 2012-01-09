package com.croeder.open_nlp_hadoop.nlp;

import java.util.Iterator;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.util.Collection;
import java.util.ArrayList;

public class ProReader implements Iterator<DictionaryEntry> {
	BufferedReader br;
	
//			[Term]
//			id: GO:0000015
//			name: phosphopyruvate hydratase complex
//			namespace: cellular_component
//			def: "A multimeric enzyme complex, usually a dimer or an octamer, that catalyzes the conversion of 2-phospho-D-glycerate to phosphoenolpyruvate and water." [GOC:jl, ISBN:0198506732 "Oxford Dictionary of Biochemistry and Molecular Biology"]
//			synonym: "enolase complex" EXACT []
//			is_a: GO:0043234 ! protein complex
	
	public ProReader(File proFile) 
	throws FileNotFoundException {
		br = new BufferedReader(new FileReader(proFile));
	}

	/**
	 * advances through file looking for the start of another term
	 * section and returns True if it finds one. If it gets to the end
	 * of the file before finding one, it returns false.
	 * I/O exception returns false too.
	 */
	public boolean hasNext() {
		try {
			while (br.ready()) {
				String s = br.readLine();
				if (s.equals("[Term]")) {
					return true;
				}
			}
		} catch (IOException e) {
			return false;
		}
		return false;
	}

	
	public DictionaryEntry next() {
		DictionaryEntry de=null;
		String id=null, name=null, def=null;
		Collection<String> synonyms= new ArrayList<String>();
		boolean done = false;
		try {
			while (br.ready() && !done) {
				String s = br.readLine();
				if (s.startsWith("id:")) {
					id = s.split(" ")[1];
				}
				if (s.startsWith("name:")) {
					// name: foo bar baz
					name = s.split(":")[1].trim();
				}
				if (s.startsWith("def:")) {
					def = s.split(" ")[1];
				}
				if (s.startsWith("synonym:")) {
					// synonym: "foo bar baz"
					synonyms.add(s.split("\"")[1]);
				}
				if (s.length() == 0) {
					done = true;
					de = new DictionaryEntry(id, name, def, synonyms);
				}
			}
			return de;
		}
		catch (IOException x) {
			return null;
		}
	}

	/**
	 * unsupported
	 */
	public void remove() {
		throw new UnsupportedOperationException("");	
	}

}
