package run;

import coordinator.ModelRuleMatchCount;
import coordinator.ModelRuleMatchWeighted;
import coordinator.WekaModelWrapper;
import results.FormatResultsToGrep;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;

import coordinator.Model;

public class RunCoordinator {

	public static void main(String[] args) throws Exception {

		Model model = null; 
		
		if (args[0].equals("RuleMatchCount")) {
			model = new ModelRuleMatchCount();
		} else 	if (args[0].equals("RuleMatchWeighted")) {
			model = new ModelRuleMatchWeighted();
		} else {
			System.out.println("Invalid Model!");
			System.exit(1);
		}
		
		
		// adiciona os dados dos nós
		for (int i = 4; i < args.length; i++) {
			model.addDatasetMetricFile(args[i] + ".J48datasetmetrics");
			model.addRuleFile(args[i] + ".J48rules");		
			model.addRuleMetricFiles(args[i] + ".J48rulesmetrics");

		}
		
		// métodos para que o modelo carregue os dados de cada nó e crie o modelo centralizado
		model.loadDatasetMetrics();
		model.loadRules();
		model.loadRuleMetrics();
				
		
		// dump do modelo criado - para depuração apenas
		System.out.println(model.toString());
		

		// testando o modelo 
        ArffLoader loader = new ArffLoader();
		loader.setSource(new File(args[1]));		
        Instances testDataSet = loader.getDataSet(); 
			
        // estabelece qual é a classe no dataset - só funciona se for a última 
        testDataSet.setClassIndex(testDataSet.numAttributes() - 1);
		        
        // WekaModelWrapper é um wrapper pra podermos usar as classes de avaliação de desempenho já existentes no Weka
        WekaModelWrapper myClassifier = new WekaModelWrapper();
        myClassifier.setModel(model);
        weka.classifiers.Evaluation eval = new weka.classifiers.Evaluation(testDataSet);
		eval.evaluateModel(myClassifier, testDataSet);
		
		// mostra os resultados no padrão weka
		System.out.println(eval.toSummaryString("\nResults\n======\n", false));
        System.out.println(eval.toClassDetailsString("\nClass Details\n======\n"));
        System.out.println(eval.toMatrixString("\nConfusion Matrix\n======\n"));
        
        FormatResultsToGrep fmtr = new FormatResultsToGrep(eval,  args[3], args[2]);
        System.out.println(fmtr.evalResultsToGrep());
        
 

	}

}
