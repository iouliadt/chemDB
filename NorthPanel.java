
//North Panel; contains Sliders for user interaction
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

public class NorthPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel pNorth, userInput, sliderLabels, sliders;
	private JTextField compoundID;
	private Slider hbdaASlider, hbdaDSlider, ringCountSlider, FARingSlider, bondCountSlider;
	private DoubleSlider molWeightSlider, logPSlider, logDSlider, areaSlider;
	private Controller controllerObject;
	private Font f;

	public NorthPanel(Controller controller) {
		controllerObject = controller;

		setLayout(new BorderLayout());
		pNorth = new JPanel();
		pNorth.setLayout(new GridLayout(1, 1));
		add("North", pNorth);

		userInput = new JPanel(); // panel containing sliders
		userInput.setLayout(new GridLayout(2, 1));

		sliderLabels = new JPanel();
		sliderLabels.setLayout(new GridLayout(1, 9));

		sliders = new JPanel();
		sliders.setLayout(new GridLayout(1, 9));

		// Create Sliders and ranges for values
		molWeightSlider = new DoubleSlider(controllerObject, 0, 1000000, 500000, "Mass");
		sliderLabels.add(molWeightSlider.getLabel());
		sliders.add(molWeightSlider.getSlider());

		logPSlider = new DoubleSlider(controllerObject, -10000, 10000, 5000, "logP");
		sliderLabels.add(logPSlider.getLabel());
		sliders.add(logPSlider.getSlider());

		logDSlider = new DoubleSlider(controllerObject, -15000, 10000, 4000, "logD");
		sliderLabels.add(logDSlider.getLabel());
		sliders.add(logDSlider.getSlider());

		areaSlider = new DoubleSlider(controllerObject, 20000, 400000, 200000, "PSA");
		sliderLabels.add(areaSlider.getLabel());
		sliders.add(areaSlider.getSlider());

		hbdaASlider = new Slider(controllerObject, 0, 40, 20, "Acceptor Count");
		sliderLabels.add(hbdaASlider.getLabel());
		sliders.add(hbdaASlider.getSlider());

		hbdaDSlider = new Slider(controllerObject, 0, 20, 10, "Donor Count");
		sliderLabels.add(hbdaDSlider.getLabel());
		sliders.add(hbdaDSlider.getSlider());

		ringCountSlider = new Slider(controllerObject, 0, 10, 5, "Ring count");
		sliderLabels.add(ringCountSlider.getLabel());
		sliders.add(ringCountSlider.getSlider());

		FARingSlider = new Slider(controllerObject, 0, 5, 2, "Fused Aromatic Rings");
		sliderLabels.add(FARingSlider.getLabel());
		sliders.add(FARingSlider.getSlider());

		bondCountSlider = new Slider(controllerObject, 0, 5, 2, "Rotatable Bonds");
		sliderLabels.add(bondCountSlider.getLabel());
		sliders.add(bondCountSlider.getSlider());

		userInput.add(sliderLabels);
		userInput.add(sliders);
		pNorth.add(userInput);
		
		disableSliders();
	}

	//method that disables sliders
	public void disableSliders() {

		molWeightSlider.getSlider().setEnabled(false);
		molWeightSlider.getLabel().setText("");
		molWeightSlider.getLabel().setEnabled(false);
		molWeightSlider.getSlider().setVisible(false);

		logPSlider.getSlider().setEnabled(false);
		logPSlider.getLabel().setText("");
		logPSlider.getLabel().setEnabled(false);
		logPSlider.getSlider().setVisible(false);

		logDSlider.getSlider().setEnabled(false);
		logDSlider.getLabel().setText("");
		logDSlider.getLabel().setEnabled(false);
		logDSlider.getSlider().setVisible(false);

		hbdaDSlider.getSlider().setEnabled(false);
		hbdaDSlider.getLabel().setText("");
		hbdaDSlider.getLabel().setEnabled(false);
		hbdaDSlider.getSlider().setVisible(false);

		hbdaASlider.getSlider().setEnabled(false);
		hbdaASlider.getLabel().setText("");
		hbdaASlider.getLabel().setEnabled(false);
		hbdaASlider.getSlider().setVisible(false);

		ringCountSlider.getSlider().setEnabled(false);
		ringCountSlider.getLabel().setText("");
		ringCountSlider.getLabel().setEnabled(false);
		ringCountSlider.getSlider().setVisible(false);

		bondCountSlider.getSlider().setEnabled(false);
		bondCountSlider.getLabel().setText("");
		bondCountSlider.getLabel().setEnabled(false);
		bondCountSlider.getSlider().setVisible(false);

		FARingSlider.getSlider().setEnabled(false);
		FARingSlider.getLabel().setText("");
		FARingSlider.getLabel().setEnabled(false);
		FARingSlider.getSlider().setVisible(false);

		areaSlider.getSlider().setEnabled(false);
		areaSlider.getLabel().setText("");
		areaSlider.getLabel().setEnabled(false);
		areaSlider.getSlider().setVisible(false);

	}

	//set the slider appearance in custom lipinski's method
	public void enableCustomLipinskisSliders() {

		molWeightSlider.getSlider().setVisible(true);
		molWeightSlider.getSlider().setEnabled(true);
		molWeightSlider.getSlider().setValue(500000);
		molWeightSlider.getLabel().setEnabled(true);
		molWeightSlider.updateText();
		f = molWeightSlider.getLabel().getFont();
		molWeightSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		logPSlider.getSlider().setVisible(true);
		logPSlider.getSlider().setEnabled(true);
		logPSlider.getSlider().setValue(5000);
		logPSlider.getLabel().setEnabled(true);
		logPSlider.updateText();
		f = logPSlider.getLabel().getFont();
		logPSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		hbdaASlider.getSlider().setVisible(true);
		hbdaASlider.getSlider().setEnabled(true);
		hbdaASlider.getSlider().setValue(10);
		hbdaASlider.getLabel().setEnabled(true);
		hbdaASlider.updateText();
		f = hbdaASlider.getLabel().getFont();
		hbdaASlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		hbdaDSlider.getSlider().setVisible(true);
		hbdaDSlider.getSlider().setEnabled(true);
		hbdaDSlider.getSlider().setValue(10);
		hbdaDSlider.getLabel().setEnabled(true);
		hbdaDSlider.updateText();
		f = hbdaDSlider.getLabel().getFont();
		hbdaDSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

	}
	
	//for default lipinski

	public void enableCustomLipinskisDisabledSliders() {

		molWeightSlider.getSlider().setVisible(true);
		molWeightSlider.getSlider().setEnabled(false);
		molWeightSlider.getSlider().setValue(500000);
		molWeightSlider.getLabel().setEnabled(false);
		molWeightSlider.updateText();
		f = molWeightSlider.getLabel().getFont();
		molWeightSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		logPSlider.getSlider().setVisible(true);
		logPSlider.getSlider().setEnabled(false);
		logPSlider.getSlider().setValue(5000);
		logPSlider.getLabel().setEnabled(false);
		logPSlider.updateText();
		f = logPSlider.getLabel().getFont();
		logPSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		hbdaASlider.getSlider().setVisible(true);
		hbdaASlider.getSlider().setEnabled(false);
		hbdaASlider.getSlider().setValue(10);
		hbdaASlider.getLabel().setEnabled(false);
		hbdaASlider.updateText();
		f = hbdaASlider.getLabel().getFont();
		hbdaASlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		hbdaDSlider.getSlider().setVisible(true);
		hbdaDSlider.getSlider().setEnabled(false);
		hbdaDSlider.getSlider().setValue(10);
		hbdaDSlider.getLabel().setEnabled(false);
		hbdaDSlider.updateText();
		f = hbdaDSlider.getLabel().getFont();
		hbdaDSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

	}

	public void enableCustomLeadLikenessSliders() {
		molWeightSlider.getSlider().setVisible(true);
		molWeightSlider.getSlider().setEnabled(true);
		molWeightSlider.getSlider().setValue(450000);
		molWeightSlider.getLabel().setEnabled(true);
		molWeightSlider.updateText();
		f = molWeightSlider.getLabel().getFont();
		molWeightSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		logDSlider.getSlider().setVisible(true);
		logDSlider.getSlider().setEnabled(true);
		logDSlider.getSlider().setValue(4000);
		logDSlider.getLabel().setEnabled(true);
		logDSlider.updateText();
		f = logDSlider.getLabel().getFont();
		logDSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		hbdaASlider.getSlider().setVisible(true);
		hbdaASlider.getSlider().setEnabled(true);
		hbdaASlider.getSlider().setValue(8);
		hbdaASlider.getLabel().setEnabled(true);
		hbdaASlider.updateText();
		f = hbdaASlider.getLabel().getFont();
		hbdaASlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		hbdaDSlider.getSlider().setVisible(true);
		hbdaDSlider.getSlider().setEnabled(true);
		hbdaDSlider.getSlider().setValue(5);
		hbdaDSlider.getLabel().setEnabled(true);
		hbdaDSlider.updateText();
		f = hbdaDSlider.getLabel().getFont();
		hbdaDSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		ringCountSlider.getSlider().setVisible(true);
		ringCountSlider.getSlider().setEnabled(true);
		ringCountSlider.getSlider().setValue(4);
		ringCountSlider.getLabel().setEnabled(true);
		ringCountSlider.updateText();
		f = ringCountSlider.getLabel().getFont();
		ringCountSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		bondCountSlider.getSlider().setVisible(true);
		bondCountSlider.getSlider().setEnabled(true);
		bondCountSlider.getSlider().setValue(10);
		bondCountSlider.getLabel().setEnabled(true);
		bondCountSlider.updateText();
		f = bondCountSlider.getLabel().getFont();
		bondCountSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

	}

	public void enableCustomLeadLikenessDisabledSliders() {
		molWeightSlider.getSlider().setVisible(true);
		molWeightSlider.getSlider().setEnabled(false);
		molWeightSlider.getSlider().setValue(450000);
		molWeightSlider.getLabel().setEnabled(false);
		molWeightSlider.updateText();
		f = molWeightSlider.getLabel().getFont();
		molWeightSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		logDSlider.getSlider().setVisible(true);
		logDSlider.getSlider().setEnabled(false);
		logDSlider.getSlider().setValue(4000);
		logDSlider.getLabel().setEnabled(false);
		logDSlider.updateText();
		f = logDSlider.getLabel().getFont();
		logDSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		hbdaASlider.getSlider().setVisible(true);
		hbdaASlider.getSlider().setEnabled(false);
		hbdaASlider.getSlider().setValue(8);
		hbdaASlider.getLabel().setEnabled(false);
		hbdaASlider.updateText();
		f = hbdaASlider.getLabel().getFont();
		hbdaASlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		hbdaDSlider.getSlider().setVisible(true);
		hbdaDSlider.getSlider().setEnabled(false);
		hbdaDSlider.getSlider().setValue(5);
		hbdaDSlider.getLabel().setEnabled(false);
		hbdaDSlider.updateText();
		f = hbdaDSlider.getLabel().getFont();
		hbdaDSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		ringCountSlider.getSlider().setVisible(true);
		ringCountSlider.getSlider().setEnabled(false);
		ringCountSlider.getSlider().setValue(4);
		ringCountSlider.getLabel().setEnabled(false);
		ringCountSlider.updateText();
		f = ringCountSlider.getLabel().getFont();
		ringCountSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		bondCountSlider.getSlider().setVisible(true);
		bondCountSlider.getSlider().setEnabled(false);
		bondCountSlider.getSlider().setValue(10);
		bondCountSlider.getLabel().setEnabled(false);
		bondCountSlider.updateText();
		f = bondCountSlider.getLabel().getFont();
		bondCountSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

	}

	public void enableCustomBioavailabilitySliders() {
		molWeightSlider.getSlider().setVisible(true);
		molWeightSlider.getSlider().setEnabled(true);
		molWeightSlider.getSlider().setValue(500000);
		molWeightSlider.getLabel().setEnabled(true);
		molWeightSlider.updateText();
		f = molWeightSlider.getLabel().getFont();
		molWeightSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		logPSlider.getSlider().setVisible(true);
		logPSlider.getSlider().setEnabled(true);
		logPSlider.getSlider().setValue(5000);
		logPSlider.getLabel().setEnabled(true);
		logPSlider.updateText();
		f = logPSlider.getLabel().getFont();
		logPSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		hbdaASlider.getSlider().setVisible(true);
		hbdaASlider.getSlider().setEnabled(true);
		hbdaASlider.getSlider().setValue(10);
		hbdaASlider.getLabel().setEnabled(true);
		hbdaASlider.updateText();
		f = hbdaASlider.getLabel().getFont();
		hbdaASlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		hbdaDSlider.getSlider().setVisible(true);
		hbdaDSlider.getSlider().setEnabled(true);
		hbdaDSlider.getSlider().setValue(5);
		hbdaDSlider.getLabel().setEnabled(true);
		hbdaDSlider.updateText();
		f = hbdaDSlider.getLabel().getFont();
		hbdaDSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		bondCountSlider.getSlider().setVisible(true);
		bondCountSlider.getSlider().setEnabled(true);
		bondCountSlider.getSlider().setValue(10);
		bondCountSlider.getLabel().setEnabled(true);
		bondCountSlider.updateText();
		f = bondCountSlider.getLabel().getFont();
		bondCountSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		FARingSlider.getSlider().setVisible(true);
		FARingSlider.getSlider().setEnabled(true);
		FARingSlider.getSlider().setValue(5);
		FARingSlider.getLabel().setEnabled(true);
		FARingSlider.updateText();
		f = FARingSlider.getLabel().getFont();
		FARingSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		areaSlider.getSlider().setVisible(true);
		areaSlider.getSlider().setEnabled(true);
		areaSlider.getSlider().setValue(200000);
		areaSlider.getLabel().setEnabled(true);
		areaSlider.updateText();
		f = areaSlider.getLabel().getFont();
		areaSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));
	}

	public void enableCustomBioavailabilityDisabledSliders() {
		molWeightSlider.getSlider().setVisible(true);
		molWeightSlider.getSlider().setEnabled(false);
		molWeightSlider.getSlider().setValue(500000);
		molWeightSlider.getLabel().setEnabled(false);
		molWeightSlider.updateText();
		f = molWeightSlider.getLabel().getFont();
		molWeightSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		logPSlider.getSlider().setVisible(true);
		logPSlider.getSlider().setEnabled(false);
		logPSlider.getSlider().setValue(5000);
		logPSlider.getLabel().setEnabled(false);
		logPSlider.updateText();
		f = logPSlider.getLabel().getFont();
		logPSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		hbdaASlider.getSlider().setVisible(true);
		hbdaASlider.getSlider().setEnabled(false);
		hbdaASlider.getSlider().setValue(10);
		hbdaASlider.getLabel().setEnabled(false);
		hbdaASlider.updateText();
		f = hbdaASlider.getLabel().getFont();
		hbdaASlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		hbdaDSlider.getSlider().setVisible(true);
		hbdaDSlider.getSlider().setEnabled(false);
		hbdaDSlider.getSlider().setValue(5);
		hbdaDSlider.getLabel().setEnabled(false);
		hbdaDSlider.updateText();
		f = hbdaDSlider.getLabel().getFont();
		hbdaDSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		bondCountSlider.getSlider().setVisible(true);
		bondCountSlider.getSlider().setEnabled(false);
		bondCountSlider.getSlider().setValue(10);
		bondCountSlider.getLabel().setEnabled(false);
		bondCountSlider.updateText();
		f = bondCountSlider.getLabel().getFont();
		bondCountSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		FARingSlider.getSlider().setVisible(true);
		FARingSlider.getSlider().setEnabled(false);
		FARingSlider.getSlider().setValue(5);
		FARingSlider.getLabel().setEnabled(false);
		FARingSlider.updateText();
		f = FARingSlider.getLabel().getFont();
		FARingSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));

		areaSlider.getSlider().setVisible(true);
		areaSlider.getSlider().setEnabled(false);
		areaSlider.getSlider().setValue(200000);
		areaSlider.getLabel().setEnabled(false);
		areaSlider.updateText();
		f = areaSlider.getLabel().getFont();
		areaSlider.getLabel().setFont(f.deriveFont(((java.awt.Font) f).getStyle() | Font.BOLD));
	}

	// getter for north Panel
	public JPanel getPanel() {
		return this.pNorth;
	}

	// getter for slider
	public DoubleSlider getMolWeightSlider() {
		return this.molWeightSlider;
	}

	// getter for slider
	public DoubleSlider getLogPSlider() {
		return this.logPSlider;
	}

	// getter for slider
	public DoubleSlider getLogDSlider() {
		return this.logDSlider;
	}

	// getter for slider
	public DoubleSlider getAreaSlider() {
		return this.areaSlider;
	}

	// getter for slider
	public Slider getHbdaASlider() {
		return this.hbdaASlider;
	}

	// getter for slider
	public Slider getHbdaDSlider() {
		return this.hbdaDSlider;
	}

	// getter for slider
	public Slider getRingCountSlider() {
		return this.ringCountSlider;
	}

	// getter for slider
	public Slider getFARingSlider() {
		return this.FARingSlider;
	}

	// getter for slider
	public Slider getBondCountSlider() {
		return this.bondCountSlider;
	}

}
