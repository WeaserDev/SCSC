package visualization;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class PrintFile extends Visualization {
	
	
	public PrintFile(int[] clusters, HashMap<Integer,String> idFile, String[][] labels) {
		super(clusters, idFile, labels);
	}
	
	public void visualize(String fileName) {
		try {
			BufferedWriter wr = new BufferedWriter(new FileWriter(fileName));
			int clusterNumber=clusterNumber();
			for (int i=0; i<clusterNumber; i++) {
			wr.write("Cluster " + (i+1));
			wr.newLine();
				for (int k=0; k<clusters.length; k++) {
					if (clusters[k]==i) {
						wr.write("file: " + idFile.get(k) + ", ");
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
	
	protected int clusterNumber() {
		int max = clusters[0];
		for (int i=1; i < clusters.length ; i++) {
			if (clusters[i]>max) {
				max=clusters[i];
			}
		}
		max+=1;
		return max;
	}
	

}
