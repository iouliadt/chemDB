//Main method where Connector and Controller are created
import java.io.IOException;
import java.sql.SQLException;
import chemaxon.marvin.plugin.PluginException;

public class Main {

	public static void main(String[] args) throws SQLException, PluginException, IOException {
		
		//model creation
		Connector connector = new Connector();
		//controller
		@SuppressWarnings("unused")
		Controller controller = new Controller(connector);
		 
	}

}
