package coordinator;

public class ModelRuleMatchWeighted extends ModelRuleMatchCount {

	public ModelRuleMatchWeighted(String testDataSetFileName) {
		super(testDataSetFileName);
	}

	@Override
	public float getRuleWeight(int ruleIndex) {
		
		return Float.valueOf(this.rulesMetrics.get(ruleIndex)[0]).floatValue();
		
	}
	
	

}
