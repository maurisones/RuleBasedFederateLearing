package results;

import java.util.HashMap;
import java.util.Map;

import weka.classifiers.Evaluation;

public class FormatResults {
	protected Evaluation eval;
	protected String datasetName;
	protected String expId;
	protected Map<String, Double> measuresMap;

	public FormatResults(Evaluation eval, String datasetName, String expId) {	
		this.eval = eval;	
		this.datasetName = datasetName;
		this.expId = expId;
		this.measuresMap = new HashMap<String, Double>();
		
		this.evalToList();
	}

	private void evalToList() {
		measuresMap.put("Accuracy", eval.pctCorrect());		
		measuresMap.put("TP0", eval.numTruePositives(0));
		measuresMap.put("FP0", eval.numFalsePositives(0));
		measuresMap.put("TN0", eval.numTrueNegatives(0));
		measuresMap.put("FN0", eval.numFalseNegatives(0));
		measuresMap.put("TP1", eval.numTruePositives(1));
		measuresMap.put("FP1", eval.numFalsePositives(1));
		measuresMap.put("TN1", eval.numTrueNegatives(1));
		measuresMap.put("FN1", eval.numFalseNegatives(1));		
		
		measuresMap.put("TPRate0", eval.truePositiveRate(0));
		measuresMap.put("TPRate1", eval.truePositiveRate(1));
		measuresMap.put("TPRateWeightedAvg", eval.weightedTruePositiveRate());
		
		measuresMap.put("FPRate0", eval.falsePositiveRate(0));
		measuresMap.put("FPRate1", eval.falsePositiveRate(1));
		measuresMap.put("FPRateWeightedAvg", eval.weightedFalsePositiveRate());

		measuresMap.put("Precision0", eval.precision(0));
		measuresMap.put("Precision1", eval.precision(1));
		measuresMap.put("PrecisionWeightedAvg", eval.weightedPrecision());
		
		measuresMap.put("Recall0", eval.recall(0));
		measuresMap.put("Recall1", eval.recall(1));
		measuresMap.put("RecallWeightedAvg", eval.weightedRecall());
		
		measuresMap.put("FMeasure0", eval.fMeasure(0));
		measuresMap.put("FMeasure1", eval.fMeasure(1));
		measuresMap.put("FMeasureWeightedAvg", eval.weightedFMeasure());

	}
	
	
	
}
