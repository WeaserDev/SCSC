package clustering.evaluation;
/**
 * The abstract class for methods that implement a supervised evaluation metric.
 * extends Evaluation class.
 * Classes that extend this class should implement {@link #evaluate(int[],float[][]} method.
 * @author Lefas Aristeidis
 */
public abstract class SupervisedEvaluation extends Evaluation {
	int[] authoritativeClusters;
	public abstract float evaluate(int[] clusters, float[][] occurenceTable);
	
	public SupervisedEvaluation(int[] authoritativeClusters) {
		this.authoritativeClusters = authoritativeClusters;
	}
}
