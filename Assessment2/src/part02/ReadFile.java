package part02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFile {

	public static void main(String[] args) {
		
		String csvFilePath = "cw/Vending Machines QUB csv.csv⁩";
		// read this java file into a string
		String content = readFile(csvFilePath);
		
		// print the content out
		System.out.print(content);
	}
	
	
	/**
	 * To read the content of a file into a string
	 * @param fname the file name
	 * @return the string representing the file content
	 */
	public static String readFile(String fname) {
		String content = null;
		try {
	        content = new String(Files.readAllBytes(Paths.get(fname)));
		} catch (IOException e) {
			System.out.println("Fail to read a file");
		}
		
		return content;
	}
	
}
