import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

public class SnakeUI extends JPanel {
    private static final int MENU_WIDTH = 180;
    private static final int MENU_HEIGHT = 250;

    private boolean gridDisplay = true;
    private int speedDifficulty = 50; // default speed

    private String title = "Snake Game";

    private JFrame startFrame;
    private JFrame optionFrame;
    private JFrame helpFrame;

    public SnakeUI() {
        setPreferredSize(new Dimension(MENU_WIDTH, MENU_HEIGHT)); // dimensions for menu
        setBorder(new EmptyBorder(0, 10, 5, 10)); // up, left, down, right
        setLayout(new GridLayout(9, 1)); // rw, clmn

        JButton startButton = new JButton("Start");
        JButton optionsButton = new JButton("Options");
        JButton helpButton = new JButton("Help");
        JButton exitButton = new JButton("Exit");
        mainMenuButtons(startButton, optionsButton, helpButton, exitButton);

        // Main Menu
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel(title);

        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        titlePanel.add(titleLabel);
        JPanel codedBy = new JPanel();
        codedBy.add(new JLabel("Coded by:"));
        JPanel bancale = new JPanel();
        bancale.add(new JLabel("Christine Bancale"));
        JPanel inting = new JPanel();
        inting.add(new JLabel("Keane Radleigh Inting"));

        add(titlePanel);
        add(startButton);
        add(optionsButton);
        add(helpButton);
        add(exitButton);
        add(new JPanel()); // empty panel
        add(codedBy);
        add(bancale);
        add(inting);
    }

    private void mainMenuButtons(JButton startButton, JButton optionsButton, JButton helpButton, JButton exitButton) {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("START");
                if (startFrame == null && (optionFrame == null || !optionFrame.isVisible())) {
                    startFrame = new JFrame(title);

                    // resets when the game is done
                    startFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            startFrame = null;
                        }
                    });

                    startFrame.add(new SnakeGamePanel(speedDifficulty, gridDisplay));
                    startFrame.setResizable(false);
                    startFrame.pack();
                    startFrame.setLocationRelativeTo(null);
                    startFrame.setVisible(true);
                } else {
                    if (startFrame == null) {
                        JOptionPane.showMessageDialog(null, "A window is opened", null,
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Another game is still running!", null,
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        optionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((optionFrame == null || !optionFrame.isVisible())
                        && (startFrame == null || !startFrame.isVisible())) {
                    System.out.println("OPTIONS");
                    optionFrame = new JFrame(title + "-Options");

                    // 35 - Easy, 50 - Normal, 75 - Hardcore, 90 - God
                    JPanel difficultyPanel = new JPanel(new FlowLayout());
                    JRadioButton easyButton = new JRadioButton("Easy");
                    JRadioButton normalButton = new JRadioButton("Normal");
                    JRadioButton hardButton = new JRadioButton("Hard");
                    JRadioButton godButton = new JRadioButton("God");
                    ButtonGroup difficultyGroup = new ButtonGroup(); // to choose only one button
                    difficultyGroup.add(easyButton);
                    difficultyGroup.add(normalButton);
                    difficultyGroup.add(hardButton);
                    difficultyGroup.add(godButton);
                    // normalButton.setSelected(true); // set default value
                    optionDelayButtons(easyButton, normalButton, hardButton, godButton);
                    difficultyPanel.add(new JLabel("Difficulty: "));
                    difficultyPanel.add(easyButton);
                    difficultyPanel.add(normalButton);
                    difficultyPanel.add(hardButton);
                    difficultyPanel.add(godButton);

                    // true - grid display on, false - grid display off
                    JPanel gridDisplayPanel = new JPanel(new FlowLayout());
                    JRadioButton onButton = new JRadioButton("On");
                    JRadioButton offButton = new JRadioButton("Off");
                    ButtonGroup gridDisplayGroup = new ButtonGroup();
                    gridDisplayGroup.add(onButton);
                    gridDisplayGroup.add(offButton);
                    // offButton.setSelected(true); // set default value
                    optionGridButtons(onButton, offButton);
                    gridDisplayPanel.add(new JLabel("Grid Display: "));
                    gridDisplayPanel.add(onButton);
                    gridDisplayPanel.add(offButton);

                    JButton saveButton = new JButton("Done");
                    saveButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("SAVED");
                            int input = JOptionPane.showConfirmDialog(
                                    null,
                                    "Is this your final decision?",
                                    null,
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null);

                            if (input == 0) {
                                optionFrame.dispose();
                            }
                        }
                    });

                    optionFrame.setLayout(new BorderLayout());
                    optionFrame.add(difficultyPanel, BorderLayout.NORTH);
                    optionFrame.add(gridDisplayPanel);
                    optionFrame.add(saveButton, BorderLayout.SOUTH);
                    optionFrame.setResizable(false);
                    optionFrame.pack();
                    optionFrame.setLocationRelativeTo(null);
                    optionFrame.setVisible(true);
                    optionFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (helpFrame == null || !helpFrame.isVisible()) {
                    System.out.println("HELP");
                    helpFrame = new JFrame(title + "-Help");

                    ImageIcon image = new ImageIcon("GameControl2.png");
                    JLabel label = new JLabel(image);

                    helpFrame.setLocation(0, 290);

                    helpFrame.setLayout(new BorderLayout());
                    helpFrame.add(label, BorderLayout.CENTER);
                    // helpFrame.setBounds(0, 50, MENU_WIDTH * 2, MENU_HEIGHT * 2);
                    helpFrame.setSize(new Dimension(MENU_WIDTH * 2, MENU_HEIGHT * 2));
                    helpFrame.setVisible(true);
                    helpFrame.pack();
                    helpFrame.setResizable(false);
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int input = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to exit?",
                        null,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null);

                if (input == 0) {
                    System.out.println("EXIT");
                    System.exit(0);
                }
            }
        });
    }

    private void optionDelayButtons(JRadioButton easyButton, JRadioButton normalButton, JRadioButton hardButton,
            JRadioButton godButton) {
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speedDifficulty = 75;
                System.out.println("Easy mode: " + speedDifficulty);
            }
        });

        normalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speedDifficulty = 50;
                System.out.println("Normal mode: " + speedDifficulty);
            }
        });

        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speedDifficulty = 35;
                System.out.println("Hard mode: " + speedDifficulty);
            }
        });

        godButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speedDifficulty = 15;
                System.out.println("God mode: " + speedDifficulty);
            }
        });

    }

    private void optionGridButtons(JRadioButton onButton, JRadioButton offButton) {
        onButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gridDisplay = true;
                System.out.println("Grid display: " + gridDisplay);
            }
        });
        offButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gridDisplay = false;
                System.out.println("Grid display: " + gridDisplay);
            }
        });
    }

}
