import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Image;
import chemaxon.formats.MolExporter;
import chemaxon.marvin.plugin.PluginException;
import chemaxon.struc.Molecule;

public class CompoundData {
	private Molecule mol;
	private MoleculeMysql molecule;
	private Object[] data;

	
	//Icon & data creation to populate table (to be used in Connector class)
	public CompoundData(Integer i) throws SQLException, IOException, PluginException {
		
		molecule = new MoleculeMysql();
		mol = molecule.getMolecule(getCompoundIds().get(i));
		
		byte[] d3 = (byte[]) MolExporter.exportToObject(mol, "png:w600,h600,b32,#00ffff00");
		ByteArrayInputStream bis = new ByteArrayInputStream(d3);
		BufferedImage bImage2 = ImageIO.read(bis);
		
		ImageIO.write(bImage2, "png", new File("/Users/juliatsatsani/Desktop/mol.png"));
		ImageIcon icon = new ImageIcon("/Users/juliatsatsani/Desktop/mol.png");
		Image image = icon.getImage(); // transform it to image to allow good resizing
		Image newimg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // scale it the smooth way  
		icon = new ImageIcon(newimg);
		
		data = new Object[] { getCompoundIds().get(i), getMolecularWeight(getCompoundIds().get(i)),
				Calculations.logP(mol), Calculations.logD(mol), Calculations.tpsa(mol), Calculations.hbdaAcceptor(mol),
				Calculations.hbdaDonor(mol), Calculations.topologyAnalyserRC(mol),
				Calculations.topologyAnalyserARC(mol), Calculations.topologyAnalyserRBC(mol), Calculations.getChemicalFormula(mol), icon };
		
	}

	//getter for CompoundData
	public Object[] getCompoundData() throws SQLException, IOException, PluginException {
		
		return this.data;
		
	}
    // To retrieve compound data from mySQL - connection methods where mySLQ commands are used
	public ArrayList<String> getCompoundIds() throws SQLException {

		Connection myConn = DriverManager.getConnection("jdbc:mysql://root@localhost:3306/molecule8", "root@",
				"!password");
		Statement myStmt = myConn.createStatement();
		ResultSet myRs1 = myStmt.executeQuery("select CompoundID from compound");
		ArrayList<String> compoundIds = new ArrayList<String>();
		while (myRs1.next()) {
			compoundIds.add(myRs1.getString("CompoundID"));
		}
		return compoundIds;
	}

	public double getMolecularWeight(String name) throws SQLException {

		Connection myConn = DriverManager.getConnection("jdbc:mysql://root@localhost:3306/molecule8", "root@",
				"!password");
		Statement myStmt = myConn.createStatement();
		double molWeight = 0;
		ResultSet myRs3 = myStmt.executeQuery("select molecularWeight from compound where CompoundID = '" + name + "'");
		while (myRs3.next()) {
			molWeight = myRs3.getDouble("molecularweight");
		}
		return Calculations.round(molWeight, 3);
	}

}
