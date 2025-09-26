package run;

import coordinator.ModelRuleMatchCount;
import coordinator.ModelRuleMatchWeighted;
import coordinator.WekaModelWrapper;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;

import coordinator.Model;

public class RunCreateModel {

	public static void main(String[] args) throws Exception {

		// define um modelo podendo ser ModelRuleMatchCount ou ModelRuleMatchWeighted
		// tem que passar o dataset de teste no construtor 
		Model model = new ModelRuleMatchCount("/home/mauri/Downloads/RulesTemp/breast-cancer-test.arff");
		//Model model = new ModelRuleMatchWeighted("/home/mauri/Downloads/RulesTemp/breast-cancer-test.arff");
		
		// adiciona os arquivos gerados pelo classificador em cada um dos nós federados
		// nesse exemplo, separei o dataset sorteando x% de exemplos em cada um dos três nós X = {30, 50, 70}
		// nó 1
		model.addDatasetMetricFile("/home/mauri/Downloads/RulesTemp/breast-cancer-30.J48datasetmetrics");
		model.addRuleFile("/home/mauri/Downloads/RulesTemp/breast-cancer-30.J48rules");		
		model.addRuleMetricFiles("/home/mauri/Downloads/RulesTemp/breast-cancer-30.J48rulesmetrics");

		// nó 2
		model.addDatasetMetricFile("/home/mauri/Downloads/RulesTemp/breast-cancer-50.J48datasetmetrics");
		model.addRuleFile("/home/mauri/Downloads/RulesTemp/breast-cancer-50.J48rules");		
		model.addRuleMetricFiles("/home/mauri/Downloads/RulesTemp/breast-cancer-50.J48rulesmetrics");

		// nó 3
		model.addDatasetMetricFile("/home/mauri/Downloads/RulesTemp/breast-cancer-70.J48datasetmetrics");
		model.addRuleFile("/home/mauri/Downloads/RulesTemp/breast-cancer-70.J48rules");		
		model.addRuleMetricFiles("/home/mauri/Downloads/RulesTemp/breast-cancer-70.J48rulesmetrics");
				
		
		// métodos para que o modelo carregue os dados de cada nó e crie o modelo centralizado
		model.loadDatasetMetrics();
		model.loadRules();
		model.loadRuleMetrics();
				
		
		// dump do modelo criado - para depuração apenas
		System.out.println(model.toString());
		

		// testando o modelo 
		// TODO - retirar a necessidade de passar o ds de teste para o modelo, pode ser passado direto para o wrapper
        ArffLoader loader = new ArffLoader();
		loader.setSource(new File(((ModelRuleMatchCount)model).getTestDataSetFileName()));		
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
 

	}

}
