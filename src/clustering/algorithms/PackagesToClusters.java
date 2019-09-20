package clustering.algorithms;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;


public class PackagesToClusters implements Clustering {
	File rootDir;
	String[] extensions;
	
	public PackagesToClusters(File rootDir, String[] extensions) {
		this.extensions = extensions;
		this.rootDir = rootDir;
	}
	
	public int[] returnClusters() {
		Collection<File> files= FileUtils.listFiles(rootDir, extensions , true);
		File[] filesArray = files.toArray(new File[files.size()]);

		int[] clusters = new int[filesArray.length];
		clusters[0]=1;
		int clusterCount = 1;
		for (int i=1; i<filesArray.length; i++) {	
			for (int k=0;k<i;k++) {
				
				if(filesArray[i].getParentFile().equals(filesArray[k].getParentFile()) ) {	
					clusters[i]=clusters[k];
					break;
				}
			}
			if (clusters[i]==0) {
				clusterCount+=1;
				clusters[i]=clusterCount;
			}
		}
		for (int i=0;i<clusters.length;i++) {
			clusters[i]--;
		}
		return clusters;
	}
}
