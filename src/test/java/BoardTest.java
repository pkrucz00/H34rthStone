import Cards.GameCard;
import GameEngine.Board;
import GameEngine.InitValues;
import Players.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    void BoardInitializationTest(){
        InitValues init = new InitValues(30,3,1,7,10,10);
        Player[] players = new Player[2];
        for (int i = 0; i < 2; i++){
            LinkedList<GameCard> deck = new LinkedList<>();
            for (int j = 0; j < 4; j++) {
                deck.add(new GameCard("card", 1, 1, 1));
            }
            players[i] = new Player("test", deck);
        }
        Board testBoard = new Board(players[0], players[1], init);


        assertTrue(Arrays.equals(players, testBoard.getPlayers()));
        assertTrue(Arrays.equals(testBoard.getCardsOnBoard(),
                new ArrayList[]{new ArrayList<>(Collections.singleton(players[0].getMainCard())),
                                new ArrayList<>(Collections.singleton(players[1].getMainCard()))}));

        for (int i = 0; i < 2; i++) {
            assertEquals(3, players[i].getCardsInHand().size());
        }

        GameCard[] mainCards = testBoard.getMainCards();
        assertEquals(init.INIT_HP, mainCards[0].getHP());
        assertEquals(init.INIT_AP, mainCards[1].getAP());
        assertEquals(init.INIT_FORCE, mainCards[0].getForce());
    }


    @Test
    void gettingCardsOnBoard(){
        InitValues init = new InitValues(30,3,1,7,10,10);
        Player[] players = new Player[2];
        for (int i = 0; i < 2; i++){
            LinkedList<GameCard> deck = new LinkedList<>();
            for (int j = 0; j < 4; j++) {
                deck.add(new GameCard("card", 1, 1, 1));
            }
            players[i] = new Player("test", deck);
        }
        Board testBoard = new Board(players[0], players[1], init);

        int i = 0;
        for (Player player:players) {       //each player chooses one card to place on board
            GameCard cardToPlaceOnBoard = player.getCardsInHand().get(0);
            testBoard.placeCardOnBoard(cardToPlaceOnBoard);
            assertEquals(2, player.getCardsInHand().size());
            assertEquals(testBoard.getCardsOnBoard()[i],
                    new ArrayList<GameCard>(Arrays.asList(player.getMainCard(), cardToPlaceOnBoard)));

            testBoard.endTurn();
            i++;
        }

        for (Player player:players){
            int currPlayerInd = testBoard.getCurrPlayerInd();
            int otherPlayerInd = (currPlayerInd + 1) %2;

            GameCard cardOnBoard = testBoard.getCardsOnBoard()[currPlayerInd].get(1);  //not 0, because 0 is the main card
            GameCard opponentsMainCard = testBoard.getMainCards()[otherPlayerInd];
            testBoard.attackCards(cardOnBoard, opponentsMainCard);
            testBoard.endTurn();

            assertEquals(29, opponentsMainCard.getHP());
            assertEquals(1, testBoard.getCardsOnBoard()[currPlayerInd].size());
        }


    }
}
