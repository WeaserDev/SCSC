package clustering.evaluation;

public class Fmeasure extends SupervisedEvaluation {
	Precision precision;
	Recall recall;
	
	public Fmeasure(int[] authoritativeClusters) {
		super(authoritativeClusters);
		this.precision = new clustering.evaluation.Precision(authoritativeClusters);
		this.recall = new clustering.evaluation.Recall(authoritativeClusters);
	}

	public float evaluate(int[] clusters, float[][] occurenceTable) {
		float prec = precision.evaluate(clusters, occurenceTable);
		float rec = recall.evaluate(clusters, occurenceTable);	
		return 2*prec*rec/(prec+rec);
	}

}
