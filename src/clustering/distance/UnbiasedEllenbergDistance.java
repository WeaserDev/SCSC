package clustering.distance;

public class UnbiasedEllenbergDistance extends DistanceFunction {
	int b,c;
	double ma;
	@Override
	public double distance(float[] firstVector, float[] secondVector) {
		calculateQuantities(firstVector,secondVector);
		return 1 - ma/(2*(ma/2 + c + b));
	}
	
	protected void calculateQuantities(float[] firstVector, float[] secondVector) {
		for (int i=0;i<firstVector.length;i++) {
			if (firstVector[i]!=0) {
				if(secondVector[i]!=0) {
					ma+= firstVector[i] + secondVector[i]; //ma is the sum of features that exist in both entities
				} else {
					b+=1; //b is the number of features that exist only in first entity
				}
			}else {
				if(secondVector[i]!=0) {
					c+=1; //c is the number of features that exist only in second entity
				}
			}
		}
	}
}
