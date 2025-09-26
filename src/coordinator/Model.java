package coordinator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import weka.core.Instances;

public abstract class Model {

	protected List<String> ruleFiles = new LinkedList<>();
	protected List<String> ruleMetricFiles = new LinkedList<>();
	protected List<String> datasetMetricFiles = new LinkedList<>();

	// dataset metrics
	protected int[] nsamples;
	protected int nclasses;
	protected String[] classnames;
	protected int[][] classdist;
	protected int natt;
	
	// rules
	List<String[]> rules = new ArrayList<>();
	List<String[]> rulesMetrics = new ArrayList<>();
	
	protected String testDataSetFileName;
	
	
	// results
	protected List<String> predclasses = new ArrayList<String>();
	protected List<String> trueclasses = new ArrayList<String>();
	protected int nMajorityAttribution = 0;
	protected String majority = null;
	
	
	public Model(String testDataSetFileName) {	
		this.testDataSetFileName = testDataSetFileName;
	}
	
	
	
	
	public String getTestDataSetFileName() {
		return testDataSetFileName;
	}




	public void setTestDataSetFileName(String testDataSetFileName) {
		this.testDataSetFileName = testDataSetFileName;
	}




	public String[] getClassnames() {
		return classnames;
	}


	public void setClassnames(String[] classnames) {
		this.classnames = classnames;
	}

	public List<String> getPredclasses() {
		return predclasses;
	}

	public void setPredclasses(List<String> predclasses) {
		this.predclasses = predclasses;
	}

	public List<String> getTrueclasses() {
		return trueclasses;
	}




	public void setTrueclasses(List<String> trueclasses) {
		this.trueclasses = trueclasses;
	}




	public boolean loadRules() {
		for (int i = 0; i < ruleFiles.size(); i++) {
		
			try {
				FileReader fr = new FileReader(ruleFiles.get(i));
				BufferedReader br = new BufferedReader(fr);
	
				String line;
				while ((line = br.readLine()) != null) {
					rules.add(line.split(","));				
				}
				br.close();
				fr.close();			
	
			} catch (IOException e) {
				e.printStackTrace();			
			}
		}
		
		return true;
	}
	
	
	public boolean loadDatasetMetrics() {

		// same to all datasets
		nclasses = Integer.valueOf(getStringFromTxt(datasetMetricFiles.get(0), "nclasses"));
		classnames = getStringFromTxt(datasetMetricFiles.get(0), "classnames").replaceAll(" ", "").split(",");
		natt = Integer.valueOf(getStringFromTxt(datasetMetricFiles.get(0), "natt"));
		
		nsamples = new int[datasetMetricFiles.size()];		
		classdist = new int[datasetMetricFiles.size()][nclasses];
		
		for (int i = 0; i < datasetMetricFiles.size(); i++) {
			nsamples[i] = Integer.valueOf(getStringFromTxt(datasetMetricFiles.get(i), "nsamples"));
			
			String[] strtmp = getStringFromTxt(datasetMetricFiles.get(i), "classdist").split(",");			
			for (int n = 0; n < strtmp.length; n++) {
				classdist[i][n] = Integer.valueOf(strtmp[n].trim()); 
			}		
		}
		
		// set the majority class
		int maj = -1, summaj = -1;		
		for (int i = 0; i < nclasses; i++) {
			int sum = 0;
			for (int j = 0; j < classdist.length; j++) {
				sum += classdist[j][i];
			}
			if (sum > summaj) {
				summaj = sum;
				maj = i;
			}			
		}
		this.majority = classnames[maj];		
		
		return false;
	}

	private String getStringFromTxt(String fileName, String prefix) {

		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);

			String line;
			while ((line = br.readLine()) != null) {
				if (line.startsWith(prefix)) {
					br.close();
					fr.close();
					return line.split(":")[1];
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}


	public abstract boolean buildModel();
	
	public abstract String classifyInstance(String[] instance);	
	
	public abstract List<String> classify();

	public void addDatasetMetricFile(String fileName) {		
		this.datasetMetricFiles.add(fileName);
	}
	
	public void addRuleFile(String fileName) {		
		this.ruleFiles.add(fileName);
	}
	
	public void addRuleMetricFiles(String fileName) {		
		this.ruleMetricFiles.add(fileName);
	}

	@Override
	public String toString() {
		return "Model [ruleFiles=" + ruleFiles + ", ruleMetricsFiles=" + ruleMetricFiles + ", datasetMetricFiles="
				+ datasetMetricFiles + ", nsamples=" + Arrays.toString(nsamples) + ", nclasses="
				+ nclasses + ", classnames=" + Arrays.toString(classnames) + ", classdist="
				+ Arrays.deepToString(classdist) + ", natt=" + natt + ", " + rulesToString(this.rules) + "\n" +  rulesToString(this.rulesMetrics) + "\n" +  "maj=" + majority +  "]";
	}
	
	public String rulesToString(List<String[]> lista) {
		String ret = "[";
		for(String[] ss: lista) {
			ret = ret + "\n" + Arrays.toString(ss);
		}
		ret = ret + "\n" + "]";
		
		return ret;
	}


	public boolean loadRuleMetrics() {
			
		for (int i = 0; i < ruleMetricFiles.size(); i++) {
			
			try {
				FileReader fr = new FileReader(ruleMetricFiles.get(i));
				BufferedReader br = new BufferedReader(fr);
	
				String line;
				while ((line = br.readLine()) != null) {
					rulesMetrics.add(line.split(","));				
				}
				br.close();
				fr.close();			
	
			} catch (IOException e) {
				e.printStackTrace();			
			}
		}
		
		return true;		
	};
	
	
}
