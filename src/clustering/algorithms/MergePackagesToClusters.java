package clustering.algorithms;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;


public class MergePackagesToClusters implements Clustering {
	File rootDir;
	int mergeThreshold;
	String extensions[];
	
	public MergePackagesToClusters(File rootDir, String[] extensions, int mergeThreshold) {
		this.extensions = extensions;
		this.rootDir = rootDir;
		this.mergeThreshold = mergeThreshold;
	}
	
	public int[] returnClusters() {
		List<List<String>> foldersList  = createFoldersList();
		int[] clusters = createClusters(foldersList);
		return clusters;
	}
	protected int[] createClusters(List<List<String>> foldersList) {
		int[] clusters = new int[foldersList.size()];
		int[] clusterMembersCount = new int[clusters.length];
		clusters[0]=1;
		int clusterCount = 1;
		clusterMembersCount[0] = 1;
		boolean foldersChanged = false;
		for (int i=1; i<foldersList.size(); i++) {	
			for (int k=0;k<i;k++) {
				if(foldersList.get(i).equals(foldersList.get(k))) {
					clusters[i] = clusters[k];
					clusterMembersCount[clusters[i]-1]+=1;
					break;
				}
			}
			if (clusters[i]==0) {
				clusterCount+=1;
				clusters[i]=clusterCount;
				clusterMembersCount[clusters[i]-1]+=1;
			}
		}
		for (int i=0;i<clusters.length;i++) {
			clusters[i]--;
		}
		for (int i=0; i<clusterCount;i++) {
			if(clusterMembersCount[i]<mergeThreshold) {
				for (int j=0;j<foldersList.size();j++) {
					if(clusters[j]==i) {
						if(foldersList.get(j).size()>0) {
							foldersList.get(j).remove(foldersList.get(j).size()-1);
							foldersChanged = true;
						}
					}
				}
			}
		}
		if (foldersChanged) {
			return createClusters(foldersList);
		} else {
			return clusters;
		}
	}
	
	protected List<List<String>> createFoldersList(){
		Collection<File> files= FileUtils.listFiles(rootDir, extensions , true);
		List<List<String>> foldersList = new ArrayList<List<String>>();
		for (File file : files) {
			String path = file.getParent();
			List<String> folders = new ArrayList<String>(Arrays.asList(path.split(Pattern.quote(File.separator))));
			foldersList.add(folders);			
		}
		int commonFoldersNumber = commonFoldersNumber(foldersList);
		for (int i=0;i<foldersList.size();i++) {
			foldersList.set(i, foldersList.get(i).subList(commonFoldersNumber, foldersList.get(i).size()));
		}
		return foldersList;
	}
	
	protected int commonFoldersNumber(List<List<String>> foldersList) {
		int commonFolders = 0;
		boolean notEqual=false;
		for (int i=0; i<foldersList.get(0).size();i++) {
			for (int k=1; k<foldersList.size();k++) {
				if(foldersList.get(k).size()<=i) {
					notEqual = true;
					break;
				}
				else if (!foldersList.get(0).get(i).equals(foldersList.get(k).get(i))) {
					notEqual = true;
					break;
				}
			}	
			if (notEqual) {
				break; 
			}
			else {
				commonFolders++;
			}	
		}
		return commonFolders;
	}
}

