//Centre Panel which consists of the Table
import javax.swing.*;
import java.awt.*;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CentrePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel pCentre;
	private Connector modelObject;
	private Controller controllerObject;
	private DataTableModel tableModel;
	private JTable table;
	private DefaultTableCellRenderer rightRenderer;

	public CentrePanel(Connector connector, Controller controller) {
		modelObject = connector;
		controllerObject = controller;

		//Centre panel for table
		pCentre = new JPanel();
		add("Center", pCentre);
		pCentre.setSize(800, 900);
		// Feedback text area
		pCentre.setLayout(new GridLayout(1, 1));

		//create table model
		tableModel = new DataTableModel(modelObject, controllerObject);

		//create table
		table = new JTable(tableModel.getTableModel());

		rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.LEFT);
		for (int i = 0; i<12; i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
		}
		//set renderer for images
		int imageViewIndex = table.convertColumnIndexToView(11); // view index of ImageIcon data
		table.getColumnModel().getColumn(imageViewIndex).setCellRenderer(new Renderer());


		//set row height and width for each column
		table.setRowHeight(100);
		table.setAutoCreateRowSorter(true);
		table.getColumnModel().getColumn(0).setPreferredWidth(500);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(5);
		table.getColumnModel().getColumn(3).setPreferredWidth(5);
		table.getColumnModel().getColumn(4).setPreferredWidth(5);
		table.getColumnModel().getColumn(5).setPreferredWidth(5);
		table.getColumnModel().getColumn(6).setPreferredWidth(5);
		table.getColumnModel().getColumn(7).setPreferredWidth(5);
		table.getColumnModel().getColumn(8).setPreferredWidth(5);
		table.getColumnModel().getColumn(9).setPreferredWidth(5);
		table.getColumnModel().getColumn(10).setPreferredWidth(25);

		//Scroll pane that provides a scrollable view of the component
		JScrollPane tablePane = new JScrollPane(table);
		pCentre.add(tablePane);
		tablePane.setSize(900, 1000);
		
	}

	// getter for Centre Panel
	public JPanel getPanel() {
		return this.pCentre;
	}

	//getter for tableModel
	public DataTableModel getTable() {
		return this.tableModel;
	}

	//Save table as Excel file
	public void exportExcelData() {
		
		FileOutputStream excelFos = null;
        XSSFWorkbook excelJTableExport = null;
        BufferedOutputStream excelBos = null;
        try {
            //Choosing Saving Location
            JFileChooser excelFileChooser = new JFileChooser("/Users/juliatsatsani/Desktop");
            //Dialog box title
            excelFileChooser.setDialogTitle("Save As ..");
            //Filter only xls, xlsx, xlsm files
            FileNameExtensionFilter fnef = new FileNameExtensionFilter("Files", "xls", "xlsx", "xlsm");
            //Setting extension for selected file names
            excelFileChooser.setFileFilter(fnef);
            int chooser = excelFileChooser.showSaveDialog(null);
            //Check if save button has been clicked
            if (chooser == JFileChooser.APPROVE_OPTION) {
                //If button is clicked execute this code
                excelJTableExport = new XSSFWorkbook();
                XSSFSheet excelSheet = excelJTableExport.createSheet("Jtable Export");
                //Loop through the table columns and rows to get its values
                for (int i = 0; i < table.getRowCount(); i++) {
                    XSSFRow excelRow = excelSheet.createRow(i);
                    for (int j = 0; j < table.getColumnCount()-1; j++) {
                        XSSFCell excelCell = excelRow.createCell(j);


                        excelCell.setCellValue(table.getValueAt(i, j).toString());

                    }
                }
                excelFos = new FileOutputStream(excelFileChooser.getSelectedFile() + ".xlsx");
                excelBos = new BufferedOutputStream(excelFos);
                excelJTableExport.write(excelBos);
                JOptionPane.showMessageDialog(null, "Exported Successfully");
            }
 
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            try {
                if (excelFos != null) {
                    excelFos.close();
                }
                if (excelBos != null) {
                    excelBos.close();
                }
                if (excelJTableExport != null) {
                    excelJTableExport.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }
}


