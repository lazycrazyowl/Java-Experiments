package com.croeder.open_nlp_hadoop.nlp;

import java.net.URL;
import java.net.URLClassLoader;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.KeyValueTextInputFormat;
import org.apache.hadoop.mapred.JobConf;

import com.croeder.open_nlp_hadoop.hadoop.WholeFileInputFormat;
import com.croeder.open_nlp_hadoop.hadoop.TokenCountReducer;
import com.croeder.open_nlp_hadoop.hadoop.TokenCountMapper;
import com.croeder.open_nlp_hadoop.hadoop.NameAssignerMapper;
import com.croeder.open_nlp_hadoop.hadoop.XmlToSentenceMapper;


public class CountUniqueIdsMain {
	
	public static void main(String args[]) 
	throws Exception {
		
		
        ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
		doCL(sysClassLoader, "sys");
		doCL(CountUniqueTokensMain.class.getClassLoader(), "ShowClasspath");
		
		Path pathA = new Path("tempA");
		Path pathB = new Path("tempB");
		Path pathC = new Path("tempC");
		Path pathD = new Path("tempD");
		
		System.out.println("================= XML to Text ===============");
		Job job = new Job();
		job.setInputFormatClass(WholeFileInputFormat.class);
		job.setJarByClass(XmlToSentenceMapper.class);
		//WholeFileInputFormat.addInputPath(job,  new Path("src/main/resources/BMC_Biochem_small"));
		WholeFileInputFormat.addInputPath(job,  new Path("src/main/resources/BMC_Biochem"));
		//WholeFileInputFormat.addInputPath(job,  new Path("/home/croeder/work/data"));
		FileOutputFormat.setOutputPath(job, pathA);
		job.setMapperClass(XmlToSentenceMapper.class);
		//FileSystem.delete(Path f, boolean recursive);
		//job.setReducerClass(ProteinDateReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.waitForCompletion(true);
		
		
		System.out.println("================= create tokens, assign names, Count names  ===============");
		Job job_3 = new Job();
		job_3.setJarByClass(TokenCountMapper.class);
		WholeFileInputFormat.addInputPath(job_3,  pathA);
		FileOutputFormat.setOutputPath(job_3, pathB);
		job_3.setMapperClass(NameAssignerMapper.class);
		job_3.setReducerClass(TokenCountReducer.class);
		job_3.setOutputKeyClass(Text.class);
		job_3.setOutputValueClass(IntWritable.class);
		job_3.waitForCompletion(true);
	}
	
	static Job createMapperJob(Path inPath, Path outPath, Class mapper, Class inKey, Class outKey) 
	throws IOException {
		Job job = new Job();
		job.setJarByClass(mapper);
		WholeFileInputFormat.addInputPath(job,  inPath);
		FileOutputFormat.setOutputPath(job, outPath);
		JobConf jobConf = new JobConf(false);
		job.setMapperClass(mapper);
		/////////.setReducerClass(TextTextNoOpReducer.class);
		///job.setInputFormatClass(TextInputFormat.class);
		job.setOutputKeyClass(inKey);
		job.setOutputValueClass(outKey);
		
		return job;
	}
	
	public static void doCL(ClassLoader classLoader, String name) {
        //Get the URLs
        URL[] urls = ((URLClassLoader)classLoader).getURLs();

		System.out.println("Showing classpath..."  + name);
        for(int i=0; i< urls.length; i++)
        {
            System.out.println(urls[i].getFile());
        } 
		System.out.println("...that's all folks.");
	
		System.out.println("CWD:" + System.getProperty("user.dir"));

	}
}
