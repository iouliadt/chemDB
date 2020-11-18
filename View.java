//GUI and its components
import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private Menu menu;
	private NorthPanel northPanel;
	private CentrePanel centrePanel;
	private Controller controllerObject;
	private Connector modelObject;

	public View(Connector connector, Controller controller) {

		controllerObject = controller;
		modelObject = connector;

		initialComponents();
		layoutComponents();

	}

	//Size, location, name
	private void initialComponents() {

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1400, 1000);
		setLocation(20, 20);
		setTitle("Chemical Database");
		setLayout(new BorderLayout());

	}


	//add layout components/panels to the  GUI
	private void layoutComponents() {

		menu = new Menu(controllerObject);
		this.setJMenuBar(menu.getMenuBar());
		northPanel = new NorthPanel(controllerObject);
		this.add(northPanel.getPanel(), BorderLayout.NORTH);
		centrePanel = new CentrePanel(modelObject, controllerObject);
		this.add(centrePanel.getPanel(), BorderLayout.CENTER);
//		southPanel = new SouthPanel(controllerObject);
//		this.add(southPanel.getPanel(), BorderLayout.SOUTH);

	}

	// getter for northPanel
	public NorthPanel getNorthPanel() {
		return this.northPanel;
	}

	// getter for centrePanel
	public CentrePanel getCentrePanel() {
		return this.centrePanel;
	}

	// getter for menu
	public Menu getMenu() {
		return this.menu;
	}

	public void updateTitle(String s) {
		this.setTitle("Chemical Database - " + s);
	}
}
