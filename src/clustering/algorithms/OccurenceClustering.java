package clustering.algorithms;

public abstract class OccurenceClustering implements Clustering {
	protected float occurenceTable[][];
	protected int[] clusters;

	
	public OccurenceClustering(float[][] occurenceTable){
		this.occurenceTable = occurenceTable;
	}
	
	public void clear() {
		clusters = null;
	}
	
	protected abstract int[] createClusters();
	
	public int[] returnClusters() {
		if (clusters==null){
			clusters = createClusters();
		}
		return clusters;
	}
	
}
