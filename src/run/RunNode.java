package run;

import java.util.List;

import node.RuleGeneratorAlgorithm;
import node.RuleGeneratorAlgorithmJ48;
import node.RuleGeneratorDecisionTable;
import node.RuleGeneratorPART;

public class RunNode {

	public static void main(String[] args) {
		
		//args[0] = "/home/mauri/Downloads/expflrules/breast-train-2-of-10-d-no30.arff";
		//args[1] = "/home/mauri/Downloads/expflrules/breast-train-2-of-10-d-no30";

		RuleGeneratorAlgorithm ruleAlgo = new RuleGeneratorAlgorithmJ48(args[0], true);		
    	ruleAlgo.generateRules();
    	ruleAlgo.writeRulesAndMetricsToFile(args[1] + ".J48rules", args[1] + ".J48rulesmetrics", args[1] + ".J48datasetmetrics");
    	
		
		RuleGeneratorAlgorithm ruleAlgo2 = new RuleGeneratorDecisionTable(args[0]);    	
    	ruleAlgo2.generateRules();
    	ruleAlgo.writeRulesAndMetricsToFile(args[1] + ".DTrules", args[1] + ".DTrulesmetrics", args[1] + ".DTdatasetmetrics");
    	
		RuleGeneratorAlgorithm ruleAlgo3 = new RuleGeneratorPART(args[0], args[1]);    	
    	ruleAlgo3.generateRules();
    	ruleAlgo.writeRulesAndMetricsToFile(args[1] + ".PARTrules", args[1] + ".PARTrulesmetrics", args[1] + ".PARTdatasetmetrics");    	


    	
	}

}
