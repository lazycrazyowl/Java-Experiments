package com.croeder.open_nlp_hadoop.nlp;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;


public class POS {

	POSTaggerME poser;	

	public POS() 
	throws IOException {
	    InputStream modelIn=null;
        try {
    	    //modelIn =  new FileInputStream("src/main/resources/en-token.bin");
            modelIn =  this.getClass().getClassLoader().getResourceAsStream("en-token.bin");
            POSModel model = new POSModel(modelIn);
            poser = new POSTaggerME(model);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
		
	}

	// not much more than a mental note	
	public String[] tag(String[] tokens) {
		return poser.tag(tokens);
	}	
}
