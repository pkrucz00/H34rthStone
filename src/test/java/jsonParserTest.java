import Cards.GameCard;
import GameEngine.InitValues;
import GameEngine.JsonParser;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.*;

public class jsonParserTest {
    @Test
    void deckParserTest(){
        String filePath = "src/main/jsonData/decks/lightSideDeck.json";
        JsonParser parser = new JsonParser();
        LinkedList<GameCard> gameCards = parser.parseDeck(filePath);

        GameCard maceWandu = gameCards.getFirst();
        GameCard barBarJinks = gameCards.getLast();

        assertEquals("Mace Wandu", maceWandu.getName());
        assertEquals(5, maceWandu.getHP());
        assertEquals(6, maceWandu.getAP());
        assertEquals(3, maceWandu.getForce());

        assertEquals(3, barBarJinks.getHP());
        assertEquals(1, barBarJinks.getAP());
        assertEquals(1, barBarJinks.getForce());
    }

    @Test
    void initValParserTest(){
        String filePath = "src/main/jsonData/initialValues.json";
        JsonParser parser = new JsonParser();
        InitValues initValues = parser.parseInitialValues(filePath);

        assertEquals(30, initValues.INIT_HP);
    }
}
