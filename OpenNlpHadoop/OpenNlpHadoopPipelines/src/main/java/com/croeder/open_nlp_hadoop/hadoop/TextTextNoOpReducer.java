package com.croeder.open_nlp_hadoop.hadoop;

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
public class TextTextNoOpReducer extends Reducer< Text, Text, Text, Text> {
	
	static Logger logger = Logger.getLogger(TextTextNoOpReducer.class);
	
	static {
		logger.setLevel(Level.DEBUG); 
	}
	
	//@Override
	public void reduce(Text tokenKey, Iterable<Text> lines, Context context) 
	throws IOException, InterruptedException {
		int sum=0;
		for (Text t : lines) {
			context.write(tokenKey, t);
		}
	}
}
