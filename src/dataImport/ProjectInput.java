package dataImport;

public class ProjectInput {
	private FileInput[] fileInput;
	
	public ProjectInput (FileInput[] fileInput) {
		this.fileInput = fileInput;
	}
	
	public FileInput[] getInput() {
		return fileInput;
	}
}
