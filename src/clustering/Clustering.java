package clustering;

abstract class Clustering {
	protected float occurenceTable[][];
	protected int[] assignments;

	
	Clustering(float[][] occurenceTable){
		this.occurenceTable = occurenceTable;
	}
	
	protected abstract int[] createClusters();
	
	public int[] returnAssignments() {
		if (assignments==null){
			assignments = createClusters();
		}
		return assignments;
	}
	
}
