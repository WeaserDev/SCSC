package clustering.distance;

public class JaccardDistance extends AssociationCoefficient {

	@Override
	public double distance(float[] firstVector, float[] secondVector) {
		calculateQuantities(firstVector,secondVector);		
		return 1 - (a/(a+b+c));
	}

}
