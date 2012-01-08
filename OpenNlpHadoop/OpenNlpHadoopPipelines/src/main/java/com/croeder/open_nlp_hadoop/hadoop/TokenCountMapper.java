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

// fix path to resources

public class TokenCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	
	@Override
	public void map(LongWritable key, Text value, Context context)
	throws IOException, InterruptedException {
		//System.out.println("TokenCountMapper: key:\"" +key.toString() + "\"  value:\"" +  value.toString() + "\"");
		
		// TODO: find and fix the evil that is prepending the values with IDs!!!
		String v = value.toString().split("\t")[1];
			context.write(new Text(v), new IntWritable(1) );
	}
	

}
