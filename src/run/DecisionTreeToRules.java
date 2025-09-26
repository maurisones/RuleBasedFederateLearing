package run;

import node.RuleGeneratorAlgorihtm;
import node.RuleGeneratorAlgorihtmJ48; 
 
public class DecisionTreeToRules { 
    public static void main(String[] args) {
    	
    	// Chama o gerador de regras para cada nó
    	// parâmetros: arquivo de entrada; diretório e prefixo de arquivos de saída; usar prunning 
    	// nó 1
    	RuleGeneratorAlgorihtm ruleAlgo = new RuleGeneratorAlgorihtmJ48(
    			"/home/mauri/Downloads/RulesTemp/breast-cancer-train-30.arff",
    			"/home/mauri/Downloads/RulesTemp/breast-cancer-30",
    			false);    	
    	ruleAlgo.generateRules();

    	// nó 2
    	ruleAlgo = new RuleGeneratorAlgorihtmJ48(
    			"/home/mauri/Downloads/RulesTemp/breast-cancer-train-50.arff",
    			"/home/mauri/Downloads/RulesTemp/breast-cancer-50",
    			false);    	
    	ruleAlgo.generateRules();           
    	
    	// nó 3
    	ruleAlgo = new RuleGeneratorAlgorihtmJ48(
    			"/home/mauri/Downloads/RulesTemp/breast-cancer-train-70.arff",
    			"/home/mauri/Downloads/RulesTemp/breast-cancer-70",
    			false);    	
    	ruleAlgo.generateRules();       
    }

} 

		
