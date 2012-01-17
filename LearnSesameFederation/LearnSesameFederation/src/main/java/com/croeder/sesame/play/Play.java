package com.croeder.sesame.play;


import org.openrdf.sail.memory.MemoryStore;

import org.openrdf.OpenRDFException;
import org.openrdf.query.Binding;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.QueryEvaluationException;

import org.openrdf.rio.RDFFormat;
import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.io.IOException;

public class Play {

	Repository myRepository;

	public static void main(String args[]) throws Exception {	
		Play p = new Play();
		p.go();
	}
	
	public Play() throws RepositoryException  {
		myRepository =  new SailRepository(new MemoryStore());
		myRepository.initialize();
	}
	
		//<rdf:Description
		//rdf:about="http://www.recshop.fake/cd/Empire Burlesque">
		//  <cd:artist>Bob Dylan</cd:artist>
		//  <cd:country>USA</cd:country>
		//  <cd:company>Columbia</cd:company>
		//  <cd:price>10.90</cd:price>
		//  <cd:year>1985</cd:year>
		//</rdf:Description>

		//name:x value:http://www.recshop.fake/cd/Empire Burlesque
		//name:y value:http://www.recshop.fake/cd#artist
		//name:z value:"Bob Dylan"


	TupleQueryResult runSerqlQuery(RepositoryConnection con) 
    throws org.openrdf.query.QueryEvaluationException,
           org.openrdf.query.MalformedQueryException, 
           org.openrdf.repository.RepositoryException {
		      //String queryString = "SELECT x, y FROM {x} p {y}";
		      String queryString = "SELECT x, y, z FROM {x} y {z}";
		      TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SERQL, queryString);
	      return  tupleQuery.evaluate();
    }

	TupleQueryResult runSparqlQuery(RepositoryConnection con) 
    throws org.openrdf.query.QueryEvaluationException,
           org.openrdf.query.MalformedQueryException,
           org.openrdf.repository.RepositoryException {
		      String queryString = "PREFIX cd: <http://www.recshop.fake/cd#> "
						+ "SELECT  ?x ?p  ?a  "
						+ "WHERE { ?x cd:artist   ?a  } "; 
		      //String queryString = "PREFIX cd: <http://www.recshop.fake/cd> "
						//+ "SELECT  ?x ?p   "
						//+ "WHERE { ?x ?p  'USA'  } ";
		      //String queryString = "PREFIX cd: <http://www.recshop.fake/cd> "
						//+ "SELECT  ?x ?p  ?a  "
						//+ "WHERE { ?x ?p ?a  } "; // works
		      TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
		      return  tupleQuery.evaluate();
    }

 	void displayResult(TupleQueryResult tqr) throws QueryEvaluationException {
		List<String> bindingNames = tqr.getBindingNames();
		while (tqr.hasNext()) {
			BindingSet bs = tqr.next();
			for (String name : bindingNames) {
				System.out.println("name:" + name + " value:" + bs.getValue(name));
			}
		}
	}

	void addData(File dataFile) 
 	throws OpenRDFException, IOException {
		RepositoryConnection con = myRepository.getConnection();
		try {
			con.add(dataFile,"http://exmaple.org/example/local",RDFFormat.RDFXML);
		}
		finally {
			con.close();
		}	
	}

	public void go() {
		try {
            RepositoryConnection con = myRepository.getConnection();
			//addData(new File("src/main/resources/go_daily-termdb.rdf-xml"));
			addData(new File("src/main/resources/example.xml"));

			System.out.println("\n------- Serql -------------");
			TupleQueryResult tqrSerql = runSerqlQuery(con);
			displayResult(tqrSerql);

			System.out.println("\n--------- Sparql ----------");
			TupleQueryResult tqrSparql = runSparqlQuery(con);
			displayResult(tqrSparql);

			System.out.println("\n----------------------");
		}
		catch (OpenRDFException e) {
			System.err.println("" + e);
		   e.printStackTrace();
		}
		catch (IOException e) {
			System.err.println("" + e);
		   e.printStackTrace();
		}
	}
	
}
