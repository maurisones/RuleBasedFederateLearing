package coordinator;

import java.util.HashMap;
import java.util.Map;

public class ModelRuleMatchCount extends Model {	
	
	@Override
	public boolean buildModel() {
		// nothing to do - this model is only an append of rules
		return true;
	}

	@Override
	public String classifyInstance(String[] instance) {
		
		Map<String, Float> votos = new HashMap<>();		
		for (int r = 0; r < this.rules.size(); r++) {
			String[] rule = this.rules.get(r);
			boolean match = true;
			for (int i = 0; i < rule.length -1; i++) {
				if (rule[i].compareTo(instance[i]) != 0 && 
						rule[i].compareTo("*") != 0 &&
						instance[i].compareTo("?") != 0) {
					match = false;
					break;
				}
			}
			
			float ruleWeight = this.getRuleWeight(r);
			
			if (match) {
				String votedClass = rule[rule.length -1];
				if (votos.containsKey(votedClass)) {
					votos.put(votedClass, votos.get(votedClass) + ruleWeight);
				}else {
					votos.put(votedClass, ruleWeight);
				}
			}
		}
		
		System.out.println(votos.toString());
		
		// encontrar a classe com mais votos no mapa
		float maisVotado = -1;
		String classeMaisVotada = null;
		for (String key: votos.keySet()) {
			if (votos.get(key) > maisVotado) {
				maisVotado = votos.get(key);
				classeMaisVotada = key;
			}			
		}
		
		if (classeMaisVotada == null) {
			classeMaisVotada = this.majority;
			this.nMajorityAttribution++;
		}
		
		return classeMaisVotada;
	}

	public float getRuleWeight(int ruleIndex) {		
		return 1;
	}


	
}
