package GameEngine;

import Cards.GameCard;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;

public class JsonParser {
    public LinkedList<GameCard> parseDeck(String jsonPath){
        try {
            JsonReader reader = new JsonReader(new FileReader(jsonPath));
            GameCard[] gameCards = new Gson().fromJson(reader, GameCard[].class);
            for (GameCard card: gameCards){
                card.setID();
            }
            return new LinkedList<GameCard>(Arrays.asList(gameCards));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public InitValues parseInitialValues(String jsonPath){
        try {
            JsonReader reader = new JsonReader(new FileReader(jsonPath));
            return new Gson().fromJson(reader, InitValues.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
