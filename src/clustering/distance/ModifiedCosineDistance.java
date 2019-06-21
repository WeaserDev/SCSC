package clustering.distance;

public class ModifiedCosineDistance extends CosineDistance {
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
