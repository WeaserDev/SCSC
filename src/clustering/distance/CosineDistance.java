package clustering.distance;
/**
 * Calculates the cosine distance of the 2 vectors. 
 * Extends abstract class distanceFunction.
 * @see clustering.distance distanceFunction
 * @author Lefas Aristeidis
 */
public class CosineDistance extends DistanceFunction {
	/**
	 * Calculates the cosine distance of the 2 vectors.
	 */
	public double distance(float[] firstVector, float[] secondVector) {
		double distance = 0;
		double normFirst = 0;
		double normSecond = 0;
		for (int i=0; i<firstVector.length; i++) {
			distance += firstVector[i]*secondVector[i];
			normFirst += Math.pow(firstVector[i], 2);
			normSecond += Math.pow(secondVector[i], 2);	
		}
		if (normFirst==0 && normSecond==0) distance=1;
		else if (normFirst==0 || normSecond==0) distance = 0;
		else distance = distance/( Math.sqrt(normFirst)*Math.sqrt(normSecond));
		distance = 1 - distance;
		return distance;
	}

}
