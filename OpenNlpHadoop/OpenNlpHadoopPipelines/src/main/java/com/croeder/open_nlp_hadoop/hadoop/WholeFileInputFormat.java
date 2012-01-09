package com.croeder.open_nlp_hadoop.hadoop;


import java.io.IOException;
import java.io.File;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.Job;



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

	public static void addInputPathsFromFile(Job job, File baseDir) 
	throws IOException {
		String[] children = baseDir.list();	
		for (String s : children) {
			File subDir = new File(baseDir, s);
			Path path = new Path(subDir.getAbsolutePath());	
			addInputPath(job, path);
		}
	}

}
