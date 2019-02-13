package visualization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import dataImport.FileInput;
import dataImport.ProjectInput;

public class FirefoxVisualizer {
	private ProjectInput project;
	private HashMap<Integer, ArrayList<FileInput>> fileClusters = new HashMap<Integer, ArrayList<FileInput>>();
	private HashMap<Integer, String> labels = new HashMap<Integer, String>();
	public FirefoxVisualizer(int[] clusters, ProjectInput project, HashMap<Integer, ArrayList<String>> labels) {
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
	}
	public void visualize() {
		String output = "{\"name\":\"\", \"children\":[";
		for(int cluster : fileClusters.keySet()) {
			String children = "";
			for(FileInput child : fileClusters.get(cluster)) {
				if(!children.isEmpty())
					children += ",";
				children += "{\"name\":\""+child.getFileName()+"\", \"size\": "+(3+child.getFileCode().length()-child.getFileCode().replaceAll("\n", "").length())+"}";
			}
			if(cluster!=0)
				output += ",";
			output += "{\"name\":\""+labels.get(cluster)+"\", \"children\":["+children+"]}";
		}
		output += "]}";
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
