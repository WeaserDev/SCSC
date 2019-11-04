package clustering.algorithms;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

/**
 * Implements the clustering interface.
 * Generates the clusters corresponding to the folder structure of the project.
 * @author Lefas Aristeidis
 */
public class PackagesToClusters implements Clustering {
	File rootDir;
	String[] extensions;
	/**
	 * Basic constructor of the class.
	 * @param rootDir File object corresponding to the folder that contains the project
	 * @param extensions Array of Strings that contains the extensions of files we want to cluster
	 */
	public PackagesToClusters(File rootDir, String[] extensions) {
		this.extensions = extensions;
		this.rootDir = rootDir;
	}
	/**
	 * Returns the cluster assignments.
	 * @return An array containing the cluster assignment for each entity.
	 */
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
