package clustering;

import java.util.ArrayList;
import java.util.List;

import weka.core.*;

abstract class WekaClustering extends Clustering {
	protected Instances wekaDataset;
	
	WekaClustering(float[][] occurenceTable) {
		super(occurenceTable);
		
	}
	
	
	protected void createWekaData() {
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

		wekaDataset = new Instances("wekaDataset", atts, instances.size());

		for(Instance inst : instances) {
			wekaDataset.add(inst);
		}

	}
	
}
