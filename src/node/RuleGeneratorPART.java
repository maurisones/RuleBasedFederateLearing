package node;


import weka.classifiers.rules.PART;

public class RuleGeneratorPART extends RuleGeneratorAlgorithm {

	public RuleGeneratorPART(String trainDataSetFileName, String outputFileName) {
		super(trainDataSetFileName, outputFileName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean generateRules() {
		try { 
	           
			this.loadArffDataset();
 
            // Build the DecisionTable classifier 
            PART algo = new PART();
            //algo.setDisplayRules(true);
            algo.buildClassifier(data);
            
            System.out.println(algo.toString());
            
            // TODO: implement the rule extraction from de algorithm output
            
            /*
            table.
            
                   
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
            */
            generateDatasetMetrics();
            
            
        }catch(Exception e) {
        	e.printStackTrace();
        	return false;
        }		
		
		return true;		
	}

}
