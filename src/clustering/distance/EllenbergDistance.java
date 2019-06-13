package clustering.distance;

public class EllenbergDistance extends DistanceFunction {
	double ma,mb,mc;
	@Override
	public double distance(float[] firstVector, float[] secondVector) {
		calculateQuantities(firstVector,secondVector);
		return 1 - ma/(2*(ma/2 + mc + mb));
	}
	
	protected void calculateQuantities(float[] firstVector, float[] secondVector) {
		for (int i=0;i<firstVector.length;i++) {
			if (firstVector[i]!=0) {
				if(secondVector[i]!=0) {
					ma+= firstVector[i] + secondVector[i]; //ma is the sum of features that exist in both entities
				} else {
					mb+=firstVector[i]; //mb is the sum of features that exist only in first entity
				}
			}else {
				if(secondVector[i]!=0) {
					mc+=secondVector[i]; //mc is the sum of features that exist only in second entity
				}
			}
		}
	}

}
