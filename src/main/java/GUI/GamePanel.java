package GUI;

import Cards.GameCard;
import GameEngine.Board;
import GameEngine.BoardDivision;
import GameEngine.Engine;
import GameEngine.InitValues;
import Players.Bot;
import Players.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;

import static java.lang.Thread.sleep;

public class GamePanel extends JPanel implements MouseListener, ActionListener {
    private final int WIDTH;
    private final int HEIGHT;

    private final int CARD_WIDTH = 100;
    private final int CARD_HEIGHT = 180;
    private final int HAND_PANEL_HEIGHT = 200;
    private final int singleBoardHeight;

    private final int BOARD_CARD_GAP;       //space between cards on board
    private final int HAND_CARD_GAP;        //space between cards in hand

    private final Engine engine;
    private final Board board;
    private final Player humanPlayer;
    private final Bot bot;
    private final InitValues init;

    private StatisticsPanel playerStatisticsPanel;
    private StatisticsPanel botStatisticsPanel;

    public GamePanel(int width, int height, Engine engine) {
        this.engine = engine;
        this.board = engine.getBoard();
        this.humanPlayer = engine.getHumanPlayer();
        this.bot = engine.getBot();
        this.init = engine.getInitValues();

        this.WIDTH = width;
        this.HEIGHT = height;

        this.singleBoardHeight = (HEIGHT-HAND_PANEL_HEIGHT)/2;
        int n_board = init.MAX_CARDS_ON_BOARD;
        this.BOARD_CARD_GAP = (WIDTH - (n_board * CARD_WIDTH)) / (n_board + 1);
        int n_hand = init.MAX_CARDS_IN_HAND;
        this.HAND_CARD_GAP = (WIDTH - (n_hand * CARD_WIDTH)) / (n_hand + 1);


        this.setPreferredSize(new Dimension(width, height));
        this.addMouseListener(this);
    }


    public void paint(Graphics g) {
        drawArea(g, Color.BLUE, Color.cyan, Color.orange);
        drawCards(g);
        if (board.haveGameEnded()){
            playerStatisticsPanel.endTurnButton.setEnabled(false);
            drawEndScreen(g, board.endGameMessage());
        }

    }

    private void drawArea(Graphics g, Color botColor, Color playerColor, Color playerHandColor){
        g.setColor(botColor);
        g.fillRect(0,0,WIDTH, singleBoardHeight);

        g.setColor(playerColor);
        g.fillRect(0, singleBoardHeight, WIDTH, singleBoardHeight);

        g.setColor(playerHandColor);
        g.fillRect(0, HEIGHT-HAND_PANEL_HEIGHT, WIDTH, HAND_PANEL_HEIGHT);
    }

    private void drawCards(Graphics g){
        BoardDivision[] divisions = BoardDivision.values();
        for (BoardDivision division: divisions) {
            Player currPlayer = division == BoardDivision.BOARD_BOT ? bot : humanPlayer;
            for (int i = 0; i < board.getNoOfCards(division); i++) {
                GameCard cardToDraw = engine.findCardByIndex(i, division);

                Color cardColor = cardToDraw.equals(currPlayer.getMainCard()) ? Color.MAGENTA : Color.lightGray;
                int[] coords = computeCardPlace(division, i);
                drawCard(g, cardColor, cardToDraw, coords[0], coords[1]);

            }
        }
    }

    private void drawCard(Graphics g, Color color, GameCard card, int x, int y){
        int spacing = 18;
        g.setColor(color);
        g.fillRect(x, y, CARD_WIDTH, CARD_HEIGHT);

        if (card.equals(engine.getPickedCard())){
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRect(x, y, CARD_WIDTH, CARD_HEIGHT);
        }

        x = x + 3;
        g.setColor(Color.black);
        g.setFont(new Font(null, Font.BOLD, 13));
        g.drawString(card.getName(), x , y + spacing);
        g.drawString("HP:" + card.getHP(), x, y + 2*spacing);
        g.drawString("AP:" + card.getAP(), x, y + 3*spacing);
        g.drawString("Force: " + card.getForce(), x, y+4*spacing);

    }

