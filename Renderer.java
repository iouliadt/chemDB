//Class for image rendering
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class  Renderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
    public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
        Component c = super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
        ((JLabel) c).setIcon( (ImageIcon) value );
        ((JLabel) c).setText( "" );
        return c;
    }
}
