package com.croeder.open_nlp_hadoop.nlp;


import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class WholeFileInputFormat extends
		FileInputFormat<Text, Text> {

	@Override
	public RecordReader<Text, Text> createRecordReader(
			InputSplit split, TaskAttemptContext context) 
	throws IOException, InterruptedException {
		WholeFileRecordReader wrr = new WholeFileRecordReader();
		wrr.initialize(split, context);
		return wrr;
	}
	
	@Override
	protected boolean isSplitable(JobContext jc, Path path) {
		return false;
	}

}
