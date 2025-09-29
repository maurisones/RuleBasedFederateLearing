package run;

import coordinator.ModelRuleMatchCount;
import coordinator.ModelRuleMatchWeighted;
import coordinator.WekaModelWrapper;
import results.FormatResultsToGrep;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;

import coordinator.Model;

public class RunWekaJ48 {

	/**	 * 
	 * @param args
	 * 	args[0]: dataset Name
	 * 	args[1]: trainDataset
	 *  args[2]: unpruned {0, 1} -> {false, true}
	 *  args[4]: testDataset 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		// testando o modelo 
        ArffLoader loader = new ArffLoader();
		loader.setSource(new File(args[1]));		
        Instances trainDataSet = loader.getDataSet(); 

		// Build the decision tree classifier 
        J48 j48Tree = new J48(); 
		
        j48Tree.setUnpruned(args[2].compareTo("0") == 0 ? false : true);
        
        // estabelece qual é a classe no dataset - só funciona se for a última 
        trainDataSet.setClassIndex(trainDataSet.numAttributes() - 1);
        
        j48Tree.buildClassifier(trainDataSet);   
        
        // testando o modelo        
		loader.setSource(new File(args[3]));		
        Instances testDataSet = loader.getDataSet(); 
			
        // estabelece qual é a classe no dataset - só funciona se for a última 
        testDataSet.setClassIndex(testDataSet.numAttributes() - 1);
		        
        weka.classifiers.Evaluation eval = new weka.classifiers.Evaluation(testDataSet);
		eval.evaluateModel(j48Tree, testDataSet);
		
		// mostra os resultados no padrão weka
		System.out.println(eval.toSummaryString("\nResults\n======\n", false));
        System.out.println(eval.toClassDetailsString("\nClass Details\n======\n"));
        System.out.println(eval.toMatrixString("\nConfusion Matrix\n======\n"));
        
        FormatResultsToGrep fmtr = new FormatResultsToGrep(eval, args[0], "PureWekaJ48");
        System.out.println(fmtr.evalResultsToGrep());
  
	}

}
