package experiments;

import java.io.IOException;
import java.util.ArrayList;

import auth.eng.textManager.WordModel;
import dataImport.ProjectInput;

public abstract class Experiment {
	
	public abstract void test(ProjectInput project, WordModel wordModel, String[] Extensions) throws IOException;
	
	protected String arrayListToCSVString(ArrayList<String> strings){
		String csvString = new String();
		for (String string:strings) {
			csvString = csvString + string + ", ";
		}
		return csvString;
		
	}
}
