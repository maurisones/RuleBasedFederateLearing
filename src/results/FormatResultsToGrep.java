package results;

import weka.classifiers.Evaluation;

public class FormatResultsToGrep extends FormatResults{
	
	
	public FormatResultsToGrep(Evaluation eval, String datasetName, String expId) {
		super(eval, datasetName, expId);
	}

	public String evalResultsToGrep() {
		String ret =  "";
		for (String k: measuresMap.keySet()) {
			ret += this.expId + ": " + this.datasetName + ": " +  k + ": " + measuresMap.get(k) + "\n"; 
		}
		return ret;
	}
	
}
