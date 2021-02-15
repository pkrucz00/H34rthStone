import Cards.GameCard;
import Players.Bot;
import Players.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    @Test
    void PlayerInitializationTest(){
        LinkedList<GameCard> deck = new LinkedList<>();
        deck.add(new GameCard("card1", 1, 1, 1));
        deck.add(new GameCard("card2", 1, 1, 1));
        Player testPlayer = new Player("test", deck);
        testPlayer.setMainCard(2,3,4);

        assertEquals("test", testPlayer.getName());
        assertEquals(deck, testPlayer.getDeck());
        assertEquals(new ArrayList<GameCard>(), testPlayer.getCardsInHand());

        GameCard testPlayerMainCard = testPlayer.getMainCard();
        assertEquals(2, testPlayerMainCard.getHP());
        assertEquals(3, testPlayerMainCard.getAP());
        assertEquals(4, testPlayerMainCard.getForce());
    }

    @Test
    void PlayerCardTakingTest(){
        LinkedList<GameCard> deck = new LinkedList<>();
        GameCard card1 = new GameCard("card1", 1, 1, 1);
        GameCard card2 = new GameCard("card2", 1, 1, 1);
        deck.add(card1);
        deck.add(card2);
        Player testPlayer = new Player("test", deck);
        testPlayer.setMainCard(2,3,4);

        testPlayer.playCard(new GameCard("dummy", 2,3,4));
        testPlayer.takeCardFromDeck();
        assertEquals(card2, testPlayer.getCardsInHand().get(0));
        assertEquals(card1, testPlayer.getDeck().getLast());

        testPlayer.takeCardFromDeck();
        ArrayList<GameCard> expectedCardsInHand = new ArrayList<>(Arrays.asList(card2, card1));
        assertEquals(expectedCardsInHand, testPlayer.getCardsInHand());
        assertEquals(new LinkedList<>(), testPlayer.getDeck());
    }


}
