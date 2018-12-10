package clustering.algorithms;

public abstract class Clustering {
	protected float occurenceTable[][];
	protected int[] clusters;

	
	public Clustering(float[][] occurenceTable){
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
