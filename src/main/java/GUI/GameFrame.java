package GUI;

import GameEngine.Engine;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    int WIDTH = 1024;
    int HEIGHT = 784;
    int STAT_HEIGHT = 75;
    private Engine engine;

    public GameFrame(boolean isDarkSideChosen){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        engine = new Engine(isDarkSideChosen);


        GamePanel gamePanel = new GamePanel(WIDTH, HEIGHT-2*STAT_HEIGHT, engine);
        this.add(gamePanel, BorderLayout.CENTER);

        StatisticsPanel playerStatistics = new StatisticsPanel(true, engine);
        playerStatistics.setPreferredSize(new Dimension(WIDTH, STAT_HEIGHT));
        playerStatistics.setBackground(Color.RED);
        playerStatistics.setOpaque(true);
        gamePanel.setPlayerStatisticsPanel(playerStatistics);
        playerStatistics.setActionListener(gamePanel);
        this.add(playerStatistics, BorderLayout.PAGE_END);

        StatisticsPanel opponentStatistics = new StatisticsPanel(false, engine);
        opponentStatistics.setPreferredSize(new Dimension(WIDTH, STAT_HEIGHT));
        opponentStatistics.setBackground(Color.PINK);
        opponentStatistics.setOpaque(true);
        gamePanel.setBotStatisticsPanel(opponentStatistics);
        this.add(opponentStatistics, BorderLayout.PAGE_START);


        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.setTitle("STAR CARDS");

    }
}
