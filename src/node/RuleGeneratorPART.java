package node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import weka.classifiers.rules.PART;

public class RuleGeneratorPART extends RuleGeneratorAlgorithm {

	public RuleGeneratorPART(String trainDataSetFileName, String outputFileName) {
		super(trainDataSetFileName);
	}

	@Override
	public boolean generateRules() {
		try { 
	           
			this.loadArffDataset();
 
            // Build the DecisionTable classifier 
            PART algo = new PART();                        
            algo.buildClassifier(data);
            
            
            System.out.println("************* Weka algo output *************");
            System.out.println(algo.toString());
              
            // produce a list with attnames
            List<String> attNames = new LinkedList<String>();
            for (int i = 0; i < data.numAttributes(); i++) {
            	attNames.add(data.attribute(i).name());
            }
            
            
            String algoStr = algo.toString().split("---\\n")[1];           
            System.out.println(algoStr);
            
            ruleList = new ArrayList<String[]>();
            ruleMetrics = new ArrayList<Float[]>();
                        
            
            processRules(algoStr, attNames, ruleList, ruleMetrics);
                    
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
	
	private static void processRules(String dtstr, List<String> attNames, 
			List<String[]> ruleList, List<Float[]> ruleMetrics
			) {
		
			String lines[] = dtstr.split("\\n\\n");
			System.out.println("*** " + Arrays.toString(lines));
			
			for (String line: lines) {
				System.out.println("line: " + line);
				if (line.trim().startsWith(":")) {
					break;
				}
				
				System.out.println("=====");
				
				// cria uma regra com *		
				String[] ruleVector = new String[attNames.size()];
				Arrays.fill(ruleVector, "*");
				
				String antecedente = line.replace("\n", " ").split(":")[0];				
				System.out.println("Antecedente: " + antecedente);
				String[] attValuePairs = antecedente.split("AND");
				for (String pair : attValuePairs) {
					String attname = pair.split("=")[0].trim();
					String attvalue = pair.split("=")[1].trim();
					
					ruleVector[attNames.indexOf(attname)] = attvalue;
				
				}
				
				
				String consequente = line.split(":")[1];
				System.out.println("Consequente: " + consequente);
				
				String classname = consequente.split("\\(")[0].trim();
				String rulemet = consequente.split("\\(")[1].replace(")", "");
				
				System.out.println("classname: " + classname);
				System.out.println("rulemet: " + rulemet);

				ruleVector[ruleVector.length -1] = classname;
				
				System.out.println(Arrays.toString(ruleVector));
				
				ruleList.add(ruleVector);

				
				Float metrics[] = {0f,0f};
				String rulemv[] = rulemet.split("\\/");
				metrics[0] = Float.valueOf(rulemv[0]);
				if (rulemv.length > 1) {
					metrics[1] = Float.valueOf(rulemv[1]);
				}
				ruleMetrics.add(metrics);
				
			}
		
	}

}
