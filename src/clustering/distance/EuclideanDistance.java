package clustering.distance;
/**
 * Calculates the euclidean distance of the 2 vectors.
 * Extends abstract class distanceFunction.
 * @see clustering.distance distanceFunction
 * @author Lefas Aristeidis
 */
public class EuclideanDistance extends DistanceFunction {

	/**
	 * Calculates the euclidean distance of the 2 vectors.
	 */
	public double distance(float[] firstVector, float[] secondVector) {
		double distance = 0;
		for (int i=0 ; i<firstVector.length; i++) {
			double difference = firstVector[i]-secondVector[i];
			distance += difference*difference;
		}
		distance = Math.sqrt(distance);
		return distance;
	}

}
