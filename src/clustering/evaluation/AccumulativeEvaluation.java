package clustering.evaluation;

public class AccumulativeEvaluation extends Evaluation {
	private float accumulativeScore = 0;
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
}