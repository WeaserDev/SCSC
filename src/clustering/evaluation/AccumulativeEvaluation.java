package clustering.evaluation;

public class AccumulativeEvaluation extends Evaluation {
	private float accumulativeScore = 0;
	private float bestScore = Float.NEGATIVE_INFINITY;
	private int timesRun = 0;
	private Evaluation baseEvaluation;
	
	public AccumulativeEvaluation(Evaluation baseEvaluation) {
		this.baseEvaluation = baseEvaluation;
	}

	@Override
	public float evaluate(int[] clusters, float[][] occurenceTable) {
		if(clusters!=null || occurenceTable!=null) {
			accumulativeScore += baseEvaluation.evaluate(clusters, occurenceTable);
			timesRun++;
			if(baseEvaluation.evaluate(clusters, occurenceTable) > bestScore)
				bestScore = baseEvaluation.evaluate(clusters, occurenceTable);
		}
		return getCurrentResults();
	}
	
	@Override
	public String toString() {
		return "Avg. "+baseEvaluation.toString();
	}
	
	public float getCurrentResults() {
		return accumulativeScore/timesRun;
	}
	
	public float getBestResults() {
		return bestScore;
	}
}