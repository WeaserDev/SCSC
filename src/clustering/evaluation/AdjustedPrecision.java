package clustering.evaluation;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class AdjustedPrecision extends SupervisedEvaluation {
	File rootDirectory;
	List<List<String>> foldersList = new ArrayList<List<String>>();
	public AdjustedPrecision(File rootDirectory) {
		super(null);
		this.rootDirectory = rootDirectory;
		
	}

	@Override
	public float evaluate(int[] clusters, float[][] occurenceTable) {
		if (foldersList.isEmpty()) {
			foldersList = createFoldersList();
		}
		int clusterIntrapairs = 0;
		float precision = 0;
		for (int i=0; i<clusters.length; i++) {
			for (int k=i+1; k<clusters.length; k++) {
				if (clusters[i]==clusters[k]) {
					clusterIntrapairs+=1;
					int commonFolders = 0;
					for (int j=0; (j<foldersList.get(i).size())&&(j<foldersList.get(k).size());j++) {
						if (foldersList.get(i).get(j).equals(foldersList.get(k).get(j))) {
							commonFolders++;
						}
						else {
							break;
						}
					}
					if (foldersList.get(i).size()==0 && foldersList.get(k).size()==0) {
						precision+=1;
					}
					else {
					precision += (float)commonFolders / (foldersList.get(i).size()+foldersList.get(k).size()-commonFolders);	
					}
				}		
			}
		}
		precision = precision/clusterIntrapairs;
		return precision;
	}
	
	protected List<List<String>> createFoldersList(){
		String extensions[] = {"java" , "py", "html", "xml", "c", "h"};
		Collection<File> files= FileUtils.listFiles(rootDirectory, extensions , true);
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
