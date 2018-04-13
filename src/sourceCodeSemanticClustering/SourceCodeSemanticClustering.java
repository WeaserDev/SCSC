package sourceCodeSemanticClustering;

import java.io.File;

import dataImport.FileInput;

public class SourceCodeSemanticClustering {

	public static void main(String[] args) {
		File projectDir=new File(("c:\\project"));
		FileInput[] fileIn = FileInput.createFileInput(projectDir);
		int size = fileIn.length;
		for (int i=0; i<size; i++) {	
			System.out.println(fileIn[i].fileName);
			System.out.println(fileIn[i].fileCode);
		}
	}


	

	

}
