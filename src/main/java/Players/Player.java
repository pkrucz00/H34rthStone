package Players;

import Cards.GameCard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class Player {
    private static int playersMade = 0;

    private int id;
    private String name;
    private LinkedList<GameCard> deck;
    protected ArrayList<GameCard> cardsInHand;
    protected GameCard mainCard;

    public Player(String name, LinkedList<GameCard> deck){
        this.id = playersMade;
        playersMade += 1;

        this.name = name;
        this.deck = deck;
        this.cardsInHand = new ArrayList<>();
    }

    public void takeCardFromDeck(){
        GameCard newCard = this.deck.pollLast();
        if (newCard != null)
            this.cardsInHand.add(newCard);
    }

    public void playCard(GameCard card){
        if (this.cardsInHand.contains(card)) {
            this.cardsInHand.remove(card);
            this.mainCard.changeForce(-card.getForce());
        }
    }

    public void setMainCard(int HP, int AP, int force){
        this.mainCard = new GameCard(this.name, HP, AP, force);
    }

    public GameCard getMainCard() {
        return mainCard;
    }

    public String getName() {
        return name;
    }

    public LinkedList<GameCard> getDeck() {
        return deck;
    }

    public ArrayList<GameCard> getCardsInHand() {
        return cardsInHand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
