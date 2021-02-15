import Cards.GameCard;
import Players.Bot;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BotTest {
    @Test
    void botInitializationTest(){
        LinkedList<GameCard> deck = new LinkedList<>();
        deck.add(new GameCard("card1", 1, 1, 1));
        deck.add(new GameCard("card2", 1, 1, 1));
        Bot testBot = new Bot("test", deck);
        testBot.setMainCard(2,3,4);

        assertEquals("test", testBot.getName());
        assertEquals(deck, testBot.getDeck());
        assertEquals(new ArrayList<GameCard>(), testBot.getCardsInHand());

        GameCard testPlayerMainCard = testBot.getMainCard();
        assertEquals(2, testPlayerMainCard.getHP());
        assertEquals(3, testPlayerMainCard.getAP());
        assertEquals(4, testPlayerMainCard.getForce());
    }

    @Test
    void botCardTakingTest(){
        LinkedList<GameCard> deck = new LinkedList<>();
        GameCard card1 = new GameCard("card1", 1, 1, 1);
        GameCard card2 = new GameCard("card2", 1, 1, 1);
        deck.add(card1);
        deck.add(card2);
        Bot testBot = new Bot("test", deck);
        testBot.setMainCard(2,3,4);

        testBot.playCard(new GameCard("dummy", 2,3,4));
        testBot.takeCardFromDeck();
        assertEquals(card2, testBot.getCardsInHand().get(0));
        assertEquals(card1, testBot.getDeck().getLast());

        testBot.takeCardFromDeck();
        ArrayList<GameCard> expectedCardsInHand = new ArrayList<>(Arrays.asList(card2, card1));
        assertEquals(expectedCardsInHand, testBot.getCardsInHand());
        assertEquals(new LinkedList<>(), testBot.getDeck());
    }

    @Test
    void botChoosingCardToPlaceTest(){
        //we want available card with most AP
        LinkedList<GameCard> deck = new LinkedList<>();
        GameCard card1 = new GameCard("card1", 3, 3, 5);
        GameCard card2 = new GameCard("card2", 1, 1, 3);
        GameCard card3 = new GameCard("card3", 1, 3, 4);
        deck.addAll(Arrays.asList(card1, card2, card3));
        Bot testBot = new Bot("test", deck);
        testBot.setMainCard(2,3,4);

        for (int i = 0; i < 3; i++){
            testBot.takeCardFromDeck();
        }

        GameCard chosenCard = testBot.chooseCardToPlay();
        assertEquals(chosenCard, card3);
        testBot.playCard(chosenCard);

        chosenCard = testBot.chooseCardToPlay();
        assertEquals(null, chosenCard);
    }

    @Test
    void botChoosingCardToAttack(){
        LinkedList<GameCard> deck = new LinkedList<>();
        GameCard card1 = new GameCard("card1", 3, 3, 5);
        GameCard card2 = new GameCard("card2", 1, 1, 3);
        GameCard card3 = new GameCard("card3", 1, 3, 4);
        deck.addAll(Arrays.asList(card1, card2, card3));
//        Bot testBot = new Bot("test", deck);
//        testBot.setMainCard(2,3,4);
//
//
//        Bot dummy = new Bot("dummy", )
//
//        testBot.chooseCardToAttack()

    }
}
