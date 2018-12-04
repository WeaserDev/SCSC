package clustering.distance;

public class EuclideanDistance extends DistanceFunction {

	
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
