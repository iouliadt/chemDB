import chemaxon.struc.PeriodicSystem;
import java.io.IOException;
import java.sql.*;
import chemaxon.formats.MolExporter;
import chemaxon.formats.MolImporter;
import chemaxon.struc.MolAtom;
import chemaxon.struc.MolBond;
import chemaxon.struc.Molecule;

public class MoleculeMysql {

	private  String compoundName;
	private  Molecule mol;
	private  Molecule mol1;
	
	public MoleculeMysql() {
		
		mol = new Molecule();
		mol1 = new Molecule();
		
	}

	//connect to mySQL
	public  Molecule getMolecule(String compoundId) throws SQLException, IOException {

		compoundName = compoundId;

		Connection con = DriverManager.getConnection("jdbc:mysql://root@localhost:3306/molecule8", "root@",
				"!password");
		Statement myStmt = con.createStatement();

		ResultSet myRs = myStmt.executeQuery("select * from atom where CompoundId =" + "\"" + compoundName + "\"");
		while (myRs.next()) {
			int atomicNumber = PeriodicSystem.findAtomicNumber(myRs.getString("AtomType"));
			double x = myRs.getDouble("xcoord");
			double y = myRs.getDouble("ycoord");
			double z = myRs.getDouble("zcoord");

			MolAtom atom = new MolAtom(atomicNumber, x, y, z);
			mol.add(atom);
		}

		// 3. Execute SQL query
		String bondQuery = "select * from bond where CompoundID ='" + compoundName + "'";
		ResultSet myRs2 = myStmt.executeQuery(bondQuery);

		MolAtom[] atomList = mol.getAtomArray();
		// 4. Process the result set
		while (myRs2.next()) {
			int bondO = myRs2.getInt("BondOrder");
			int atom1 = myRs2.getInt("Atom1Number");
			int atom2 = myRs2.getInt("Atom2Number");

			MolBond bond = new MolBond(atomList[atom1 - 1], atomList[atom2 - 1], bondO);
			mol.add(bond);
		}

		String smileString = (String) MolExporter.exportToObject(mol, "smiles:a-H");

		mol1 = MolImporter.importMol(smileString);
		return mol1;

	}
	
}
