package Players;

import Cards.GameCard;

import java.util.ArrayList;
import java.util.LinkedList;

public class Bot extends Player{
    public Bot(String name, LinkedList<GameCard> deck) {
        super(name, deck);
    }

    public GameCard chooseCardToPlay(){
        int maxForce = mainCard.getForce();
        GameCard cardWithMostAP = null;
        for (GameCard card: cardsInHand){
            if (card.getForce() <= maxForce && (cardWithMostAP == null || card.getAP() > cardWithMostAP.getAP())){
                cardWithMostAP = card;
            }
        }
        return cardWithMostAP;
    }

    public GameCard[] chooseCardToAttack(ArrayList<GameCard> botCardsOnBoard, ArrayList<GameCard> opponentsCardsOnBoard){
        GameCard cardToUse = mainCard;
        GameCard cardToAttack = null;
        for (GameCard botCard: botCardsOnBoard){
            if (botCard.getForce() < mainCard.getForce() && (cardToUse == null || botCard.getAP() > cardToUse.getAP() )){
                cardToUse = botCard;
            }
        }
        for (GameCard opponentsCard: opponentsCardsOnBoard){
            if (cardToAttack == null || opponentsCard.getHP() <= cardToUse.getAP()){
                cardToAttack = opponentsCard;
            }
        }
        return new GameCard[] {cardToUse, cardToAttack};
    }
}
