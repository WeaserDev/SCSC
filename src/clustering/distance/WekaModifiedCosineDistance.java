package clustering.distance;

import weka.core.Instance;
import weka.core.neighboursearch.PerformanceStats;

public class WekaModifiedCosineDistance extends WekaCosineDistance {

	private static final long serialVersionUID = -4559805530395367756L;

	public double distance(Instance first, Instance second, double cutOffValue, PerformanceStats stats) {
		  double distance = super.distance(first, second, cutOffValue, stats);
		  
		  if (distance > 0.5) {
			  distance = (1-distance)*distance*distance + distance*Math.sqrt(distance);
		  }
		  else {
			  distance = Math.sqrt(distance);
		  }
		  
		  return distance;		  
	  }

}
