//Sliders for doubles
import javax.swing.JLabel;
import javax.swing.JSlider;

public class DoubleSlider extends JSlider{


	private static final long serialVersionUID = 1L;
	
	private JLabel l;
	private JSlider b;
	private Controller controllerObject;
	private double t;
	private String name;
	
	public DoubleSlider(Controller controller, int start, int finish, int initial, String s) {
		
		controllerObject = controller;
		name = s;
		// create label 
        l = new JLabel(); 
        l.setHorizontalAlignment(JLabel.CENTER);
        // create a slider 
        b = new JSlider(start, finish, initial); 

        b.setEnabled(false);
		l.setEnabled(false);
        // setChangeListener 
        b.addChangeListener(controllerObject);
        b.addMouseListener(controllerObject); 

        // set the text of label
        t = b.getValue()/1000.000;
        l.setText(name + "  " + t); 
	}
	
	public JSlider getSlider() {
		return this.b;
	}
	
	public JLabel getLabel() {
		return this.l;
	}
	
	//to convert an integer into a double; divide by 1000 (all doubles are limited to 3 decimal places)
	public void updateText() { 
		t = b.getValue()/1000.000; 
		l.setText(name + "  " + t);	
	}
	
	public Double getCurrentValue() {
		t = b.getValue()/1000.000;
		return t;
	}
}
