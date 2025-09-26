package node;

public abstract class RuleGeneratorAlgorihtm {
	
	protected String trainDataSetFileName;
	protected String outputPrefixFileName;
	protected int classIndex = -1;	// -1 means the last column of the dataset	
	protected String ruleFileExtension = "J48rules";
	protected String ruleMetricFileExtension = "J48rulesmetrics";
	protected String datasetMetricFileExtension = "J48datasetmetrics";
	
	public RuleGeneratorAlgorihtm(String trainDataSetFileName, String outputFileName) {
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

	public abstract boolean generateRules();
	
	
}
