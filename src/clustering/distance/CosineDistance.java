package clustering.distance;

public class CosineDistance extends DistanceFunction {


	public double distance(float[] firstVector, float[] secondVector) {
		double distance = 0;
		double normFirst = 0;
		double normSecond = 0;
		for (int i=0; i<firstVector.length; i++) {
			distance += firstVector[i]*secondVector[i];
			normFirst += Math.pow(firstVector[i], 2);
			normSecond += Math.pow(secondVector[i], 2);	
		}
		distance = distance/( Math.sqrt(normFirst)*Math.sqrt(normSecond));
		distance = 1 - distance;
				
		return distance;
	}

}
