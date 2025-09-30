package run;

import node.RuleGeneratorAlgorihtm;
import node.RuleGeneratorAlgorihtmJ48;

public class RunNode {

	public static void main(String[] args) {
		
		//args[0] = "/tmp/expflrules/breast-train-2-of-10-d-no30.arff";
		//args[1] = "/tmp/expflrules/breast-train-2-of-10-d-no30";
		
		RuleGeneratorAlgorihtm ruleAlgo = new RuleGeneratorAlgorihtmJ48(
    			args[0],
    			args[1],
    			false);    	
    	ruleAlgo.generateRules();

	}

}
