package part02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 * class to test reading csv file and create Vending Machine instances from this
 * 
 * @author QUB 40210913 David Montgomery
 */
public class CsvReadTest {

	private static String csvInPath = "src/Vending Machines QUB csv.csv";

	public static void main(String[] args) {

		ArrayList<VendingMachine> myVendingMachines = new ArrayList<VendingMachine>();
		myVendingMachines.addAll(importVmData(csvInPath, true));
		for (VendingMachine eachVendingMachine : myVendingMachines) {
			System.out.println(eachVendingMachine);
		}

	}

	/**
	 * //Method takes the file path of the .csv to be read & a Boolean to indicate
	 * if it has a header.
	 * 
	 * @param csvInPath file path of csv
	 * @param hasHeader whether the csv has a header or not
	 * @return
	 */
	private static Collection<? extends VendingMachine> importVmData(String csvInPath, boolean hasHeader) {

		// Vending Machine objects will be stored here and returned to the calling
		// method
		ArrayList<VendingMachine> myVendingMachines = new ArrayList<VendingMachine>();
		try {
			File myFile = new File(csvInPath);
			Scanner mySc = new Scanner(myFile);
			if (hasHeader) {
				mySc.nextLine();
			}
			while (mySc.hasNextLine()) {
				String record = mySc.nextLine();
				String[] parts = record.split(",");

				String Owner = parts[0].trim();
				int maxItems = Integer.parseInt(parts[1].trim());

				// Add a new Vending Machine to the array list to be returned.
				myVendingMachines.add(new VendingMachine(Owner, maxItems));
			}

			// loop through each instance of VendingMachine in the list and get system info on each one
			
			for (VendingMachine eachVendingMachine : myVendingMachines) {
				System.out.println(eachVendingMachine);
			}

			mySc.close();
			return myVendingMachines;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// A null object is returned in the event of any errors.
			return null;
		}
	}
}
