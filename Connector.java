//Connector class; initial non-filtered table is created here, containing information for compounds obtained from mySQL
import java.io.IOException;
import java.sql.*;
import chemaxon.marvin.plugin.PluginException;

public class Connector {
	private CompoundData compound;
	private Object[][] noFilter;	

	//connector constructor
	public Connector() throws SQLException, IOException, PluginException {
		
		noFilter = new Object[100][12];
		
		
		//populate table with data from CompoundData class
		for (int i = 0; i < 100; i++) {
			compound = new CompoundData(i);
			noFilter[i] = compound.getCompoundData();
			
		}
	}
	
	//getter for no filter table
	public Object[][] getNoFilterTable(){
		return this.noFilter;
	}
		
}
