package GameEngine;

public class InitValues {
    public final int INIT_HP;
    public final int INIT_AP;
    public final int INIT_FORCE;

    public final int MAX_CARDS_ON_BOARD;
    public final int MAX_CARDS_IN_HAND;
    public final int MAX_FORCE;

    public InitValues(int initHP, int initAP, int initForce,
                      int maxOnBoard, int maxInHand, int maxForce){
        this.INIT_HP = initHP;
        this.INIT_AP = initAP;
        this.INIT_FORCE = initForce;
        this.MAX_CARDS_ON_BOARD = maxOnBoard;
        this.MAX_CARDS_IN_HAND = maxInHand;
        this.MAX_FORCE = maxForce;
    }

}
