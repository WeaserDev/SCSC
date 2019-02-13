package visualization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import dataImport.FileInput;
import dataImport.ProjectInput;

public class FirefoxVisualizer {
	private ProjectInput project;
	private HashMap<Integer, ArrayList<FileInput>> fileClusters = new HashMap<Integer, ArrayList<FileInput>>();
	private ArrayList<FileInput> wrong = new ArrayList<FileInput>();
	private HashMap<Integer, String> labels = new HashMap<Integer, String>();
	public FirefoxVisualizer(int[] clusters, ProjectInput project, HashMap<Integer, ArrayList<String>> labels, int[] originalClusters) {
		this.project = project;
		for(int i=0;i<clusters.length;i++) {
			if(!fileClusters.containsKey(clusters[i]))
				fileClusters.put(clusters[i], new ArrayList<FileInput>());
			fileClusters.get(clusters[i]).add(project.getInput()[i]);
		}
		for(int cluster : fileClusters.keySet()) {
			String clusterLabel = "";
			for(String label : labels.get(cluster)) {
				if(!clusterLabel.isEmpty())
					clusterLabel += ", ";
				clusterLabel += label;
			}
			this.labels.put(cluster, clusterLabel);
		}
		if(originalClusters!=null) {
			HashMap<FileInput, Integer> fileCorrectClusters = new HashMap<FileInput, Integer>();
			for(int i=0;i<originalClusters.length;i++) 
				fileCorrectClusters.put(project.getInput()[i], originalClusters[i]);
			for(ArrayList<FileInput> cluster : fileClusters.values()) {
				HashMap<Integer, Integer> clusterCount = new HashMap<Integer, Integer>();
				for(FileInput member : cluster)
					clusterCount.put(fileCorrectClusters.get(member), clusterCount.getOrDefault(fileCorrectClusters.get(member), 0)+1);
				int correctCluster = -1;
				int correctCount = 0;
				for(Entry<Integer, Integer> count : clusterCount.entrySet()) {
					if(count.getValue()>correctCount) {
						correctCount = count.getValue();
						correctCluster = count.getKey();
					}
				}
				for(FileInput member : cluster)
					if(fileCorrectClusters.get(member)!=correctCluster)
						wrong.add(member);
			}
		}
	}
	public void visualize() {
		String output = "";
		for(int cluster : fileClusters.keySet()) {
			String children = "";
			for(FileInput child : fileClusters.get(cluster)) {
				if(!children.isEmpty())
					children += ",";
				children += "{\"name\":\""+child.getFileName()+"\", \"size\": "+(3+child.getFileCode().length()-child.getFileCode().replaceAll("\n", "").length())
						 +", \"color\":"+(wrong.contains(child)?"\"red\"":"\"white\"")	
						+"}";
			}
			if(!output.isEmpty())
				output += ",";
			output += "{\"name\":\""+labels.get(cluster)+"\", \"children\":["+children+"]}";
		}
		output = "{\"name\":\"\", \"children\":["+output+"]}";
		try (PrintWriter out = new PrintWriter("visualize/dataCluster.json")) {
		    out.println(output);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		String path = "file:///"+(new File("visualize/visualizeCluster.html")).getAbsolutePath();
		try {
			 Runtime rt = Runtime.getRuntime();
		     try{
		    	 Process clientProcess = rt.exec(new String[] {"C:\\Program Files\\Mozilla Firefox\\firefox.exe","-new-tab", path});
		     clientProcess.waitFor();
		     } catch (Exception e){
		    	 e.printStackTrace();
		     }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
