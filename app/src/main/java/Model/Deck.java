package Model;

import java.util.Vector;
import java.util.Collections;

public class Deck {

    private Vector<Card> deckOfCards = new Vector<Card>();

    /**
     * Constructor, initializes deck
     */
    Deck() {


        Vector<Card> newDeck = new Vector<Card>();

        for(int i = 0; i < 13; i++) {
            for(int j = 0; j < 4; j++) {

                newDeck.addElement(new Card(i,j));
            }
        }

        deckOfCards = newDeck;
    }

    /**
     * gets the deck
     * @return Vector<Model.Card>
     */
    public Vector<Card> getDeck() {

        return this.deckOfCards;
    }

    /**
     * sets the deck
     * @param Vector<Model.Card> deckOfCards
     */
    public void setDeck(Vector<Card> deckOfCards) {

        this.deckOfCards = deckOfCards;
    }

    /**
     * shuffles the cards
     */
    public void shuffleCards() {

        Collections.shuffle(deckOfCards);

    }

    /**
     * Loops through vector to print it
     * @param Vector<Model.Card> v
     */
    public void printVector(Vector<Card> v) {

        for(int i = 0; i < v.size(); i++) {

            v.get(i).print();
        }
    }


}
