import java.awt.BorderLayout;

import javax.swing.JFrame;

public class App extends JFrame {
    public App() {
        // Menu panel
        add(new SnakeUI(), BorderLayout.CENTER);

        setResizable(false);
        pack();
        // setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    public static void main(String[] args) {
        new App();
    }
}
