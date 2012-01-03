package com.croeder.sesame.play;

import org.openrdf.query.Binding;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.sail.memory.MemoryStore;

import org.openrdf.OpenRDFException;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;

import org.openrdf.rio.RDFFormat;
import java.io.File;
import java.net.URL;
import java.util.Collection;


public class Play {

	Repository myRepository;
	public static void main(String args[]) throws Exception {	
		Play p = new Play();
		p.go();
	}
	
	public Play() throws RepositoryException  {
		myRepository = new SailRepository(new MemoryStore());
		myRepository.initialize();
	}
	
	public void go() {
		// This file:
		//File file = new File("src/main/resources/go_daily-termdb.rdf-xml");
		// is bogus. See: http://wiki.geneontology.org/index.php/File_Format_FAQ

		File file = new File("src/main/resources/go_daily-termdb.owl");
		String baseURI = "http://www.geneontology.org";

		try {
		   RepositoryConnection con = myRepository.getConnection();
		   try {
		      con.add(file, baseURI, RDFFormat.RDFXML);

		      URL url = new URL("http://example.org/example/remote");
		      //con.add(url, url.toString(), RDFFormat.RDFXML);
		      con.add(url, null, RDFFormat.RDFXML);
		      
		      
		      String queryString = "SELECT x, y FROM {x} p {y}";
		      TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SERQL, queryString);
		      TupleQueryResult result = tupleQuery.evaluate();
		      try {
		    	Collection<String> names = result.getBindingNames();
		    	while (result.hasNext()) {
		    		BindingSet bs = result.next();
		    		for (String n : names) {
		    			Binding b = bs.getBinding(n);
		    			System.out.println(n + ":" + b.getValue().toString());
		    		}
		    	}
		      }
		      finally {
		         result.close();
		      }
		   }
		   finally {
		      con.close();
		   }
		}
		catch (OpenRDFException e) {
			System.err.println("" + e);
		   e.printStackTrace();
		}
		catch (java.io.IOException e) {
			System.err.println("" + e);
		   e.printStackTrace();
		}
	}
	
}
