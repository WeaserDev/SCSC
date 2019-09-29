package dataImport;

import java.io.File;


public class ProjectInputWithNameExtension extends ProjectInput {
	private FileInputWithNameExtension[] fileInput;
	
	public ProjectInputWithNameExtension (File rootDirectory, String[] extensions) {
		super(rootDirectory, new String[]{""});
		this.fileInput = FileInputWithNameExtension.createFileInput(rootDirectory, extensions);
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
	
	public static ProjectInputWithNameExtension[] createProjectInput(File projectDir, String[] extensions) {
		File[] directories = projectDir.listFiles(File::isDirectory);
		ProjectInputWithNameExtension[] input = new ProjectInputWithNameExtension[directories.length];
		
		for (int i=0; i<directories.length; i++) {
			if (directories[i].listFiles().length>0) {
				input[i] = new ProjectInputWithNameExtension(directories[i], extensions);
				System.out.println("Imported project "+directories[i].getPath());
			}
		}
		return input;
	}
	
}
