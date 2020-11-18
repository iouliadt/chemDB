//Class where table contents are created
import javax.swing.table.DefaultTableModel;

public class DataTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	private Connector modelObject;
	private Controller controllerObject;
	
	// table's column names
	private String[] columnNames = new String[] { "Compound", "Molecular weight", "LogP", "LogD",
			"Polar Surface Area (pH= 7.4)", "Acceptor Count", "Donor Count", "Ring Count", "Fused Aromatic Rings",
			"Rotatable Bonds", "Chemical Formula","Icon" };
	private DefaultTableModel tableModel;
	private Object[][] tableData;

	public DataTableModel(Connector connector, Controller controller) {

		modelObject = connector;
		controllerObject = controller;
		tableData = modelObject.getNoFilterTable();
		tableModel = new DefaultTableModel(tableData, columnNames) 
		
	    //for sorting; column variable types
		{
			Class[] types = { String.class, Double.class, Double.class, Double.class, Double.class, Integer.class,
					Integer.class, Integer.class, Integer.class, Integer.class, String.class, String.class };
			boolean[] canEdit = { true, true, true, true, true, true, true, true, true, true,  true, false };

			@Override
			public Class getColumnClass(int columnIndex) {
				return this.types[columnIndex];
			}

			public boolean isCellEditable(int columnIndex) {
				return this.canEdit[columnIndex];
			}
		};

	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	// clear table
	public void clearTable() {
		//clear existing table
		this.tableModel.setRowCount(0);
	}

	//update table
	public void updateTable(Object[][] tableDisplay) {
		//add new table for each row
		for (int i = 0; i < tableDisplay.length; i++) {
			this.tableModel.addRow(tableDisplay[i]);
		}
	}

	//getter for tablemodel
	public DefaultTableModel getTableModel() {
		return this.tableModel;
	}

}