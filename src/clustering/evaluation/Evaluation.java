package clustering.evaluation;
/**
 * The abstract class for methods that implement an evaluation metric.
 * Classes that extend this class should implement {@link #evaluate(int[],float[][]} method.
 * @author Lefas Aristeidis
 */
public abstract class Evaluation {
	/**
	 * Returns the evaluation value for the implemented metric.
	 * @param clusters An array containing the cluster assignment of each entity
	 * @param occurenceTable The document-term table. occurenceTable(i,j) value represents the relevant importance of term j for document i.
	 * @return The result of the evaluation metric
	 */
	public abstract float evaluate(int[] clusters, float[][] occurenceTable);
	/**
	 * Returns the simple name of the evaluation class.
	 * @return The simple name of the evaluation class
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
