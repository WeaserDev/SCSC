package clustering.evaluation;

public class MojoQ extends MojoDistance {

	public MojoQ(int[] authoritativeClusters) {
		super(authoritativeClusters);
	}
	
	@Override
	public float evaluate(int[] clusters, float[][] occurenceTable) {
		float mojoDistance = super.evaluate(clusters,occurenceTable);
		return (1-mojoDistance/clusters.length)*100;
	}
}
