package node;

public class RuleGenerator {
	
	private RuleGeneratorAlgorithm ruleGeneratorAlgorithm;
	
	public RuleGenerator(RuleGeneratorAlgorithm ruleGeneratorAlgorithm) {
		this.ruleGeneratorAlgorithm = ruleGeneratorAlgorithm;
	}

	public void runRuleGenerator() {
		ruleGeneratorAlgorithm.generateRules();
	}
}
