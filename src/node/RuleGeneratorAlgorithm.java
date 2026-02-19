package node;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import weka.core.AttributeStats;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public abstract class RuleGeneratorAlgorithm {

	protected String trainDataSetFileName;	
	protected int classIndex = -1; // -1 means the last column of the dataset	
	protected Instances data;
	protected List<String[]> ruleList;
	protected List<Float[]> ruleMetrics;
	protected List<String> datasetMetrics;

	public RuleGeneratorAlgorithm(String trainDataSetFileName) {
		this.trainDataSetFileName = trainDataSetFileName;
	}

	public int getClassIndex() {
		return classIndex;
	}

	public void setClassIndex(int classIndex) {
		this.classIndex = classIndex;
	}

	public String getDatasetFileName() {
		return trainDataSetFileName;
	}

	public void setDatasetFileName(String datasetFileName) {
		this.trainDataSetFileName = datasetFileName;
	}

	public void generateDatasetMetrics() {
		datasetMetrics = new LinkedList<String>();
		
		AttributeStats classStats = data.attributeStats(data.classIndex());
		datasetMetrics.add("nsamples:" + classStats.intCount);
		datasetMetrics.add("nclasses:" + classStats.distinctCount);

		// class distribution
		Map<String, Integer> values = new HashMap<>();
		for (int i = 0; i < classStats.intCount; i++) {
			String value = data.instance(i).stringValue(data.classIndex());
			if (!values.containsKey(value)) {
				values.put(value, 1);
			} else {
				values.put(value, values.get(value) + 1);
			}
		}
		
		datasetMetrics.add("classnames:" + data.classAttribute().value(0) + "," + data.classAttribute().value(1));
		datasetMetrics.add("classdist:" + values.get(data.classAttribute().value(0)) + ","
					+ values.get(data.classAttribute().value(1)));
		datasetMetrics.add("natt:" + data.numAttributes());

	}

	public boolean loadArffDataset() {
		try {			
			// Load dataset
			ArffLoader loader = new ArffLoader();
			loader.setSource(new File(this.trainDataSetFileName));
			data = loader.getDataSet();

			if (this.classIndex != -1) {
				data.setClassIndex(this.classIndex);
			} else {
				// Set the last attribute as the class
				data.setClassIndex(data.numAttributes() - 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false; 
		}
		return true;
	}

	public List<String> getRules(){
		
		List<String> ruleStrs = new LinkedList<String>();
		
		 for (String rr[]: ruleList) {
         	ruleStrs.add(Arrays.toString(rr).replaceAll("\\[", "").replaceAll("\\]", "").replace(" ", ""));
         }
		 
		 return ruleStrs;
	}
	
	public List<String> getRulesWithMetrics(){
		
		List<String> ruleStrs = this.getRules();
		for (int i = 0; i < ruleStrs.size(); i++) {
			String rule = ruleStrs.get(i);
			rule = rule + "," + Arrays.toString(ruleMetrics.get(i)).replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", "");
			ruleStrs.set(i, rule);
		}
		
		return ruleStrs;
	}
	
	public List<String> getDatasetMetrics(){		
		return datasetMetrics;
	}
	
	public void writeRulesAndMetricsToFile(String ruleFileName, String ruleMetricFileName,
			String datasetMetricFileName) {
	
		// write a file rules
        FileWriter fw;
		try {
			fw = new FileWriter(new File(ruleFileName));
	
	        for (String rr[]: ruleList) {
	        	fw.append(Arrays.toString(rr).replaceAll("\\[", "").replaceAll("\\]", "").replace(" ", ""));
	        	fw.append("\n");
	        }        
	        fw.close();
	        
	        // write a file metrics
	        fw = new FileWriter(new File(ruleMetricFileName));
	        for (Float rr[]: ruleMetrics) {
	        	fw.append(Arrays.toString(rr).replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", ""));
	        	fw.append("\n");
	        }            
	        fw.close();
	        
	        // write a dataset metrics
	        fw = new FileWriter(new File(datasetMetricFileName));
	        for (String s: datasetMetrics) {
	        	fw.append(s + "\n");	        	
	        }            
	        fw.close();
	        
		} catch (IOException e) {
			System.out.println("Error writing files.");
			e.printStackTrace();
		}
	}
	
	public void showRulesWithMetrics() {
		System.out.println("************* Rules with metrics *************");
		for (String s: getRulesWithMetrics()) {
			System.out.println(s);
		}        
	}
	
	public abstract boolean generateRules();

}
