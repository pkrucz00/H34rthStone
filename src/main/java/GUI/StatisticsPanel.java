package GUI;

import Cards.GameCard;
import GameEngine.Engine;
import Players.Bot;
import Players.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatisticsPanel extends JPanel {
    protected JButton endTurnButton;
    private boolean isButtonClicked;

    private final String playerName;
    private final GameCard mainCard;
    private JLabel mainCardHPLabel;
    private JLabel mainCardForceLabel;

    public StatisticsPanel(boolean isHumanPlayer, Engine engine){
        //initialization
        if (isHumanPlayer) {
            Player player = engine.getHumanPlayer();
            this.playerName = player.getName();
            this.mainCard = player.getMainCard();
        }
        else {
            Bot bot = engine.getBot();
            this.playerName = bot.getName();
            this.mainCard = bot.getMainCard();
        }

        this.setLayout(new GridLayout(1, 4));
        //info labels
        JLabel nameLabel = new JLabel(playerName);
        this.add(nameLabel);

        this.mainCardHPLabel = new JLabel();
        this.add(mainCardHPLabel);

        this.mainCardForceLabel = new JLabel();
        this.add(mainCardForceLabel);
        mainCardLabelsUpdate();

        //button assigment
        if (isHumanPlayer){
            this.isButtonClicked = false;
            endTurnButton = new JButton("End Turn");
            endTurnButton.setFocusable(false);
            this.add(endTurnButton);
        }
        else{
            JPanel dummyPanel = new JPanel();
            this.add(dummyPanel);
        }

    }

    public void mainCardLabelsUpdate(){
        mainCardHPLabel.setText("HP: " + mainCard.getHP());
        mainCardForceLabel.setText("Force: " + mainCard.getForce());
    }

    public void setActionListener(ActionListener a){
        this.endTurnButton.addActionListener(a);
    }
}
