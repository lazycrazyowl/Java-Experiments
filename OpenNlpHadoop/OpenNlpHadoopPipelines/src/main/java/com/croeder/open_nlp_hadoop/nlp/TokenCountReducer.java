package com.croeder.open_nlp_hadoop.nlp;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;


/*
 * IN: key: protein:date,  value: count
 * OUT: key: protein:date, value: count 
 */
public class TokenCountReducer extends Reducer< Text,IntWritable, Text, IntWritable> {
	
	static Logger logger = Logger.getLogger(TokenCountReducer.class);
	
	static {
		logger.setLevel(Level.DEBUG); 
	}
	
	//@Override
	public void reduce(Text tokenKey, Iterable<IntWritable> counts, Context context) 
	throws IOException, InterruptedException {
		int sum=0;
		for (IntWritable i : counts) {
			sum += i.get();
		}
		context.write(tokenKey, new IntWritable(sum));
	}
}
