package dataImport;

import java.io.File;


public class ProjectInput {
	private FileInput[] fileInput;
	private String projectName;
	private File projectDirectory;
	
	public ProjectInput (File rootDirectory) {
		this.fileInput = FileInput.createFileInput(rootDirectory);
		this.projectName = rootDirectory.getName();
		this.projectDirectory = rootDirectory;
	}
	
	public FileInput[] getInput() {
		return fileInput;
	}
	public File getProjectDirectory() {
		return projectDirectory;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public static ProjectInput[] createProjectInput(File projectDir) {
		File[] directories = projectDir.listFiles(File::isDirectory);
		ProjectInput[] input = new ProjectInput[directories.length];
		
		for (int i=0; i<directories.length; i++) {
			if (directories[i].listFiles().length>0) {
				input[i] = new ProjectInput(directories[i]);
				System.out.println("Imported project "+directories[i].getPath());
			}
		}
		return input;
	}
	
}
