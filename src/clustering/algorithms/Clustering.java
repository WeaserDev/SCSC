package clustering.algorithms;

abstract class Clustering {
	protected float occurenceTable[][];
	protected int[] clusters;

	
	Clustering(float[][] occurenceTable){
		this.occurenceTable = occurenceTable;
	}
	
	protected abstract int[] createClusters();
	
	public int[] returnClusters() {
		if (clusters==null){
			clusters = createClusters();
		}
		return clusters;
	}
	
}
