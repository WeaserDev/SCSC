package visualization;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import featureExtraction.FeatureExtraction;

public class PrintFile extends Visualization {
	
	
	public PrintFile(int[] assignments, HashMap<Integer,String> idFile, String[][] labels) {
		super(assignments, idFile, labels);
	}
	
	public void visualize(String fileName) {
		

		try {
			BufferedWriter wr = new BufferedWriter(new FileWriter(fileName));
			int clusters=clusterNumber();
			wr.write("entropy: " + calculateEntropy());
			wr.newLine();
			wr.newLine();
			for (int i=0; i<clusters; i++) {
			wr.write("Cluster " + (i+1));
			wr.newLine();
				for (int k=0; k<assignments.length; k++) {
					if (assignments[k]==i) {
						wr.write(idFile.get(k) + ", ");
					}
				}
				wr.newLine();
				wr.write("labels: ");
				for (int k=0 ; k<labels[0].length ; k++) {
					wr.write(labels[i][k] + ", ");
				}
				wr.newLine();
				wr.newLine();
			}			
			wr.close();
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
