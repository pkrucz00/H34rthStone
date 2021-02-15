package Cards;

import java.util.Objects;

public class GameCard {
    private static int cardsMade = 0;

    private int id;
    private String name;
    private int HP;
    private int AP;
    private int force;

    public GameCard(String name, int healthPoints, int attackPoints, int force){
        this.setID();

        this.name = name;
        this.HP = healthPoints;
        this.AP = attackPoints;
        this.force = force;
    }

    public boolean isExterminated(){
        return this.HP <= 0;
    }

    public void changeHP(int change) {
        this.HP += change;
    }

    public void changeForce(int change) {
        this.force += change;
    }

    public void attack(GameCard other){
        other.changeHP(-this.getAP());
        this.changeHP(-other.getAP());
    }

    public void setID(){
        GameCard.cardsMade += 1;
        this.id = cardsMade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameCard gameCard = (GameCard) o;
        return id == gameCard.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getName(){
        return name;
    }
    public int getHP() {
        return HP;
    }
    public int getAP() {
        return AP;
    }
    public int getForce() {
        return force;
    }
    public int getId() { return id;}

}
