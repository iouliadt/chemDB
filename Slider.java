//Class where Slider objects are defined
import javax.swing.JLabel;
import javax.swing.JSlider;

public class Slider extends JSlider{

	private static final long serialVersionUID = 1L;
	
	private JLabel l;
	private JSlider b;
	private Controller controllerObject;
	private String name;
	
	public Slider(Controller controller, int start, int finish, int initial, String s) {
		
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
        l.setText(name + "  " + b.getValue()); 
	}
	
	public JSlider getSlider() {
		return this.b;
	}
	
	public JLabel getLabel() {
		return this.l;
	}
	
	public void updateText() {
		l.setText(name + "  " + b.getValue());	
	}
	
	public Integer getCurrentValue() {
		return b.getValue();
	}
}
