import Cards.GameCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {
    @Test
    void GameCardInitializationTest(){
        GameCard testCard = new GameCard("test1", 4, 3, 6);
        assertEquals(4, testCard.getHP());
        assertEquals(3, testCard.getAP());
        assertEquals(6, testCard.getForce());

        GameCard anotherCard = new GameCard("test1", 4, 3, 6);
        assertNotEquals(testCard, anotherCard);
    }

    @Test
    void attackTest(){
        GameCard testCard = new GameCard("test1", 6, 3, 6);
        GameCard anotherCard = new GameCard("test2", 1, 7, 6);
        testCard.attack(anotherCard);
        assertTrue(testCard.isExterminated());
        assertTrue(anotherCard.isExterminated());
    }
}
