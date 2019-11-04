package sourceCodeSemanticClustering;
import clustering.algorithms.MergePackagesToClusters;
import java.io.File;
import java.io.IOException;

import auth.eng.textManager.stemmers.Stemmer;

public class test4 {
	public static void main(String[] args) throws IOException{
		String testProjectPath = "..\\";
		int threshold = 5;
		MergePackagesToClusters merge = new MergePackagesToClusters(new File(testProjectPath), threshold);
		int[] evaluationClusters = merge.returnClusters();
		for (int i=0;i<evaluationClusters.length;i++) {
			System.out.println(evaluationClusters[i]);
			Stemmer stem = new Stemmer;
		}
	}
}
