package node;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import weka.classifiers.rules.DecisionTable;

public class RuleGeneratorDecisionTable extends RuleGeneratorAlgorithm {

	public RuleGeneratorDecisionTable(String trainDataSetFileName, String outputFileName) {
		super(trainDataSetFileName, outputFileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean generateRules() {
		try { 
	           
			this.loadArffDataset();
 
            // Build the DecisionTable classifier 
            DecisionTable algo = new DecisionTable();
            algo.setDisplayRules(true);
            algo.buildClassifier(data);
            
            System.out.println(algo.toString());
                  
            // produce a list with attnames
            List<String> attNames = new LinkedList<String>();
            for (int i = 0; i < data.numAttributes(); i++) {
            	attNames.add(data.attribute(i).name());
            }
            
           
            String algoStr = algo.toString().split("===\\n")[2];
            algoStr = algoStr.split("===")[0];
            System.out.println(algoStr);
            
            String usedAtts = algo.toString().split("Feature set: ")[1];
            usedAtts = usedAtts.split("\\n")[0];
            int usedAttsi[] = Arrays.stream(usedAtts.split(",")) // Split into a Stream<String>
            	.map(String::trim)        // Trim whitespace from each string
            	.mapToInt(Integer::parseInt) // Convert each string to an int
            	.toArray();
                        
            System.out.println(Arrays.toString(usedAttsi));
            
            
            
            List<String[]> ruleList = new LinkedList<String[]>();
            List<Float[]> ruleMetrics = new LinkedList<Float[]>();
            
            
            processTable(algoStr, attNames, ruleList, ruleMetrics, usedAttsi);
            
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
            
            generateDatasetMetrics();
            
            
        }catch(Exception e) {
        	e.printStackTrace();
        	return false;
        }		
		
		return true;		
	}

	
	
	private static void processTable(String dtstr, List<String> attNames, 
			List<String[]> ruleList, List<Float[]> ruleMetrics, 
			int usedAttsi[]) {
		
			String lines[] = dtstr.split("\\n");
			
			for (String line: lines) {
				System.out.println("=====");
				
				// cria uma regra com *		
				String[] ruleVector = new String[attNames.size()];
				Arrays.fill(ruleVector, "*");
				
				int i = 0;
				StringTokenizer st = new StringTokenizer(line, " ");
				while (st.hasMoreTokens()) {
					ruleVector[usedAttsi[i] -1] = st.nextToken();		 
					i++;
		        }				
				System.out.println(Arrays.toString(ruleVector));
				
				ruleList.add(ruleVector);
				
				// TODO: implementar o calculo das metricas das regras, o DT não traz isso na sua saída
				Float metrics[] = {1f,1f};
				ruleMetrics.add(metrics);
				
			}
		
	}

}
