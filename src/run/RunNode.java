package run;

import node.RuleGeneratorAlgorithm;
import node.RuleGeneratorAlgorithmJ48;
import node.RuleGeneratorDecisionTable;
import node.RuleGeneratorPART;

public class RunNode {

	public static void main(String[] args) {
		
		//args[0] = "/home/mauri/Downloads/expflrules/breast-train-2-of-10-d-no30.arff";
		//args[1] = "/home/mauri/Downloads/expflrules/breast-train-2-of-10-d-no30";

		RuleGeneratorAlgorithm ruleAlgo = new RuleGeneratorAlgorithmJ48(
    			args[0],
    			args[1], true);  			
    	ruleAlgo.generateRules();

		
		RuleGeneratorAlgorithm ruleAlgo2 = new RuleGeneratorDecisionTable(
    			args[0],
    			args[1]);    	
		
		// tem que alterar a extensão dos arquivos de saída pois o padrão é para o J48
		ruleAlgo2.setDatasetMetricFileExtension("DTdatasetmetrics");
		ruleAlgo2.setRuleFileExtension("DTrules");
		ruleAlgo2.setRuleMetricFileExtension("DTrulesmetrics");
    	ruleAlgo2.generateRules();
    	
    	
		RuleGeneratorAlgorithm ruleAlgo3 = new RuleGeneratorPART(
    			args[0],
    			args[1]);    	
		ruleAlgo3.setDatasetMetricFileExtension("PARTdatasetmetrics");
		ruleAlgo3.setRuleFileExtension("PARTrules");
		ruleAlgo3.setRuleMetricFileExtension("PARTrulesmetrics");
    	ruleAlgo3.generateRules();

	}

}
