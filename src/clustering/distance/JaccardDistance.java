package clustering.distance;
/**
 * Calculates the jaccard distance of the 2 vectors.
 * Extends abstract class AssociationDistance.
 * @see clustering.distance AssociationDistance
 * @author Lefas Aristeidis
 */
public class JaccardDistance extends AssociationDistance {
	/**
	 * Calculates the jaccard distance of the 2 vectors.
	 */
	@Override
	public double distance(float[] firstVector, float[] secondVector) {
		calculateQuantities(firstVector,secondVector);		
		return 1 - (a/(a+b+c));
	}

}
