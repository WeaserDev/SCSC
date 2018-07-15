package clustering.evaluation;

public abstract class Evaluation {
	public abstract float evaluate(int[] clusters, float[][] occurenceTable);
}
