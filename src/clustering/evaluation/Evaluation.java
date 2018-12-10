package clustering.evaluation;

public abstract class Evaluation {
	public abstract float evaluate(int[] clusters, float[][] occurenceTable);
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
