/* Class containing methods that filter the database on various criteria.
Each method takes as argument the non-filtered table (2D Array) and returns a filtered one*/

public class Filters {

	//counter loops go through each row and column to populate new table, if all the conditions are true,
	//the molecule is added in the lipinski table
	public static Object[][] lipinskisTable(Object[][] table) {

		int rowCounter = 0;
		Object[][] lipinskiTemporary = new Object[100][12];
		for (int i = 0; i < 100; i++) {
			if ((Double) table[i][2] <= 5.0 && (Integer) table[i][6] <= 5 && (Integer) table[i][5] <= 10
					&& (Double) table[i][1] <= 500.0) {
				lipinskiTemporary[rowCounter] = table[i];
				rowCounter++;
			}
		}

		Object[][] lipinski = new Object[rowCounter][12];
		for (int i = 0; i < lipinski.length; i++) {
			lipinski[i] = lipinskiTemporary[i];

		}
		return lipinski;
	}

	public static Object[][] leadTable(Object[][] table) {

		int rowCounter = 0;
		Object[][] leadLikenessTemporary = new Object[100][12];
		for (int i = 0; i < 100; i++) {
			if ((Double) table[i][3] <= 4.0 && (Double) table[i][3] >= -4.0 && (Integer) table[i][6] <= 5
					&& (Integer) table[i][5] <= 8 && (Double) table[i][1] <= 450.0 && (Integer) table[i][7] <= 4
					&& (Integer) table[i][9] <= 10) {
				leadLikenessTemporary[rowCounter] = table[i];
				rowCounter++;
			}
		}

		Object[][] leadLikeness = new Object[rowCounter][12];
		for (int i = 0; i < leadLikeness.length; i++) {
			leadLikeness[i] = leadLikenessTemporary[i];
		}
		return leadLikeness;

	}

	public static Object[][] bioavailabilityTable(Object[][] table) {

		int rowCounter = 0;
		Object[][] bioavailabilityTemporary = new Object[100][12];
		for (int i = 0; i < 100; i++) {
			if ((Double) table[i][2] <= 5 && (Integer) table[i][6] <= 5 && (Integer) table[i][5] <= 10
					&& (Double) table[i][1] <= 500.0 && (Integer) table[i][8] <= 5 && (Integer) table[i][9] <= 10
					&& (Double) table[i][4] <= 200) {
				bioavailabilityTemporary[rowCounter] = table[i];
				rowCounter++;
			}
		}

		Object[][] bioavailability = new Object[rowCounter][12];
		for (int i = 0; i < bioavailability.length; i++) {
			bioavailability[i] = bioavailabilityTemporary[i];
		}
		return bioavailability;
	}

	
	
	// Custom methods with "flexible" criteria, user input parameters correspond to user slider manipulation
	
	public static Object[][] customLipinskisTable(Object[][] table, Double logP, Integer hbdaD, Integer hbdaA,
			Double molWeight) {

		int rowCounter = 0;
		Object[][] lipinskiTemporary = new Object[100][12];
		for (int i = 0; i < 100; i++) {
			if ((Double) table[i][2] <= logP && (Integer) table[i][6] <= hbdaD && (Integer) table[i][5] <= hbdaA
					&& (Double) table[i][1] <= molWeight) {
				lipinskiTemporary[rowCounter] = table[i];
				rowCounter++;
			}
		}

		Object[][] lipinski = new Object[rowCounter][12];
		for (int i = 0; i < lipinski.length; i++) {
			lipinski[i] = lipinskiTemporary[i];
		}
		return lipinski;
	}

	public static Object[][] customLeadLikenessTable(Object[][] table, double inputLogD, int inputhbdaD, int inputhbdaA,
			double inputmolWeight, int inputRingCount, int inputBondCount) {

		int rowCounter = 0;
		Object[][] LLTemporary = new Object[100][12];
		for (int i = 0; i < 100; i++) {
			if ((Double) table[i][3] <= inputLogD && (Double) table[i][3] >= -inputLogD
					&& (Integer) table[i][6] <= inputhbdaD && (Integer) table[i][5] <= inputhbdaA
					&& (Double) table[i][1] <= inputmolWeight && (Integer) table[i][7] <= inputRingCount
					&& (Integer) table[i][9] <= inputBondCount) {
				LLTemporary[rowCounter] = table[i];
				rowCounter++;
			}
		}
		Object[][] LL = new Object[rowCounter][12];
		for (int i = 0; i < LL.length; i++) {
			LL[i] = LLTemporary[i];
		}
		return LL;
	}

	public static Object[][] customBioavailabilityTable(Object[][] table, double inputMolWeight, double inputLogP,
			double inputPSA, int inputhbdaD, int inputhbdaA, int inputBondCount, int inputFARingCount) {
		int rowCounter = 0;
		Object[][] bioavailabilityTemporary = new Object[100][12];
		for (int i = 0; i < 100; i++) {
			if ((Double) table[i][2] <= inputLogP && (Integer) table[i][6] <= inputhbdaD
					&& (Integer) table[i][5] <= inputhbdaA && (Double) table[i][1] <= inputMolWeight
					&& (Integer) table[i][8] <= inputFARingCount && (Integer) table[i][9] <= inputBondCount
					&& (Double) table[i][4] <= inputPSA) {
				bioavailabilityTemporary[rowCounter] = table[i];
				rowCounter++;
			}
		}
		Object[][] bioavailability = new Object[rowCounter][12];
		for (int i = 0; i < bioavailability.length; i++) {
			bioavailability[i] = bioavailabilityTemporary[i];
		}
		return bioavailability;
	}

	public static Object[][] customFiltering(Object[][] table, String s) {

		int rowCounter = 0;
		Object[][] customFilteringTemporary = new Object[table.length][12];
		for (int i = 0; i < table.length; i++) {
			if (((String) table[i][0]).contains(s)) {
				customFilteringTemporary[rowCounter] = table[i];
				rowCounter++;
			}
		}

		Object[][] customFiltering = new Object[rowCounter][12];
		for (int i = 0; i < customFiltering.length; i++) {
			customFiltering[i] = customFilteringTemporary[i];
		}
		return customFiltering;

	}

}
