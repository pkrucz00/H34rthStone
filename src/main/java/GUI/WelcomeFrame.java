package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeFrame extends JFrame implements ActionListener {
    int WIDTH = 1024;
    int HEIGHT = 784;

    private JButton continueButton;
    private JRadioButton lightSideCheckbox;
    private JRadioButton darkSideCheckbox;
    ButtonGroup group = new ButtonGroup();

    String font = "MS Gothic";

    public WelcomeFrame(){
        this.setTitle("Star cards");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.add(createStarCardsTitle());
        this.add(createChoicePanel());
        this.add(createButtonPanel());

        this.pack();
        this.setLayout(new GridLayout(3,1));
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }



    private JPanel createStarCardsTitle(){
        JPanel panel = new JPanel();
        JLabel title = new JLabel("STAR CARDS", SwingConstants.CENTER);
        title.setFont(new Font(font, Font.BOLD, 60));
        title.setForeground(Color.YELLOW);
        panel.add(title);

        JLabel header = new JLabel("Let the force be with you", SwingConstants.CENTER);
        header.setFont(new Font(font, Font.BOLD, 30));
        panel.add(header);

        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT/3));
        panel.setLayout(new GridLayout(2,1));
        return panel;

    }

    private JPanel createChoicePanel(){
        JPanel choicePanel = new JPanel();
        choicePanel.setLayout(new GridLayout(1,2));
        choicePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT/3));

        this.lightSideCheckbox = new JRadioButton("Join the light side");
        addCheckboxes(choicePanel, lightSideCheckbox);

        this.darkSideCheckbox = new JRadioButton("Join the dark side");
        addCheckboxes(choicePanel, darkSideCheckbox);
        return choicePanel;
    }

    private void addCheckboxes(JPanel choicePanel, JRadioButton checkbox) {
        checkbox.setFont(new Font(font, Font.BOLD, 20));
        group.add(checkbox);
        checkbox.addActionListener(this);
        checkbox.setVerticalAlignment(JLabel.CENTER);
        checkbox.setHorizontalAlignment(JLabel.CENTER);
        checkbox.setFocusable(false);

        JPanel box = new JPanel();
        box.setPreferredSize(new Dimension(WIDTH/2, HEIGHT/3));
        box.setLayout(new BorderLayout());
        box.add(checkbox, BorderLayout.CENTER);
        choicePanel.add(box);
    }

    private JPanel createButtonPanel(){
        JPanel panel = new JPanel();

        continueButton = new JButton("Continue");
        continueButton.addActionListener(this);
        continueButton.setPreferredSize(new Dimension(200,50));
        continueButton.setFont(new Font(font, Font.BOLD, 15));
        continueButton.setFocusable(false);
        continueButton.setHorizontalAlignment(0);
        continueButton.setVerticalAlignment(0);

        panel.add(continueButton);
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT/3));
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == continueButton){
            if (darkSideCheckbox.isSelected() || lightSideCheckbox.isSelected()) {
                this.dispose();
                new GameFrame(darkSideCheckbox.isSelected());
            }
        }
    }
}
