package dataImport;

import java.io.File;


public class ProjectInput {
	private FileInput[] fileInput;
	protected String projectName;
	protected File projectDirectory;
	
	public ProjectInput (File rootDirectory, String[] extensions) {
		this.fileInput = FileInput.createFileInput(rootDirectory, extensions);
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
	
	public static ProjectInput[] createProjectInput(File projectDir, String[] extensions) {
		File[] directories = projectDir.listFiles(File::isDirectory);
		ProjectInput[] input = new ProjectInput[directories.length];
		
		for (int i=0; i<directories.length; i++) {
			if (directories[i].listFiles().length>0) {
				input[i] = new ProjectInput(directories[i], extensions);
				System.out.println("Imported project "+directories[i].getPath());
			}
		}
		return input;
	}
	
}
