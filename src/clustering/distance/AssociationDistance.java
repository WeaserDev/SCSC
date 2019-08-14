package clustering.distance;

public abstract class AssociationDistance extends DistanceFunction {
	int a,b,c,d;
	
	protected void calculateQuantities(float[] firstVector, float[] secondVector) {
		for (int i=0;i<firstVector.length;i++) {
			if (firstVector[i]!=0) {
				if(secondVector[i]!=0) {
					a+=1; //a is the number of features that exist in both entities
				} else {
					b+=1; //b is the number of features that exist only in first entity
				}
			}else {
				if(secondVector[i]!=0) {
					c+=1; //c is the number of features that exist only in second entity
				} else {
					d+=1; //d is the number of features that doesn't exist in any of the two entities
				}
			}
		}
	}
	
}
