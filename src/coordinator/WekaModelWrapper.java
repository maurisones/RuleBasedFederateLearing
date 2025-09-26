package coordinator;

import java.util.Arrays;

import weka.classifiers.AbstractClassifier;
import weka.core.Instance;
import weka.core.Instances;

public class WekaModelWrapper extends AbstractClassifier {
	
	private Model model; 

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	@Override
	public void buildClassifier(Instances arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public double classifyInstance(Instance instance) throws Exception {
		
		String[] stringArray = new String[instance.numAttributes()];
        for (int i = 0; i < instance.numAttributes(); i++) {
            stringArray[i] = instance.stringValue(i);	            
        }
        
                
		String pred = model.classifyInstance(stringArray);
		
		System.out.println(pred + "<->" + model.classnames[0]);
		
		if (pred.compareTo(model.classnames[0]) == 0) {
			return 0;
		}
		
		
		return 1;
	}

	

}
