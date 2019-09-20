package dataImport;

import java.io.File;
import org.apache.commons.io.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;


public class FileInputWithNameExtension extends FileInput {

	public static FileInputWithNameExtension[] createFileInput(File rootDir, String[] extensions) {
		Collection<File> files= FileUtils.listFiles(rootDir, extensions , true);
		File[] filesArray = files.toArray(new File[files.size()]);

		int size = filesArray.length;

		FileInputWithNameExtension[] inputArray = new FileInputWithNameExtension[size];

		for (int i=0; i<size; i++) {	
			inputArray[i] = new FileInputWithNameExtension(filesArray[i].getName(),readFile(filesArray[i]));
		}

		System.out.println(rootDir+" : found "+inputArray.length+" project files");
		return inputArray;
	}

	private static String readFile(File file) {
		try {
			return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public FileInputWithNameExtension(String fileName, String fileCode) {
		super(fileName,fileCode);
	}

	public String getFileName() {
		return fileName;
	}
	
	public String getFileCode() {
		return fileCode;
	}
	
}









