package com.croeder.open_nlp_hadoop.hadoop;

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
// TODO: put a real xml to text translation in there
// TODO: understand map pipelines well enough to know if the key needs to change
//    from .nxml to .txt or not

public class XmlToSentenceMapper extends Mapper<Text, Text, Text, Text> {
	SentenceDetector sentenceDetector;
	
	@Override
	public void map(Text key, Text value, Context context)
	throws IOException, InterruptedException {
		String s = value.toString();
		String r = s.replace(">", " ").replace("<", " ") ;
		int i=0;
		for (String sentence : getSentences(r)) {
			context.write(new Text(key.toString() + ":" + i++),
					      new Text(sentence)  ); 
		}
	}
	public XmlToSentenceMapper() {
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
