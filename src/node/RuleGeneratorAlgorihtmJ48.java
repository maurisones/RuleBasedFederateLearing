package node;

import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.AttributeStats;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class RuleGeneratorAlgorihtmJ48 extends RuleGeneratorAlgorihtm {

	private boolean unpruned = false;	
	
	public RuleGeneratorAlgorihtmJ48(String datasetFileName, String outputFileName, boolean unpruned) {
		super(datasetFileName, outputFileName);
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
            // Load dataset 
            ArffLoader loader = new ArffLoader(); 
            loader.setSource(new File(this.trainDataSetFileName));
            Instances data = loader.getDataSet(); 
            
            if (this.classIndex != -1) {
                data.setClassIndex(this.classIndex);
            }else {
                // Set the last attribute as the class            
                data.setClassIndex(data.numAttributes() - 1);
            }
 
            // Build the decision tree classifier 
            J48 tree = new J48(); 
            
            tree.setUnpruned(unpruned);
            
            tree.buildClassifier(data);   
            
                   
            // produce a list with attnames
            List<String> attNames = new LinkedList<String>();
            for (int i = 0; i < data.numAttributes(); i++) {
            	attNames.add(data.attribute(i).name());
            }
            
            System.out.println("Unpruned: " + tree.getUnpruned());
            System.out.println(attNames.toString());           
            System.out.println(tree.toString());  
            
            String treeV[] = tree.toString().replaceAll(" ", "").split("\\n");
            System.out.println(Arrays.toString(treeV));
            
            
            List<String[]> ruleList = new LinkedList<String[]>();
            List<Float[]> ruleMetrics = new LinkedList<Float[]>();
            
            processTree(treeV, 3, "", attNames, ruleList, ruleMetrics);
            //System.out.println(treeV[4]);
            
            //addRuleToList(", node-caps=yes, |deg-malig=1:recurrence-events(1.01/0.4)", ruleList, attNames);
            

            
            for (String rr[]: ruleList)
            	System.out.println(Arrays.toString(rr));
            
            for (Float rr[]: ruleMetrics)
            	System.out.println(Arrays.toString(rr));
            
            
            // write a file rules
            FileWriter fw = new FileWriter(new File(this.outputPrefixFileName + "." + this.ruleFileExtension));
            for (String rr[]: ruleList) {
            	fw.append(Arrays.toString(rr).replaceAll("\\[", "").replaceAll("\\]", "").replace(" ", ""));
            	fw.append("\n");
            }
            
            fw.close();
            
            // write a file metrics
            fw = new FileWriter(new File(this.outputPrefixFileName + "." + this.ruleMetricFileExtension));
            for (Float rr[]: ruleMetrics) {
            	fw.append(Arrays.toString(rr).replaceAll(" ", "").replaceAll("\\[", "").replaceAll("\\]", ""));
            	fw.append("\n");
            }            
            fw.close();
            
            // datasetMetrics
            fw = new FileWriter(new File(this.outputPrefixFileName + "." + this.datasetMetricFileExtension));            
            AttributeStats classStats = data.attributeStats(data.classIndex());
            fw.append("nsamples:" + classStats.intCount + "\n");
            fw.append("nclasses:" + classStats.distinctCount + "\n");
            
            
            // class distribution
            Map<String, Integer> values = new HashMap<>();
            for (int i = 0; i < classStats.intCount; i++) {
            	String value = data.instance(i).stringValue(data.classIndex());
            	if (!values.containsKey(value)) {
            		values.put(value, 1);
            	}else {
            		values.put(value, values.get(value) + 1);
            	}
            }
            System.out.println();
            System.out.println(data.classAttribute().value(1));
            fw.append("classnames:" + data.classAttribute().value(0) + "," + data.classAttribute().value(1) + "\n");
            fw.append("classdist:" + values.get(data.classAttribute().value(0)) + "," + values.get(data.classAttribute().value(1)) + "\n");
            fw.append("natt:" + data.numAttributes() + "\n");
            
            
            fw.close();
            
            
            
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
				System.out.println(rule + ", " + treeV[i]);
				
				// adicionar a rule na lista
				addRuleToList(rule + ", " + treeV[i], ruleList, ruleMetrics, attNames);
				
				
			} else {			
				rule = rule + ", " + treeV[i];
			}			
			
			processTree(treeV, i + 1, rule, attNames, ruleList, ruleMetrics);			
		}
		
	}



	private static void addRuleToList(String rule, List<String[]> ruleList, List<Float[]> ruleMetrics, List<String> attNames) {
		String[] ruleVector = new String[attNames.size()];
		Arrays.fill(ruleVector, "*");
		
		String rulem = rule.substring(rule.indexOf("(") + 1, rule.indexOf(")"));
		System.out.println("ruleMetric: " + rulem);

		
		rule = rule.replaceAll("\\|","").replaceAll(":", ",").replace(" ", "");		
		rule = rule.substring(0, rule.indexOf("("));
				
		for (String rp: rule.split(",")) {
			System.out.println(rp);
			if (rp.contains("=")) {
				String rpatt= rp.split("=")[0];
				String rpvalue = rp.split("=")[1];				
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
