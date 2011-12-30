package com.croeder.open_nlp_hadoop.nlp;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import org.apache.log4j.Logger;

/*
 * adapted from this guy's work:
 * http://cotdp.com/blog/2011/03/reading-zip-files-from-hadoop-mapreduce.html
 */
public class WholeFileRecordReader extends
		RecordReader<Text, Text> {
	
	static Logger logger = Logger.getLogger(WholeFileRecordReader.class);
	
	private boolean processed=false;
	private Configuration conf;
	private TaskAttemptContext context;
	private FileSplit split;
	//private BytesWritable value= new BytesWritable();
	private Text value = new Text();
	private Text key;

	@Override
	public void close() 
	throws IOException {
		// null
	}

	@Override
	public Text getCurrentKey() 
	throws IOException, InterruptedException {
		return key;
	}

	@Override
	public Text getCurrentValue() 
	throws IOException, InterruptedException {
		return value;
	}

	@Override
	public float getProgress() 
	throws IOException, InterruptedException {
		return processed ? 1.0f : 0.0f;
	}

	@Override
	public void initialize(InputSplit split, TaskAttemptContext context)
	throws IOException, InterruptedException {
		this.split = (FileSplit) split;
		this.context = context;
		conf = context.getConfiguration();
	}

	@Override
	public boolean nextKeyValue() 
	throws IOException, InterruptedException {
		if (!processed) {
			byte[] contents = new byte[(int) split.getLength()];
			FSDataInputStream in = null;
			Path file = null;
			try {
				file = split.getPath();
				key = new Text(file.getName());
				FileSystem fs = file.getFileSystem(conf);
				in = fs.open(file);
				IOUtils.readFully(in, contents, 0, contents.length);
				value.set(contents, 0, contents.length);
			}
			catch (Exception wtf) {
				logger.error("exception in edu.ucdenver.ccp.bio_trends.hadoop.WholeFileRecordReader" + wtf);
				throw new RuntimeException(wtf);
			}
			finally {
				//logger.error("error with nextKeyValue, file: " + file.getName());
				IOUtils.closeStream(in);
			}
			processed = true;
			return true;
		}
		return false;
	}

}
