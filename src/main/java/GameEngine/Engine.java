package GameEngine;

import Cards.GameCard;
import Players.Bot;
import Players.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

public class Engine {
    private Board board;
    private Player humanPlayer;
    private Bot bot;

    private final String[] playerNames = new String[] {"Master Yoga", "Dart Sinuous"};

    public final String jsonInitValPath = "src/main/jsonData/initialValues.json";
    public final String jsonDarkSideDeckPath = "src/main/jsonData/decks/darkSideDeck.json";
    public final String jsonLightSideDeckPath = "src/main/jsonData/decks/lightSideDeck.json";

    public final InitValues initValues;

    private GameCard pickedCard;

    public Engine(boolean playAsDarkSide){
        JsonParser parser = new JsonParser();
        this.initValues = parser.parseInitialValues(jsonInitValPath);
        LinkedList<GameCard> darkSideDeck = parser.parseDeck(jsonDarkSideDeckPath);
        LinkedList<GameCard> lightSideDeck = parser.parseDeck(jsonLightSideDeckPath);

        if (playAsDarkSide) {
            this.humanPlayer = new Player(playerNames[1], darkSideDeck);
            this.bot = new Bot(playerNames[0], lightSideDeck);
        } else {
            this.humanPlayer = new Player(playerNames[0], lightSideDeck);
            this.bot = new Bot(playerNames[1], darkSideDeck);
        }

        this.board = new Board(humanPlayer, bot, initValues);
        this.pickedCard = null;
    }

    public void makeTurnOfBot(){
        if (board.getCurrPlayerInd() != 1)
             throw new AssertionError("Bot tried to make a move not on his turn");
        GameCard cardToPlaceOnBoard = bot.chooseCardToPlay();
        if (cardToPlaceOnBoard != null)
            board.placeCardOnBoard(cardToPlaceOnBoard);

        ArrayList<GameCard>[] cardsOnBoard = board.getCardsOnBoard();
        ArrayList<GameCard> botCards = cardsOnBoard[1];
        ArrayList<GameCard> playersCards = cardsOnBoard[0];
        GameCard[] attackCards = bot.chooseCardToAttack(botCards, playersCards);
        board.attackCards(attackCards[0], attackCards[1]);


    }

    public void cardInHandClicked(GameCard card){
        if (card.getForce() <= humanPlayer.getMainCard().getForce()){
            board.placeCardOnBoard(card);
        }
    }

    public void cardOnPlayerBoardClicked(GameCard card){
        if(!board.wasCardUsed(card))
            this.pickedCard = card;
    }

    public void cardOnBotBoardClicked(GameCard card) {
        if (pickedCard != null){
            board.attackCards(pickedCard, card);
            pickedCard = null;
        }
    }

    public GameCard findCardByIndex(int index, BoardDivision boardDevision){
        if (boardDevision == BoardDivision.BOARD_BOT){
            return board.getCardsOnBoard()[1].get(index);
        } else if (boardDevision == BoardDivision.BOARD_PLAYER){
            return board.getCardsOnBoard()[0].get(index);
        } else {
            return humanPlayer.getCardsInHand().get(index);
        }
    }


    public Board getBoard() {
        return board;
    }

    public Player getHumanPlayer() {
        return humanPlayer;
    }

    public Bot getBot() {
        return bot;
    }

    public InitValues getInitValues() {
        return initValues;
    }

    public GameCard getPickedCard() {
        return pickedCard;
    }
}
