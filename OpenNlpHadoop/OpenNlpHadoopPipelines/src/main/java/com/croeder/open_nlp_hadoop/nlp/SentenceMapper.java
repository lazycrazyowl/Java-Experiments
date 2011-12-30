package com.croeder.open_nlp_hadoop.nlp;

import opennlp.tools.util.StringList;
import opennlp.tools.dictionary.Dictionary;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;

import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;

// TODO: fix path to resources

public class SentenceMapper extends Mapper<Text, Text, Text, Text> {
	SentenceDetector sentenceDetector;
	
	@Override
	public void map(Text key, Text value, Context context)
	throws IOException, InterruptedException {
		String fileContents = value.toString();
		int i=0;
		for (String s : getSentences(fileContents)) {
			context.write(new Text(key.toString() + ":" + i++ ), 
					new Text(s) );
		}

	}

	public SentenceMapper() {
		InputStream modelIn=null;
		try {
			modelIn = new FileInputStream("src/main/resources/en-sent.bin");
			SentenceModel model = new SentenceModel(modelIn);
			sentenceDetector = new SentenceDetectorME(model);
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


	public String[] getSentences(String s) {
		return sentenceDetector.sentDetect(s);
	}
}
