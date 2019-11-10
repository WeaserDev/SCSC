package clustering.distance;
/**
 * The abstract class for methods that calculate the distance between the feature vectors.
 * Class that extend this class should implement {@link #distance(float[][], float[][])} method.
 * @author Lefas Aristeidis
 */
public abstract class DistanceFunction {
	public abstract double distance (float[] firstVector, float[] secondVector);
}
