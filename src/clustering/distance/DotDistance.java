package clustering.distance;

public class DotDistance extends DistanceFunction {

	public double distance(float[] firstVector, float[] secondVector) {
		double distance = 0;

		for (int i=0; i<firstVector.length; i++) {
			distance += firstVector[i]*secondVector[i];
		}
		distance = 1 - Math.exp(-distance/firstVector.length);	
		return distance;
	}

}
