package clustering.evaluation;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;


public class PackagesToClusters {
	public int[] Clusters(File rootDir) {
		String extensions[] = {"java" , "py"};
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
		return clusters;
	}
}
