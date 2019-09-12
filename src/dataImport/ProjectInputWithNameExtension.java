package dataImport;

import java.io.File;


public class ProjectInputWithNameExtension extends ProjectInput {
	private FileInputWithNameExtension[] fileInput;
	private String projectName;
	private File projectDirectory;
	
	public ProjectInputWithNameExtension (File rootDirectory) {
		super(rootDirectory);
		this.fileInput = FileInputWithNameExtension.createFileInput(rootDirectory);
		this.projectName = rootDirectory.getName();
		this.projectDirectory = rootDirectory;
	}
	
	public FileInputWithNameExtension[] getInput() {
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
