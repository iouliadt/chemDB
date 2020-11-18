//Class containing methods to calculate values using chemaxon Plugins
import java.io.IOException;
import java.sql.SQLException;
import chemaxon.marvin.calculations.HBDAPlugin;
import chemaxon.marvin.calculations.TPSAPlugin;
import chemaxon.marvin.calculations.TopologyAnalyserPlugin;
import chemaxon.marvin.calculations.logDPlugin;
import chemaxon.marvin.calculations.logPPlugin;
import chemaxon.marvin.plugin.PluginException;
import chemaxon.struc.Molecule;

public class Calculations {

	// method to limit doubles to 2 decimal places
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	// logD calculator method
	public static double logD(Molecule mol) throws SQLException, PluginException, IOException {

		// instantiate plugin
		logDPlugin pluginLogD = new logDPlugin();
		// set pH
		pluginLogD.setpH(7.4);
		// set the input molecule
		pluginLogD.setMolecule(mol);
		// run the calculation
		pluginLogD.run();
		// get result
		double logD = pluginLogD.getlogD();

		return round(logD, 3);

	}

	//logP
	public static double logP(Molecule mol) throws SQLException, PluginException, IOException {
		logPPlugin pluginlogP = new logPPlugin();
		// set logP calculation method
		pluginlogP.setlogPMethod(logPPlugin.METHOD_WEIGHTED);
		// set method weights
		pluginlogP.setWeightOfMethods(1, 2, 1, 0);
		// set parameters
		pluginlogP.setCloridIonConcentration(0.2);
		pluginlogP.setNaKIonConcentration(0.2);
		// set result types
		pluginlogP.setUserTypes("logPTrue");
		pluginlogP.setMolecule(mol);
		// run the calculation
		pluginlogP.run();
		// get the overall logP value
		double logp = pluginlogP.getlogPTrue();

		return round(logp, 3);
	}

	//Acceptor count with multiplicity
	public static int hbdaAcceptor(Molecule mol) throws SQLException, PluginException, IOException {
		// create plugin to calculate HBDA
		HBDAPlugin pluginHBDA = new HBDAPlugin();
		// set plugin parameters
		pluginHBDA.setDoublePrecision(2);
		// set PH to 7.4
		pluginHBDA.setpH(7.4);
		// set target molecule
		pluginHBDA.setMolecule(mol);
		// run the calculation
		pluginHBDA.run();
		// with multiplicity
		int molecularAcceptorCount = pluginHBDA.getAcceptorCount();

		return molecularAcceptorCount;
	}

	//Donor count with multiplicity
	public static int hbdaDonor(Molecule mol) throws SQLException, PluginException, IOException {

		// create plugin to calculate HBDA
		HBDAPlugin pluginHBDA = new HBDAPlugin();
		// set plugin parameters
		pluginHBDA.setDoublePrecision(2);
		pluginHBDA.setpHLower(2.0);
		pluginHBDA.setpHUpper(12.0);
		pluginHBDA.setpHStep(2.0);
		// set PH to 7.4
		pluginHBDA.setpH(7.4);
		// set target molecule
		pluginHBDA.setMolecule(mol);
		// run the calculation
		pluginHBDA.run();
		// with multiplicity
		int molecularDonorCount = pluginHBDA.getDonorCount();

		return molecularDonorCount;

	}

	//Polar surface area
	public static double tpsa(Molecule mol) throws SQLException, PluginException, IOException {

		TPSAPlugin pluginTPSA = new TPSAPlugin();
		// optional: take major microspecies at pH=7.4
		pluginTPSA.setpH(7.4);
		// set target molecule
		pluginTPSA.setMolecule(mol);
		// run the calculation
		pluginTPSA.run();
		// get result
		double area = pluginTPSA.getSurfaceArea();

		return round(area, 3);

	}

	//Ring count
	public static int topologyAnalyserRC(Molecule mol) throws SQLException, PluginException, IOException {

		// create plugin
		TopologyAnalyserPlugin pluginTopo = new TopologyAnalyserPlugin();
		// set target molecule
		pluginTopo.setMolecule(mol);
		// run the calculation
		pluginTopo.run();
		// get molecular results
		int ringCount = pluginTopo.getRingCount();
		// System.out.println("Ring count: " + ringCount);
		return ringCount;

	}

	//Rotatable bonds
	public static int topologyAnalyserRBC(Molecule mol) throws SQLException, PluginException, IOException {

		// create plugin
		TopologyAnalyserPlugin pluginTopo = new TopologyAnalyserPlugin();
		// set target molecule
		pluginTopo.setMolecule(mol);
		// run the calculation
		pluginTopo.run();
		// get molecular results
		int rotatableBondCount = pluginTopo.getRotatableBondCount();
		
		return rotatableBondCount;

	}

	// Aromatic Ring Count
	public static int topologyAnalyserARC(Molecule mol) throws SQLException, PluginException, IOException {

		// create plugin
		TopologyAnalyserPlugin pluginTopo = new TopologyAnalyserPlugin();
		// set target molecule
		pluginTopo.setMolecule(mol);
		// run the calculation
		pluginTopo.run();
		// get molecular results
		int fusedAromaticRingCount = pluginTopo.getFusedAromaticRingCount();
		return fusedAromaticRingCount;

	}
	
	//returns chemical formula
	public static String getChemicalFormula(Molecule mol) {
		String chemFormula=mol.getFormula();
		return chemFormula;
		}
}