    private void drawEndScreen(Graphics g, String message){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.YELLOW);
        g.setFont(new Font("MS Gothic", Font.BOLD, 60));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString(message, (WIDTH - metrics2.stringWidth(message)) / 2, HEIGHT / 2);
    }

    private int[] computeCardPlace(BoardDivision cardPlace, int index){
        int x, y;
        if (cardPlace == BoardDivision.BOARD_BOT || cardPlace == BoardDivision.BOARD_PLAYER){
            x = BOARD_CARD_GAP + (index * (CARD_WIDTH + BOARD_CARD_GAP));
        } else {
            x = HAND_CARD_GAP + (index * (CARD_WIDTH + HAND_CARD_GAP));
        }
        int singleBoardHeight = (HEIGHT-HAND_PANEL_HEIGHT)/2;
        if (cardPlace == BoardDivision.BOARD_BOT){
            y = (singleBoardHeight - CARD_HEIGHT)/2;
        }else if (cardPlace == BoardDivision.BOARD_PLAYER){
            y = singleBoardHeight + (singleBoardHeight - CARD_HEIGHT)/2;
        } else {
            y = HEIGHT - HAND_PANEL_HEIGHT + (HAND_PANEL_HEIGHT - CARD_HEIGHT) / 2;
        }
        return new int[] {x, y};
    }

    private BoardDivision findBoardDivisionByYCoord(int y){
        BoardDivision result;
        if (y >= 0 && y < singleBoardHeight){
            result = BoardDivision.BOARD_BOT;
        } else if (y >= singleBoardHeight && y < 2*singleBoardHeight){
            result = BoardDivision.BOARD_PLAYER;
        } else {
            result = BoardDivision.HAND_PLAYER;
        }
        return result;
    }

    private GameCard findCardByCoords(int x, int y){
        GameCard result = null;
        BoardDivision currentDivision = findBoardDivisionByYCoord(y);
        int offset, panelHeight, gap;
        switch (currentDivision) {
            case BOARD_BOT -> {
                offset = 0;
                panelHeight = singleBoardHeight;
                gap = BOARD_CARD_GAP;
            }
            case BOARD_PLAYER -> {
                offset = singleBoardHeight;
                panelHeight = singleBoardHeight;
                gap = BOARD_CARD_GAP;
            }
            case HAND_PLAYER -> {
                offset = 2 * singleBoardHeight;
                panelHeight = HAND_PANEL_HEIGHT;
                gap = HAND_CARD_GAP;
            }default -> {
                offset = 0;
                panelHeight = 0;
                gap = 0;
            }
        }

        if (y >= offset + (panelHeight - CARD_HEIGHT)/2 && y  <= (panelHeight + CARD_HEIGHT)/2 + offset){
            int index = (x - gap)/(CARD_WIDTH + gap);
            int pixelsFromLeft = (x-gap)%(CARD_WIDTH + gap);
            if (index < board.getNoOfCards(currentDivision) && pixelsFromLeft <= CARD_WIDTH){
                result = engine.findCardByIndex(index, currentDivision);
            }
        }
        return result;
    }

    public void setPlayerStatisticsPanel(StatisticsPanel playerStatisticsPanel) {
        this.playerStatisticsPanel = playerStatisticsPanel;
    }

    public void setBotStatisticsPanel(StatisticsPanel botStatisticsPanel){
        this.botStatisticsPanel = botStatisticsPanel;
    }

    private void updateStatistics(){
        this.playerStatisticsPanel.mainCardLabelsUpdate();
        this.botStatisticsPanel.mainCardLabelsUpdate();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == playerStatisticsPanel.endTurnButton){
            board.endTurn();
            playerStatisticsPanel.endTurnButton.setEnabled(false);
            updateStatistics();

            try {
                sleep(500);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            engine.makeTurnOfBot();
            board.endTurn();
            repaint();
            updateStatistics();
            playerStatisticsPanel.endTurnButton.setEnabled(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (playerStatisticsPanel.endTurnButton.isEnabled()) {
            int x = e.getX();
            int y = e.getY();
            BoardDivision division = this.findBoardDivisionByYCoord(y);
            GameCard pickedCard = this.findCardByCoords(x, y);
            switch (division) {
                case BOARD_BOT -> engine.cardOnBotBoardClicked(pickedCard);
                case BOARD_PLAYER -> engine.cardOnPlayerBoardClicked(pickedCard);
                case HAND_PLAYER -> engine.cardInHandClicked(pickedCard);
            }
            repaint();
            updateStatistics();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
