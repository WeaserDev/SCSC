package clustering.distance;
/**
 * Calculates the dot distance of the 2 vectors.
 * Extends abstract class distanceFunction.
 * @see clustering.distance distanceFunction
 * @author Lefas Aristeidis
 */
public class DotDistance extends DistanceFunction {
	/**
	 * Calculates the dot distance of the 2 vectors.
	 */
	public double distance(float[] firstVector, float[] secondVector) {
		double distance = 0;

		for (int i=0; i<firstVector.length; i++) {
			distance += firstVector[i]*secondVector[i];
		}
		distance = 1 - Math.exp(-distance/firstVector.length);	
		return distance;
	}

}
