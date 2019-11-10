package clustering.distance;
/**
 * Calculates the Modified Cosine Distance of the 2 vectors.
 * Extends abstract class distanceFunction.
 * @see clustering.distance distanceFunction
 * @author Lefas Aristeidis
 */
public class ModifiedCosineDistance extends CosineDistance {
	/**
	 * Calculates the Modified Cosine Distance of the 2 vectors.
	 */
	public double distance(float[] firstVector, float[] secondVector) {
		  double distance = super.distance(firstVector, secondVector);
		  
		  if (distance < 0.5) {
			  distance = (1-distance)*distance*distance + distance*Math.sqrt(distance);
		  }
		  else {
			  distance = Math.sqrt(distance);
		  }
		  
		  return distance;		  
	  }
}
