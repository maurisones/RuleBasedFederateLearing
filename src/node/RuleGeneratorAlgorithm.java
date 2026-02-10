package node;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import weka.core.AttributeStats;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public abstract class RuleGeneratorAlgorithm {

	protected String trainDataSetFileName;
	protected String outputPrefixFileName;
	protected int classIndex = -1; // -1 means the last column of the dataset
	protected String ruleFileExtension = "J48rules";
	protected String ruleMetricFileExtension = "J48rulesmetrics";
	protected String datasetMetricFileExtension = "J48datasetmetrics";
	protected Instances data;

	public RuleGeneratorAlgorithm(String trainDataSetFileName, String outputFileName) {
		this.trainDataSetFileName = trainDataSetFileName;
		this.outputPrefixFileName = outputFileName;
	}

	public int getClassIndex() {
		return classIndex;
	}

	public void setClassIndex(int classIndex) {
		this.classIndex = classIndex;
	}

	public String getRuleFileExtension() {
		return ruleFileExtension;
	}

	public void setRuleFileExtension(String ruleFileExtension) {
		this.ruleFileExtension = ruleFileExtension;
	}

	public String getRuleMetricFileExtension() {
		return ruleMetricFileExtension;
	}

	public void setRuleMetricFileExtension(String ruleMetricFileExtension) {
		this.ruleMetricFileExtension = ruleMetricFileExtension;
	}

	public String getDatasetMetricFileExtension() {
		return datasetMetricFileExtension;
	}

	public void setDatasetMetricFileExtension(String datasetMetricFileExtension) {
		this.datasetMetricFileExtension = datasetMetricFileExtension;
	}

	public String getDatasetFileName() {
		return trainDataSetFileName;
	}

	public void setDatasetFileName(String datasetFileName) {
		this.trainDataSetFileName = datasetFileName;
	}

	public String getOutputPrefixFileName() {
		return outputPrefixFileName;
	}

	public void setOutputPrefixFileName(String outputPrefixFileName) {
		this.outputPrefixFileName = outputPrefixFileName;
	}

	public boolean generateDatasetMetrics() {
		// datasetMetrics
		try {
			FileWriter fw = new FileWriter(new File(this.outputPrefixFileName + "." + this.datasetMetricFileExtension));
			AttributeStats classStats = data.attributeStats(data.classIndex());
			fw.append("nsamples:" + classStats.intCount + "\n");
			fw.append("nclasses:" + classStats.distinctCount + "\n");

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
			System.out.println();
			System.out.println(data.classAttribute().value(1));
			fw.append("classnames:" + data.classAttribute().value(0) + "," + data.classAttribute().value(1) + "\n");
			fw.append("classdist:" + values.get(data.classAttribute().value(0)) + ","
					+ values.get(data.classAttribute().value(1)) + "\n");
			fw.append("natt:" + data.numAttributes() + "\n");

			fw.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	public boolean loadArffDataset() {
		try {
			System.out.println("zxxx " + this.trainDataSetFileName);
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

	public abstract boolean generateRules();

}
