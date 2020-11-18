//Controller class which is used for action events, user interaction and display updates
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import chemaxon.marvin.plugin.PluginException;

import java.awt.Color;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller implements ActionListener, KeyListener, ChangeListener, MouseListener, FocusListener {
	private View viewObject;
	private Connector modelObject;
	private AtomicInteger selectionStatus;

	public Controller(Connector connector) throws SQLException, IOException, PluginException {

		modelObject = connector;
		//create view window
		viewObject = new View(connector, this);
		viewObject.setVisible(true);

		selectionStatus = new AtomicInteger(0);

	}

	//for Menu bar interaction
	public void actionPerformed(ActionEvent e) {
			
		if (e.getSource() instanceof JMenuItem) {
			if (e.getSource() == viewObject.getMenu().getSave()) { //if save button is pressed, 
				viewObject.getCentrePanel().exportExcelData(); // export excel method is called which saves table to .xlsx file
			} else if(e.getSource() == viewObject.getMenu().getNoFilter()) { //if no filter option is chose, no filter table appears
				viewObject.updateTitle("No Filter");                     
				viewObject.getNorthPanel().disableSliders();
				selectionStatus.set(0);   //status 0 stands for no filters
				if ("Search Compound".equals(viewObject.getMenu().getCompoundID().getText())) {
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable().updateTable(modelObject.getNoFilterTable());
				} else {
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable().updateTable(Filters.customFiltering(
							modelObject.getNoFilterTable(), viewObject.getMenu().getCompoundID().getText()));
				}
			}else if(e.getSource() == viewObject.getMenu().getLipinskiFilter()) {
				viewObject.updateTitle("Lipinski's Rule of Five");
					viewObject.getNorthPanel().disableSliders();
					viewObject.getNorthPanel().enableCustomLipinskisDisabledSliders();
					selectionStatus.set(1);// status 1 is default lipinski
					if ("Search Compound".equals(viewObject.getMenu().getCompoundID().getText())) {
						viewObject.getCentrePanel().getTable().clearTable();
						viewObject.getCentrePanel().getTable()
								.updateTable(Filters.lipinskisTable(modelObject.getNoFilterTable()));
					} else {
						viewObject.getCentrePanel().getTable().clearTable();
						viewObject.getCentrePanel().getTable()
								.updateTable(Filters.customFiltering(Filters.lipinskisTable(modelObject.getNoFilterTable()),
										viewObject.getMenu().getCompoundID().getText()));
				}
			} else if (e.getSource() == viewObject.getMenu().getLLFilter()) {
				viewObject.updateTitle("Lead-Likeness");
				viewObject.getNorthPanel().disableSliders();
				viewObject.getNorthPanel().enableCustomLeadLikenessDisabledSliders();
				selectionStatus.set(2);// default lead-likeness etc.
				if ("Search Compound".equals(viewObject.getMenu().getCompoundID().getText())) {
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.leadTable(modelObject.getNoFilterTable()));
				} else {
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customFiltering(Filters.leadTable(modelObject.getNoFilterTable()),
									viewObject.getMenu().getCompoundID().getText()));
				}
			} else if (e.getSource() == viewObject.getMenu().getBioFilter()) {
				viewObject.updateTitle("Bioavailability");
				viewObject.getNorthPanel().disableSliders();
				viewObject.getNorthPanel().enableCustomBioavailabilityDisabledSliders();
				selectionStatus.set(3);
				if ("Search Compound".equals(viewObject.getMenu().getCompoundID().getText())) {
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.bioavailabilityTable(modelObject.getNoFilterTable()));
				} else {
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customFiltering(
									Filters.bioavailabilityTable(modelObject.getNoFilterTable()),
									viewObject.getMenu().getCompoundID().getText()));
				}
			} else if (e.getSource() == viewObject.getMenu().getCustomLipinskiFilter()) {
				viewObject.updateTitle("Custom Lipinski's Rule of Five");
				viewObject.getNorthPanel().disableSliders();
				selectionStatus.set(4);
				viewObject.getNorthPanel().enableCustomLipinskisSliders();
				if ("Search Compound".equals(viewObject.getMenu().getCompoundID().getText())) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable().updateTable(Filters.customLipinskisTable(
							modelObject.getNoFilterTable(), inputLogP, inputDonor, inputAcceptor, inputMolWeight));
				} else {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customFiltering(
									Filters.customLipinskisTable(modelObject.getNoFilterTable(), inputLogP, inputDonor,
											inputAcceptor, inputMolWeight),
									viewObject.getMenu().getCompoundID().getText()));
				}
			} else if (e.getSource() == viewObject.getMenu().getCustomLL()) {
				viewObject.updateTitle("Custom Lead-Likeness");
				viewObject.getNorthPanel().disableSliders();
				selectionStatus.set(5);
				viewObject.getNorthPanel().enableCustomLeadLikenessSliders();
				if ("Search Compound".equals(viewObject.getMenu().getCompoundID().getText())) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogD = viewObject.getNorthPanel().getLogDSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int ringCount = viewObject.getNorthPanel().getRingCountSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customLeadLikenessTable(modelObject.getNoFilterTable(), inputLogD,
									inputDonor, inputAcceptor, inputMolWeight, ringCount, bondCount));
				} else {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogD = viewObject.getNorthPanel().getLogDSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int ringCount = viewObject.getNorthPanel().getRingCountSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customFiltering(
									Filters.customLeadLikenessTable(modelObject.getNoFilterTable(), inputLogD,
											inputDonor, inputAcceptor, inputMolWeight, ringCount, bondCount),
									viewObject.getMenu().getCompoundID().getText()));
				}
			} else if (e.getSource() == viewObject.getMenu().getCustomBio()) {
				viewObject.updateTitle("Custom Bioavailability");
				viewObject.getNorthPanel().disableSliders();
				selectionStatus.set(6);
				viewObject.getNorthPanel().enableCustomBioavailabilitySliders();
				if ("Search Compound".equals(viewObject.getMenu().getCompoundID().getText())) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int FARingCount = viewObject.getNorthPanel().getFARingSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					double area = viewObject.getNorthPanel().getAreaSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customBioavailabilityTable(modelObject.getNoFilterTable(), inputMolWeight,
									inputLogP, area, inputDonor, inputAcceptor, bondCount, FARingCount));
				} else {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int FARingCount = viewObject.getNorthPanel().getFARingSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					double area = viewObject.getNorthPanel().getAreaSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customFiltering(
									Filters.customBioavailabilityTable(modelObject.getNoFilterTable(), inputMolWeight,
											inputLogP, area, inputDonor, inputAcceptor, bondCount, FARingCount),
									viewObject.getMenu().getCompoundID().getText()));
				}
			}
		}
	}

	//For Search bar
	@Override
	public void keyReleased(KeyEvent e) { // when the user stops typing in the search bar,action takes place
		
		if (e.getSource() == viewObject.getMenu().getCompoundID()) {
			if (selectionStatus.get() == 0) {  // if there is nothing in the search bar, the table is cleared
				viewObject.getCentrePanel().getTable().clearTable();
				viewObject.getCentrePanel().getTable()
				// update table with custom filtering method 
						.updateTable(Filters.customFiltering(modelObject.getNoFilterTable(), viewObject.getMenu().getCompoundID().getText())); 
			} else if (selectionStatus.get() == 1) {
				viewObject.getCentrePanel().getTable().clearTable();
				viewObject.getCentrePanel().getTable().updateTable(
						Filters.customFiltering(Filters.lipinskisTable(modelObject.getNoFilterTable()), viewObject.getMenu().getCompoundID().getText()));
			} else if (selectionStatus.get() == 2) {
				viewObject.getCentrePanel().getTable().clearTable();
				viewObject.getCentrePanel().getTable().updateTable(
						Filters.customFiltering(Filters.leadTable(modelObject.getNoFilterTable()), viewObject.getMenu().getCompoundID().getText()));
			} else if (selectionStatus.get() == 3) {
				viewObject.getCentrePanel().getTable().clearTable();
				viewObject.getCentrePanel().getTable().updateTable(
						Filters.customFiltering(Filters.bioavailabilityTable(modelObject.getNoFilterTable()), viewObject.getMenu().getCompoundID().getText()));
			} else if (selectionStatus.get() == 4) {
				viewObject.getCentrePanel().getTable().clearTable();
				double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
				double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
				int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
				int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
				viewObject.getCentrePanel().getTable().clearTable();
				viewObject.getCentrePanel().getTable()
						.updateTable(Filters.customFiltering(
								Filters.customLipinskisTable(modelObject.getNoFilterTable(), inputLogP, inputDonor,
										inputAcceptor, inputMolWeight),
								viewObject.getMenu().getCompoundID().getText()));
			} else if (selectionStatus.get() == 5) {
				double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
				double inputLogD = viewObject.getNorthPanel().getLogDSlider().getCurrentValue();
				int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
				int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
				int ringCount = viewObject.getNorthPanel().getRingCountSlider().getCurrentValue();
				int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
				viewObject.getCentrePanel().getTable().clearTable();
				viewObject.getCentrePanel().getTable()
						.updateTable(Filters.customFiltering(
								Filters.customLeadLikenessTable(modelObject.getNoFilterTable(), inputLogD, inputDonor,
										inputAcceptor, inputMolWeight, ringCount, bondCount),
								viewObject.getMenu().getCompoundID().getText()));
			} else if (selectionStatus.get() == 6) {
				double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
				double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
				int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
				int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
				int FARingCount = viewObject.getNorthPanel().getFARingSlider().getCurrentValue();
				int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
				double area = viewObject.getNorthPanel().getAreaSlider().getCurrentValue();
				viewObject.getCentrePanel().getTable().clearTable();
				viewObject.getCentrePanel().getTable()
						.updateTable(Filters.customFiltering(
								Filters.customBioavailabilityTable(modelObject.getNoFilterTable(), inputMolWeight,
										inputLogP, area, inputDonor, inputAcceptor, bondCount, FARingCount),
								viewObject.getMenu().getCompoundID().getText()));
			}
		}
	}

	//For sliders 
	@Override
	public void stateChanged(ChangeEvent e) { // to update slider text
		if (e.getSource() == viewObject.getNorthPanel().getMolWeightSlider().getSlider()) {
			viewObject.getNorthPanel().getMolWeightSlider().updateText();
		} else if (e.getSource() == viewObject.getNorthPanel().getLogPSlider().getSlider()) {
			viewObject.getNorthPanel().getLogPSlider().updateText();
		} else if (e.getSource() == viewObject.getNorthPanel().getLogDSlider().getSlider()) {
			viewObject.getNorthPanel().getLogDSlider().updateText();
		} else if (e.getSource() == viewObject.getNorthPanel().getAreaSlider().getSlider()) {
			viewObject.getNorthPanel().getAreaSlider().updateText();
		} else if (e.getSource() == viewObject.getNorthPanel().getHbdaASlider().getSlider()) {
			viewObject.getNorthPanel().getHbdaASlider().updateText();
		} else if (e.getSource() == viewObject.getNorthPanel().getHbdaDSlider().getSlider()) {
			viewObject.getNorthPanel().getHbdaDSlider().updateText();
		} else if (e.getSource() == viewObject.getNorthPanel().getRingCountSlider().getSlider()) {
			viewObject.getNorthPanel().getRingCountSlider().updateText();
		} else if (e.getSource() == viewObject.getNorthPanel().getFARingSlider().getSlider()) {
			viewObject.getNorthPanel().getFARingSlider().updateText();
		} else if (e.getSource() == viewObject.getNorthPanel().getBondCountSlider().getSlider()) {
			viewObject.getNorthPanel().getBondCountSlider().updateText();
		}
	}

	//For Sliders
	@Override
	public void mouseReleased(MouseEvent e) {// once the user releases the mouse from the slider, filters are applied
		if (e.getSource() == viewObject.getNorthPanel().getMolWeightSlider().getSlider()) {
			if ("Search Compound".equals(viewObject.getMenu().getCompoundID().getText())) {
				if (selectionStatus.get() == 4) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable().updateTable(Filters.customLipinskisTable(
							modelObject.getNoFilterTable(), inputLogP, inputDonor, inputAcceptor, inputMolWeight));
				} else if (selectionStatus.get() == 5) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogD = viewObject.getNorthPanel().getLogDSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int ringCount = viewObject.getNorthPanel().getRingCountSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customLeadLikenessTable(modelObject.getNoFilterTable(), inputLogD,
									inputDonor, inputAcceptor, inputMolWeight, ringCount, bondCount));
				} else if (selectionStatus.get() == 6) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int FARingCount = viewObject.getNorthPanel().getFARingSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					double area = viewObject.getNorthPanel().getAreaSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customBioavailabilityTable(modelObject.getNoFilterTable(), inputMolWeight,
									inputLogP, area, inputDonor, inputAcceptor, bondCount, FARingCount));
				}
			} else {
				if (selectionStatus.get() == 4) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customFiltering(
									Filters.customLipinskisTable(modelObject.getNoFilterTable(), inputLogP, inputDonor,
											inputAcceptor, inputMolWeight),
									viewObject.getMenu().getCompoundID().getText()));
				} else if (selectionStatus.get() == 5) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogD = viewObject.getNorthPanel().getLogDSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int ringCount = viewObject.getNorthPanel().getRingCountSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customFiltering(
									Filters.customLeadLikenessTable(modelObject.getNoFilterTable(), inputLogD,
											inputDonor, inputAcceptor, inputMolWeight, ringCount, bondCount),
									viewObject.getMenu().getCompoundID().getText()));
				} else if (selectionStatus.get() == 6) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int FARingCount = viewObject.getNorthPanel().getFARingSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					double area = viewObject.getNorthPanel().getAreaSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customFiltering(
									Filters.customBioavailabilityTable(modelObject.getNoFilterTable(), inputMolWeight,
											inputLogP, area, inputDonor, inputAcceptor, bondCount, FARingCount),
									viewObject.getMenu().getCompoundID().getText()));
				}
			}
		} else if (e.getSource() == viewObject.getNorthPanel().getLogPSlider().getSlider()) {
			if ("Search Compound".equals(viewObject.getMenu().getCompoundID().getText())) {
				if (selectionStatus.get() == 4) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable().updateTable(Filters.customLipinskisTable(
							modelObject.getNoFilterTable(), inputLogP, inputDonor, inputAcceptor, inputMolWeight));
				} else if (selectionStatus.get() == 5) {
				} else if (selectionStatus.get() == 6) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int FARingCount = viewObject.getNorthPanel().getFARingSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					double area = viewObject.getNorthPanel().getAreaSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customBioavailabilityTable(modelObject.getNoFilterTable(), inputMolWeight,
									inputLogP, area, inputDonor, inputAcceptor, bondCount, FARingCount));
				}
			} else {
				if (selectionStatus.get() == 4) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customFiltering(
									Filters.customLipinskisTable(modelObject.getNoFilterTable(), inputLogP, inputDonor,
											inputAcceptor, inputMolWeight),
									viewObject.getMenu().getCompoundID().getText()));
				} else if (selectionStatus.get() == 5) {
				} else if (selectionStatus.get() == 6) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int FARingCount = viewObject.getNorthPanel().getFARingSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					double area = viewObject.getNorthPanel().getAreaSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customFiltering(
									Filters.customBioavailabilityTable(modelObject.getNoFilterTable(), inputMolWeight,
											inputLogP, area, inputDonor, inputAcceptor, bondCount, FARingCount),
									viewObject.getMenu().getCompoundID().getText()));
				}
			}
		} else if (e.getSource() == viewObject.getNorthPanel().getLogDSlider().getSlider()) {
			if ("Search Compound".equals(viewObject.getMenu().getCompoundID().getText())) {
				if (selectionStatus.get() == 4) {
				} else if (selectionStatus.get() == 5) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogD = viewObject.getNorthPanel().getLogDSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int ringCount = viewObject.getNorthPanel().getRingCountSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customLeadLikenessTable(modelObject.getNoFilterTable(), inputLogD,
									inputDonor, inputAcceptor, inputMolWeight, ringCount, bondCount));
				} else if (selectionStatus.get() == 6) {
				}
			} else {
				if (selectionStatus.get() == 4) {
				} else if (selectionStatus.get() == 5) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogD = viewObject.getNorthPanel().getLogDSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int ringCount = viewObject.getNorthPanel().getRingCountSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customFiltering(
									Filters.customLeadLikenessTable(modelObject.getNoFilterTable(), inputLogD,
											inputDonor, inputAcceptor, inputMolWeight, ringCount, bondCount),
									viewObject.getMenu().getCompoundID().getText()));
				} else if (selectionStatus.get() == 6) {
				}
			}
		} else if (e.getSource() == viewObject.getNorthPanel().getAreaSlider().getSlider()) {
			if ("".equals(viewObject.getMenu().getCompoundID().getText())) {
				if (selectionStatus.get() == 4) {
				} else if (selectionStatus.get() == 5) {
				} else if (selectionStatus.get() == 6) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int FARingCount = viewObject.getNorthPanel().getFARingSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					double area = viewObject.getNorthPanel().getAreaSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customBioavailabilityTable(modelObject.getNoFilterTable(), inputMolWeight,
									inputLogP, area, inputDonor, inputAcceptor, bondCount, FARingCount));
				}
			} else {
				if (selectionStatus.get() == 4) {
				} else if (selectionStatus.get() == 5) {
				} else if (selectionStatus.get() == 6) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int FARingCount = viewObject.getNorthPanel().getFARingSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					double area = viewObject.getNorthPanel().getAreaSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customFiltering(
									Filters.customBioavailabilityTable(modelObject.getNoFilterTable(), inputMolWeight,
											inputLogP, area, inputDonor, inputAcceptor, bondCount, FARingCount),
									viewObject.getMenu().getCompoundID().getText()));
				}
			}
		} else if (e.getSource() == viewObject.getNorthPanel().getHbdaASlider().getSlider()) {
			if ("Search Compound".equals(viewObject.getMenu().getCompoundID().getText())) {
				if (selectionStatus.get() == 4) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable().updateTable(Filters.customLipinskisTable(
							modelObject.getNoFilterTable(), inputLogP, inputDonor, inputAcceptor, inputMolWeight));
				} else if (selectionStatus.get() == 5) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogD = viewObject.getNorthPanel().getLogDSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int ringCount = viewObject.getNorthPanel().getRingCountSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customLeadLikenessTable(modelObject.getNoFilterTable(), inputLogD,
									inputDonor, inputAcceptor, inputMolWeight, ringCount, bondCount));
				} else if (selectionStatus.get() == 6) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int FARingCount = viewObject.getNorthPanel().getFARingSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					double area = viewObject.getNorthPanel().getAreaSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customBioavailabilityTable(modelObject.getNoFilterTable(), inputMolWeight,
									inputLogP, area, inputDonor, inputAcceptor, bondCount, FARingCount));
				}
			} else {
				if (selectionStatus.get() == 4) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customFiltering(
									Filters.customLipinskisTable(modelObject.getNoFilterTable(), inputLogP, inputDonor,
											inputAcceptor, inputMolWeight),
									viewObject.getMenu().getCompoundID().getText()));
				} else if (selectionStatus.get() == 5) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogD = viewObject.getNorthPanel().getLogDSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int ringCount = viewObject.getNorthPanel().getRingCountSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customFiltering(
									Filters.customLeadLikenessTable(modelObject.getNoFilterTable(), inputLogD,
											inputDonor, inputAcceptor, inputMolWeight, ringCount, bondCount),
									viewObject.getMenu().getCompoundID().getText()));
				} else if (selectionStatus.get() == 6) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int FARingCount = viewObject.getNorthPanel().getFARingSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					double area = viewObject.getNorthPanel().getAreaSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customFiltering(
									Filters.customBioavailabilityTable(modelObject.getNoFilterTable(), inputMolWeight,
											inputLogP, area, inputDonor, inputAcceptor, bondCount, FARingCount),
									viewObject.getMenu().getCompoundID().getText()));
				}
			}
		} else if (e.getSource() == viewObject.getNorthPanel().getHbdaDSlider().getSlider()) {
			if ("Search Compound".equals(viewObject.getMenu().getCompoundID().getText())) {
				if (selectionStatus.get() == 4) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable().updateTable(Filters.customLipinskisTable(
							modelObject.getNoFilterTable(), inputLogP, inputDonor, inputAcceptor, inputMolWeight));
				} else if (selectionStatus.get() == 5) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogD = viewObject.getNorthPanel().getLogDSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int ringCount = viewObject.getNorthPanel().getRingCountSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customLeadLikenessTable(modelObject.getNoFilterTable(), inputLogD,
									inputDonor, inputAcceptor, inputMolWeight, ringCount, bondCount));
				} else if (selectionStatus.get() == 6) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int FARingCount = viewObject.getNorthPanel().getFARingSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					double area = viewObject.getNorthPanel().getAreaSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customBioavailabilityTable(modelObject.getNoFilterTable(), inputMolWeight,
									inputLogP, area, inputDonor, inputAcceptor, bondCount, FARingCount));
				}
			} else {
				if (selectionStatus.get() == 4) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customFiltering(
									Filters.customLipinskisTable(modelObject.getNoFilterTable(), inputLogP, inputDonor,
											inputAcceptor, inputMolWeight),
									viewObject.getMenu().getCompoundID().getText()));
				} else if (selectionStatus.get() == 5) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogD = viewObject.getNorthPanel().getLogDSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int ringCount = viewObject.getNorthPanel().getRingCountSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customFiltering(
									Filters.customLeadLikenessTable(modelObject.getNoFilterTable(), inputLogD,
											inputDonor, inputAcceptor, inputMolWeight, ringCount, bondCount),
									viewObject.getMenu().getCompoundID().getText()));
				} else if (selectionStatus.get() == 6) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int FARingCount = viewObject.getNorthPanel().getFARingSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					double area = viewObject.getNorthPanel().getAreaSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customFiltering(
									Filters.customBioavailabilityTable(modelObject.getNoFilterTable(), inputMolWeight,
											inputLogP, area, inputDonor, inputAcceptor, bondCount, FARingCount),
									viewObject.getMenu().getCompoundID().getText()));
				}
			}
		} else if (e.getSource() == viewObject.getNorthPanel().getRingCountSlider().getSlider()) {
			if ("Search Compound".equals(viewObject.getMenu().getCompoundID().getText())) {
				if (selectionStatus.get() == 4) {
				} else if (selectionStatus.get() == 5) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogD = viewObject.getNorthPanel().getLogDSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int ringCount = viewObject.getNorthPanel().getRingCountSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customLeadLikenessTable(modelObject.getNoFilterTable(), inputLogD,
									inputDonor, inputAcceptor, inputMolWeight, ringCount, bondCount));
				} else if (selectionStatus.get() == 6) {
				}
			} else {
				if (selectionStatus.get() == 4) {
				} else if (selectionStatus.get() == 5) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogD = viewObject.getNorthPanel().getLogDSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int ringCount = viewObject.getNorthPanel().getRingCountSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customFiltering(
									Filters.customLeadLikenessTable(modelObject.getNoFilterTable(), inputLogD,
											inputDonor, inputAcceptor, inputMolWeight, ringCount, bondCount),
									viewObject.getMenu().getCompoundID().getText()));
				} else if (selectionStatus.get() == 6) {
				}
			}
		} else if (e.getSource() == viewObject.getNorthPanel().getFARingSlider().getSlider()) {
			if ("Search Compound".equals(viewObject.getMenu().getCompoundID().getText())) {
				if (selectionStatus.get() == 4) {
				} else if (selectionStatus.get() == 5) {
				} else if (selectionStatus.get() == 6) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int FARingCount = viewObject.getNorthPanel().getFARingSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					double area = viewObject.getNorthPanel().getAreaSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customBioavailabilityTable(modelObject.getNoFilterTable(), inputMolWeight,
									inputLogP, area, inputDonor, inputAcceptor, bondCount, FARingCount));
				}
			} else {
				if (selectionStatus.get() == 4) {
				} else if (selectionStatus.get() == 5) {
				} else if (selectionStatus.get() == 6) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int FARingCount = viewObject.getNorthPanel().getFARingSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					double area = viewObject.getNorthPanel().getAreaSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customFiltering(
									Filters.customBioavailabilityTable(modelObject.getNoFilterTable(), inputMolWeight,
											inputLogP, area, inputDonor, inputAcceptor, bondCount, FARingCount),
									viewObject.getMenu().getCompoundID().getText()));
				}
			}
		} else if (e.getSource() == viewObject.getNorthPanel().getBondCountSlider().getSlider()) {
			if ("Search Compound".equals(viewObject.getMenu().getCompoundID().getText())) {
				if (selectionStatus.get() == 4) {
				} else if (selectionStatus.get() == 5) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogD = viewObject.getNorthPanel().getLogDSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int ringCount = viewObject.getNorthPanel().getRingCountSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customLeadLikenessTable(modelObject.getNoFilterTable(), inputLogD,
									inputDonor, inputAcceptor, inputMolWeight, ringCount, bondCount));
				} else if (selectionStatus.get() == 6) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int FARingCount = viewObject.getNorthPanel().getFARingSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					double area = viewObject.getNorthPanel().getAreaSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customBioavailabilityTable(modelObject.getNoFilterTable(), inputMolWeight,
									inputLogP, area, inputDonor, inputAcceptor, bondCount, FARingCount));
				}
			} else {
				if (selectionStatus.get() == 4) {
				} else if (selectionStatus.get() == 5) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogD = viewObject.getNorthPanel().getLogDSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int ringCount = viewObject.getNorthPanel().getRingCountSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customFiltering(
									Filters.customLeadLikenessTable(modelObject.getNoFilterTable(), inputLogD,
											inputDonor, inputAcceptor, inputMolWeight, ringCount, bondCount),
									viewObject.getMenu().getCompoundID().getText()));
				} else if (selectionStatus.get() == 6) {
					double inputMolWeight = viewObject.getNorthPanel().getMolWeightSlider().getCurrentValue();
					double inputLogP = viewObject.getNorthPanel().getLogPSlider().getCurrentValue();
					int inputDonor = viewObject.getNorthPanel().getHbdaDSlider().getCurrentValue();
					int inputAcceptor = viewObject.getNorthPanel().getHbdaASlider().getCurrentValue();
					int FARingCount = viewObject.getNorthPanel().getFARingSlider().getCurrentValue();
					int bondCount = viewObject.getNorthPanel().getBondCountSlider().getCurrentValue();
					double area = viewObject.getNorthPanel().getAreaSlider().getCurrentValue();
					viewObject.getCentrePanel().getTable().clearTable();
					viewObject.getCentrePanel().getTable()
							.updateTable(Filters.customFiltering(
									Filters.customBioavailabilityTable(modelObject.getNoFilterTable(), inputMolWeight,
											inputLogP, area, inputDonor, inputAcceptor, bondCount, FARingCount),
									viewObject.getMenu().getCompoundID().getText()));
				}
			}
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {// make search bar clickable
		if(e.getSource() == viewObject.getMenu().getCompoundID()) {
			viewObject.getMenu().getCompoundID().setFocusable(true);
		}
	}
	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() == viewObject.getMenu().getCompoundID()) {
			viewObject.getMenu().getCompoundID().setFocusable(false);
		}
	}

	//Search bar settings
	@Override
	public void focusGained(FocusEvent e) { // if search bar is clicked (there is a cursor), text appears and color is black
		if (viewObject.getMenu().getCompoundID().getText().equals("Search Compound")) {
			viewObject.getMenu().getCompoundID().setText("");
			viewObject.getMenu().getCompoundID().setForeground(Color.BLACK);
		}
	}

	
	@Override
	public void focusLost(FocusEvent e) { // if no cursor, text turns gray
		if (viewObject.getMenu().getCompoundID().getText().isEmpty()) {
			viewObject.getMenu().getCompoundID().setForeground(Color.GRAY);
			viewObject.getMenu().getCompoundID().setText("Search Compound");
		}
	}
}
