package node;

import weka.classifiers.trees.J48;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;



public class RuleGeneratorAlgorithmJ48 extends RuleGeneratorAlgorithm {

	private boolean unpruned = false;	
	
	public RuleGeneratorAlgorithmJ48(String datasetFileName, boolean unpruned) {
		super(datasetFileName);
		this.unpruned = unpruned;
	}

	
	
	public boolean isUnpruned() {
		return unpruned;
	}



	public void setUnpruned(boolean unpruned) {
		this.unpruned = unpruned;
	}



	@Override
	public boolean generateRules() {		
		try { 
           
			this.loadArffDataset();
 
            // Build the decision tree classifier 
            J48 algo = new J48(); 
            
            algo.setUnpruned(unpruned);
            
            algo.buildClassifier(data);   
            
                   
            // produce a list with attnames
            List<String> attNames = new LinkedList<String>();
            for (int i = 0; i < data.numAttributes(); i++) {
            	attNames.add(data.attribute(i).name());
            }
            
            System.out.println("Unpruned: " + algo.getUnpruned());
            
            System.out.println("************* Weka algo output *************");
            System.out.println(algo.toString());  
            
            String treeV[] = algo.toString().replaceAll(" ", "").split("\\n");
                        
            ruleList = new ArrayList<String[]>();
            ruleMetrics = new ArrayList<Float[]>();
                        
            processTree(treeV, 3, "", attNames, ruleList, ruleMetrics);            
            
            showRulesWithMetrics();
            
            generateDatasetMetrics();

            System.out.println("************* Dataset metrics *************");
            System.out.println(this.datasetMetrics.toString());
            
        }catch(Exception e) {
        	e.printStackTrace();
        	return false;
        }		
		
		return true;		
	}
	
	private static void processTree(String[] treeV, int i, String rule, List<String> attNames, 
			List<String[]> ruleList, List<Float[]> ruleMetrics) {
		if (i < treeV.length && rulePart(treeV[i], attNames)) {

			if (!treeV[i].startsWith("|")) {
				rule = "";
			}
			
			if (treeV[i].contains(":")) {				
				//System.out.println(rule + ", " + treeV[i]);
				
				// adiciona a rule na lista
				addRuleToList(rule + ", " + treeV[i], ruleList, ruleMetrics, attNames);
				
				
			} else {			
				rule = rule + ", " + treeV[i];
			}			
			
			processTree(treeV, i + 1, rule, attNames, ruleList, ruleMetrics);			
		}
		
	}



	private static void addRuleToList(String rule, List<String[]> ruleList, List<Float[]> ruleMetrics, List<String> attNames) {
	
		//System.out.println("rule: " + rule);
		
		String[] ruleVector = new String[attNames.size()];
		Arrays.fill(ruleVector, "*");
		
		String rulem = rule.split(":")[1];
		rulem = rulem.substring(rulem.indexOf("(") + 1, rulem.indexOf(")"));
		//System.out.println("ruleMetric: " + rulem);

		
		rule = rule.replaceAll("\\|","").replaceAll(":", ",").replace(" ", "");		
		rule = rule.substring(0, rule.indexOf("("));
				
		for (String rp: rule.split(",")) {
			//System.out.println("rp: " + rp);
			if (rp.contains("=")) {
				String rpatt= rp.split("=")[0];
				String rpvalue = rp.split("=")[1];				
				
				//System.out.println("rpatt: x" + rpatt);
				//System.out.println("rpvalue: x" + rpvalue);
				
				ruleVector[attNames.indexOf(rpatt)] = rpvalue;				
			} else {
				ruleVector[attNames.size() - 1] = rp;
			}
		}
		
		ruleList.add(ruleVector);
		
		
		Float metrics[] = {0f,0f};
		String rulemv[] = rulem.split("\\/");
		metrics[0] = Float.valueOf(rulemv[0]);
		if (rulemv.length > 1) {
			metrics[1] = Float.valueOf(rulemv[1]);
		}
		ruleMetrics.add(metrics);
		
	}

	private static boolean rulePart(String s, List<String> attNames) {
		if (s.startsWith("|")) {
			return true;
		}
		
		for (String att: attNames) {
			if (s.startsWith(att)) {
				return true;
			}
		}		
		
		return false;
	} 

}
