package dataImport;

import java.io.File;
import org.apache.commons.io.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;


public class fileInput {
	public String fileName;
	public String fileCode;

	public static fileInput[] createFileInput(File rootDir) {
			String extensions[] = {"java" , "py"};
			Collection<File> files= FileUtils.listFiles(rootDir, extensions , true);
			File[] filesArray = files.toArray(new File[files.size()]);

			int size = filesArray.length;

			fileInput[] inputArray = new fileInput[size];

			for (int i=0; i<size; i++) {	
				inputArray[i] = new fileInput();
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

	public fileInput() {
		fileName="";
		fileCode="";
	}

}









