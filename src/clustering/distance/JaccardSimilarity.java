package clustering.distance;

public class JaccardSimilarity extends AssociationCoefficient {

	@Override
	public double distance(float[] firstVector, float[] secondVector) {
		calculateQuantities(firstVector,secondVector);		
		return a/(a+b+c);
	}

}
