package dataImport;

import java.io.File;


public class ProjectInput {
	private FileInput[] fileInput;
	private String projectName;
	
	public ProjectInput (FileInput[] fileInput,String projectName) {
		this.fileInput = fileInput;
		this.projectName = projectName;
	}
	
	public FileInput[] getInput() {
		return fileInput;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public static ProjectInput[] createProjectInput(File projectDir) {
		File[] directories = projectDir.listFiles(File::isDirectory);
		ProjectInput[] input = new ProjectInput[directories.length];
		
		for (int i=0; i<directories.length; i++) {	
			input[i] = new ProjectInput(FileInput.createFileInput(directories[i]),directories[i].getName());
		}
		return input;
	}
	
}
