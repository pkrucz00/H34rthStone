import GameEngine.Board;
import GameEngine.Engine;
import Players.Bot;
import Players.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EngineTest {
    @Test
    void engineInitTest() {
        Engine engine = new Engine(true);
        Board board = engine.getBoard();
        Player player = engine.getHumanPlayer();
        Bot bot = engine.getBot();

        assertEquals(30, player.getMainCard().getHP());
        assertEquals("Master Yoga", bot.getName());
    }
}
