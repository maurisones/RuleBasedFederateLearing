package coordinator;

public class ModelRuleMatchWeighted extends ModelRuleMatchCount {

	@Override
	public float getRuleWeight(int ruleIndex) {
		
		return Float.valueOf(this.rulesMetrics.get(ruleIndex)[0]).floatValue();
		
	}
	
	

}
