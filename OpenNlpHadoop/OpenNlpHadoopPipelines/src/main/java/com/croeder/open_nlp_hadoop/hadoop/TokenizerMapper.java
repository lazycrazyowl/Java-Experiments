package com.croeder.open_nlp_hadoop.hadoop;

import opennlp.tools.util.StringList;
import opennlp.tools.dictionary.Dictionary;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;

// TODO: fix path to resources

public class TokenizerMapper extends Mapper<LongWritable, Text, LongWritable, Text> {

	Tokenizer tokenizer;
	
	public TokenizerMapper() {
		InputStream modelIn=null;
		try {
			//modelIn =  new FileInputStream("src/main/resources/en-token.bin");
			modelIn = this.getClass().getClassLoader().getResourceAsStream("en-token.bin");
			TokenizerModel model = new TokenizerModel(modelIn);
			tokenizer = new TokenizerME(model);
		}
		catch (IOException e) {
		  e.printStackTrace();
		}
		finally {
		  if (modelIn != null) {
		    try {
		      modelIn.close();
		    }
		    catch (IOException e) {
		    }
		  }
		}	
	}
	
	@Override
	public void map(LongWritable key, Text value, Context context)
	throws IOException, InterruptedException {
		String sentence = value.toString();
		int j=0;
		for (String t : tokenizer.tokenize(sentence)) {
			context.write(new LongWritable( (key.get() * 10000 + j++)), 
					new Text(t) );
		}

	}
	public String[] tokenize(String s) { return tokenizer.tokenize(s); }
}
