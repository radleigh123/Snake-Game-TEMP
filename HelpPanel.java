import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class HelpPanel extends JLabel {
    JLabel label;

    public HelpPanel() {
        ImageIcon image = new ImageIcon("GameControl.png");
        label = new JLabel(image);
    }
}
