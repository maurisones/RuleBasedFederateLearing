package run;

import node.RuleGeneratorAlgorihtm;
import node.RuleGeneratorAlgorihtmJ48;

public class RunNode {

	public static void main(String[] args) {
		RuleGeneratorAlgorihtm ruleAlgo = new RuleGeneratorAlgorihtmJ48(
    			args[0],
    			args[1],
    			false);    	
    	ruleAlgo.generateRules();

	}

}
