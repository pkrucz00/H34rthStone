package GameEngine;

import Cards.GameCard;
import Players.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Board {
    private final Player[] players = new Player[2];
    private final GameCard[] mainCards;
    private ArrayList<GameCard>[] cardsOnBoard;
    private Set<GameCard> disabledCards = new HashSet<>();
    private boolean gameEnded = false;
    int currPlayerInd;

    private final InitValues init;

    public Board(Player player1, Player player2, InitValues init){
        this.players[0] = player1;
        this.players[1] = player2;
        this.init = init;

        for (Player player:players) {
            player.setMainCard(init.INIT_HP, init.INIT_AP, init.INIT_FORCE);
            Collections.shuffle(player.getDeck());  //we shuffle all the players cards before game
            for (int i = 0; i < 3; i++){
                player.takeCardFromDeck();      //we take 3 cards from the deck
            }
        }
        this.mainCards = new GameCard[] {player1.getMainCard(), player2.getMainCard()};
        this.cardsOnBoard = new ArrayList[2] ;  //one set for each player
        for (int i = 0; i < 2; i++){
            cardsOnBoard[i] = new ArrayList<>(Collections.singleton(mainCards[i])); //main card is always on board
        }
        this.currPlayerInd = 0;
    }

    public void placeCardOnBoard(GameCard card){
        if (cardsOnBoard[currPlayerInd].size() < init.MAX_CARDS_ON_BOARD) {
            Player currentPlayer = this.players[currPlayerInd];
            currentPlayer.playCard(card);
            cardsOnBoard[currPlayerInd].add(card);
            disabledCards.add(card);
        }
    }

    public int checkWinner(){
        boolean isDeadP1 = this.mainCards[0].isExterminated();
        boolean isDeadP2 = this.mainCards[1].isExterminated();
        if (isDeadP1 && isDeadP2){
          return 3;                //Draw
        } else if (isDeadP1){
            return 2;               //P2 won
        } else if (isDeadP2){
            return 1;               //P1 won
        } else {
            return 0;               //game still going
        }
    }

    public void attackCards(GameCard attacker, GameCard defender){
        attacker.attack(defender);
        disabledCards.add(attacker);
        if (attacker.isExterminated()){
            if (attacker.equals(mainCards[currPlayerInd])){
                gameEnded = true;
            }
            this.cardsOnBoard[currPlayerInd].remove(attacker);
        }
        if (defender.isExterminated()){
            int otherPlayerInd = (currPlayerInd+1)%2;
            if (defender.equals(mainCards[otherPlayerInd])){
                gameEnded = true;
            }
            this.cardsOnBoard[otherPlayerInd].remove(defender);
        }
    }

    public void endTurn(){
        Player currPlayer = this.players[currPlayerInd];
        GameCard mainCard = this.mainCards[currPlayerInd];

        if (mainCard.getForce() < init.MAX_FORCE){
            mainCard.changeForce(1);
        }
        if (currPlayer.getCardsInHand().size() < init.MAX_CARDS_IN_HAND)
            currPlayer.takeCardFromDeck();

        currPlayerInd = (currPlayerInd + 1) % 2;
        disabledCards.clear();
    }

    public String endGameMessage(){
        gameEnded = true;
        int winner = checkWinner();
        if (winner == 1)
            return "You have won!";
        if (winner == 2)
            return "You lose";
        if (winner == 3){
            return "Draw";
        }
        return "Błąd";
    }

    public boolean wasCardUsed(GameCard card){
        return disabledCards.contains(card);
    }

    public Player[] getPlayers() {
        return players;
    }

    public GameCard[] getMainCards() {
        return mainCards;
    }

    public ArrayList<GameCard>[] getCardsOnBoard() {
        return cardsOnBoard;
    }

    public int getCurrPlayerInd() {
        return currPlayerInd;
    }

    public Set<GameCard> getDisabledCards() {
        return disabledCards;
    }

    public int getNoOfCards(BoardDivision division){
        return switch (division) {
            case BOARD_BOT -> cardsOnBoard[1].size();
            case BOARD_PLAYER -> cardsOnBoard[0].size();
            case HAND_PLAYER -> players[0].getCardsInHand().size();
        };
    }

    public boolean haveGameEnded(){
        return gameEnded;
    }
}
