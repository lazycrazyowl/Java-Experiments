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

import com.croeder.open_nlp_hadoop.nlp.DictionaryAssigner;
import com.croeder.open_nlp_hadoop.opennlp_add.DictionaryNameAssigner;
import com.croeder.open_nlp_hadoop.opennlp_add.DictionaryNameAssigner.Annotation;

// TODO: fix path to resources

public class NameAssignerMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	Tokenizer tokenizer;
	DictionaryAssigner da;
	
	public NameAssignerMapper() {
		try {
		da = new DictionaryAssigner();
		InputStream modelIn=null;
		try {
			modelIn =  new FileInputStream("src/main/resources/en-token.bin");
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
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void map(LongWritable key, Text value, Context context)
	throws IOException, InterruptedException {
		String sentence = value.toString();
		int j=0;
		String[] tokens =  tokenizer.tokenize(sentence);
		Annotation[] annos = da.find(tokens);
		for (Annotation a : annos) {	
			context.write( new Text(a.getId()), new IntWritable(1));
		}

	}
}	
