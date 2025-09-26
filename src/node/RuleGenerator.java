package node;

public class RuleGenerator {
	
	private RuleGeneratorAlgorihtm ruleGeneratorAlgorithm;
	
	public RuleGenerator(RuleGeneratorAlgorihtm ruleGeneratorAlgorithm) {
		this.ruleGeneratorAlgorithm = ruleGeneratorAlgorithm;
	}

	public void runRuleGenerator() {
		ruleGeneratorAlgorithm.generateRules();
	}
}
