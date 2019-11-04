package clustering.algorithms;

import java.util.ArrayList;
import java.util.List;

import weka.clusterers.Clusterer;
import weka.core.*;
/**
 * This class extends the OccurenceClustering abstract class. It contains classes that convert the data input and output to work with weka libary.
 * Classes that extend this class implement algorithms from the weka library.
 * @author Lefas Aristeidis
 */
abstract public class WekaClustering extends OccurenceClustering {
	protected Instances wekaDataset;

	
	public WekaClustering(float[][] occurenceTable) {
		super(occurenceTable);	
	}
	
	
	protected Instances createWekaData() {
		ArrayList<Attribute> atts = new ArrayList<Attribute>();
		List<Instance> instances = new ArrayList<Instance>();
		int featuresNumber = occurenceTable[0].length;
		int filesNumber = occurenceTable.length;

		for(int i = 0; i < featuresNumber; i++)
		{
		    Attribute current = new Attribute( "Attribute" + i, i );
		    if(i == 0)
		    {
		        for(int k = 0; k < filesNumber; k++)
		        {
		            instances.add(new SparseInstance(featuresNumber));
		        }
		    }

		    for(int k = 0; k < filesNumber; k++)
		    {
		        instances.get(k).setValue(current, occurenceTable[k][i]);
		    }

		    atts.add(current);
		}

		Instances wekaData = new Instances("wekaDataset", atts, instances.size());

		for(Instance inst : instances) {
			wekaData.add(inst);
		}
		return wekaData;

	}
	
	protected int[] createClusterAssignments(Clusterer clusterer) {
		int[] clusters = new int[wekaDataset.numInstances()];
		int i=0;
		
		
		for(Instance instance: wekaDataset) {
			try {
				clusters[i] = clusterer.clusterInstance(instance);
				i+=1;
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		return clusters;
	}
	
}
