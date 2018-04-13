package dataImport;

import java.io.File;
import org.apache.commons.io.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;


public class FileInput {
	public String fileName;
	public String fileCode;

	public static FileInput[] createFileInput(File rootDir) {
		String extensions[] = {"java" , "py"};
		Collection<File> files= FileUtils.listFiles(rootDir, extensions , true);
		File[] filesArray = files.toArray(new File[files.size()]);

		int size = filesArray.length;

		FileInput[] inputArray = new FileInput[size];

		for (int i=0; i<size; i++) {	
			inputArray[i] = new FileInput();
			inputArray[i].fileName = filesArray[i].getName();
			inputArray[i].fileCode = readFile(filesArray[i]);

		}
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

	public FileInput() {
		fileName="";
		fileCode="";
	}

}









