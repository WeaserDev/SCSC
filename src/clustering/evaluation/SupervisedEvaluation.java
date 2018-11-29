package clustering.evaluation;

public abstract class SupervisedEvaluation extends Evaluation {
	int[] authoritativeClusters;
	public abstract float evaluate(int[] clusters, float[][] occurenceTable);
	
	public SupervisedEvaluation(int[] authoritativeClusters) {
		this.authoritativeClusters = authoritativeClusters;
	}
}
